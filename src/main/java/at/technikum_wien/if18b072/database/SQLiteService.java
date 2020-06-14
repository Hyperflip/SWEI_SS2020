package at.technikum_wien.if18b072.database;

import static at.technikum_wien.if18b072.Constants.*;
import at.technikum_wien.if18b072.models.PhotographerModel;
import at.technikum_wien.if18b072.models.PictureModel;
import org.tinylog.Logger;

import java.sql.*;
import java.util.*;

public class SQLiteService implements IDatabaseService {

    // CREATE STATEMENTS

    private static final String CREATE_PHOTOGRAPHERS =
            "CREATE TABLE IF NOT EXISTS photographers" +
                    "(photographerEmail TEXT PRIMARY KEY," +
                    "firstName TEXT, lastName TEXT," +
                    "birthday TEXT, notes TEXT)";

    private static final String CREATE_PICTURES =
            "CREATE TABLE IF NOT EXISTS pictures" +
                    "(path TEXT PRIMARY KEY, photographerEmail TEXT," +
                    "focalRatio TEXT, exposureTime TEXT, orientation TEXT," +
                    "make TEXT, model TEXT, fileFormat TEXT, dateCreated TEXT," +
                    "country TEXT, byLine TEXT, caption TEXT," +
                    "FOREIGN KEY(photographerEmail) REFERENCES photographers(photographerEmail))";

    // INSERT STATEMENTS

    private static final String INSERT_PHOTOGRAPHER =
            "INSERT OR IGNORE INTO photographers " +
                    "VALUES(?, ?, ?, ?, ?)";

    private static final String INSERT_PICTURES =
            "INSERT OR IGNORE INTO pictures " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // SELECT STATEMENTS
    // selection by filename
    private static final String SELECT_PATHS_BY_FILENAME =
            "SELECT path FROM pictures " +
                    "WHERE path LIKE ?";

    // selection by camera manufacturer
    private static final String SELECT_PATHS_BY_MAKE =
            "SELECT path FROM pictures " +
                    "WHERE make LIKE ?";

    // selection by country
    private static final String SELECT_PATHS_BY_COUNTRY =
            "SELECT path FROM pictures " +
                    "WHERE country LIKE ?";

    private static final String SELECT_PATHS_BY_BYLINE =
            "SELECT path FROM pictures " +
                    "WHERE byLine LIKE ?";

    // whole picture selection
    private static final String SELECT_PICTURE_BY_PATH =
            "SELECT * FROM pictures WHERE path = ?";

    // UPDATE STATEMENTS

    private static final String UPDATE_PICTURE_IPTC =
            "UPDATE pictures " +
                    "SET fileFormat = ?, dateCreated = ?, country = ?, byLine = ?, caption = ? " +
                    "WHERE path = ?";

    private static final String DB_URL = "jdbc:sqlite:sqlite.db";
    private Connection connection;

    public SQLiteService() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            Logger.debug("Successfully established SQLite database connection.");
        } catch (SQLException e) {
            Logger.debug("Failed to establish SQLite database connection.");
            Logger.trace(e);
        }

        createTables();
    }

    private void createTables() {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(CREATE_PHOTOGRAPHERS);
            stmt.execute(CREATE_PICTURES);
            stmt.close();
            Logger.debug("Successfully located/create database tables.");
        } catch (SQLException e) {
            Logger.debug("Failed to locate/create database tables.");
            Logger.trace(e);
        }

    }

    @Override
    public boolean addNewPhotographer(PhotographerModel photographer) {
        try {
            PreparedStatement stmt = connection.prepareStatement(INSERT_PHOTOGRAPHER);
            stmt.setString(1, photographer.getPhotographerEmail());
            stmt.setString(2, photographer.getFirstName());
            stmt.setString(3, photographer.getLastName());
            stmt.setString(4, photographer.getBirthday());
            stmt.setString(5, photographer.getNotes());
            stmt.execute();
            stmt.close();
            Logger.debug("Successfully inserted into table photographers.");
            return true;
        } catch (SQLException e) {
            Logger.debug("Failed to insert into table photographers.");
            Logger.trace(e);
        }
        return false;
    }

    @Override
    public boolean addNewPicture(PictureModel picture) {
        try {
            PreparedStatement stmt = connection.prepareStatement(INSERT_PICTURES);
            stmt.setString(1, picture.getPath());
            stmt.setString(2, picture.getPhotographerEmail());
            stmt.setString(3, picture.getFocalRatio());
            stmt.setString(4, picture.getExposureTime());
            stmt.setString(5, picture.getOrientation());
            stmt.setString(6, picture.getMake());
            stmt.setString(7, picture.getModel());
            stmt.setString(8, picture.getFileFormat());
            stmt.setString(9, picture.getDateCreated());
            stmt.setString(10, picture.getCountry());
            stmt.setString(11, picture.getByLine());
            stmt.setString(12, picture.getCaption());
            stmt.execute();
            stmt.close();

            Logger.debug("Successfully inserted into table pictures.");
            return true;
        } catch (SQLException e) {
            Logger.debug("Failed to insert into table photographers.");
            Logger.trace(e);
        }
        return false;
    }

    @Override
    public boolean updatePicture(PictureModel picture) {
        try {
            PreparedStatement stmt = connection.prepareStatement(UPDATE_PICTURE_IPTC);
            stmt.setString(1, picture.getFileFormat());
            stmt.setString(2, picture.getDateCreated());
            stmt.setString(3, picture.getCountry());
            stmt.setString(4, picture.getByLine());
            stmt.setString(5, picture.getCaption());
            stmt.setString(6, picture.getPath());
            stmt.execute();

            Logger.debug("Successfully updated picture in database.");
            return true;
        } catch (SQLException e) {
            Logger.debug("Failed at updating picture in database.");
            Logger.trace(e);
        }
        return false;
    }

    @Override
    public boolean updatePhotographer(PhotographerModel photographer) {
        return false;
    }

    private ArrayList<String> getPathsFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<String> results = new ArrayList<>();
        while(rs.next()) {
            results.add(rs.getString("path"));
        }
        return results;
    }

    @Override
    public ArrayList<String> getPathsFromSearchString(String search) {
        try {
            // look for filename
            PreparedStatement stmt1 = connection.prepareStatement(SELECT_PATHS_BY_FILENAME);
            stmt1.setString(1, IMAGES_PATH_REL + "%" + search + "%");
            // look for camera manufacturer
            PreparedStatement stmt2 = connection.prepareStatement(SELECT_PATHS_BY_MAKE);
            stmt2.setString(1, "%" + search + "%");
            // look for country
            PreparedStatement stmt3 = connection.prepareStatement(SELECT_PATHS_BY_COUNTRY);
            stmt3.setString(1, "%" + search + "%");
            // look for byLine
            PreparedStatement stmt4 = connection.prepareStatement(SELECT_PATHS_BY_BYLINE);
            stmt4.setString(1, "%" + search + "%");

            /* result set containing all paths matching search string in each columns
            HashSet -> no duplicates :) */
            HashSet<String> result = new HashSet<>(Collections.emptySet());

            result.addAll(getPathsFromResultSet(stmt1.executeQuery()));
            stmt1.close();
            result.addAll(getPathsFromResultSet(stmt2.executeQuery()));
            stmt2.close();
            result.addAll(getPathsFromResultSet(stmt3.executeQuery()));
            stmt3.close();
            result.addAll(getPathsFromResultSet(stmt4.executeQuery()));
            stmt4.close();

            Logger.debug("Successfully retrieved picture paths from database.");
            return new ArrayList<>(result);
        } catch (SQLException e) {
            Logger.debug("Failed to retrieve picture paths from database.");
            Logger.trace(e);
            return null;
        }
    }

    @Override
    public PictureModel getPictureModelFromPath(String path) {
        try {
            PreparedStatement stmt = connection.prepareStatement(SELECT_PICTURE_BY_PATH);
            stmt.setString(1, path);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                PictureModel pm = new PictureModel(rs.getString("path"));
                pm.setPhotographerEmail(rs.getString("photographerEmail"));
                pm.setFocalRatio(rs.getString("focalRatio"));
                pm.setExposureTime(rs.getString("exposureTime"));
                pm.setOrientation(rs.getString("orientation"));
                pm.setMake(rs.getString("make"));
                pm.setModel(rs.getString("model"));
                pm.setFileFormat(rs.getString("fileFormat"));
                pm.setDateCreated(rs.getString("dateCreated"));
                pm.setCountry(rs.getString("country"));
                pm.setByLine(rs.getString("byLine"));
                pm.setCaption(rs.getString("caption"));

                Logger.debug("Successfully retrieved picture by path from database.");
                stmt.close();
                return pm;
            }
            else {
                Logger.debug("Failed to retrieve picture by path from database: empty ResultSet returned.");
                return null;
            }

        } catch (SQLException e) {
            Logger.debug("Failed to retrieve picture by path from database.");
            Logger.trace(e);
            return null;
        }
    }

    @Override
    public void close() {

    }
}
