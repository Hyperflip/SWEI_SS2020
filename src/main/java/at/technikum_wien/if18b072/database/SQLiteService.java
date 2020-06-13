package at.technikum_wien.if18b072.database;

import at.technikum_wien.if18b072.models.PhotographerModel;
import at.technikum_wien.if18b072.models.PictureModel;
import org.tinylog.Logger;

import java.sql.*;
import java.util.List;

public class SQLiteService implements IDatabaseService {

    private static final String CREATE_PHOTOGRAPHERS =
            "CREATE TABLE IF NOT EXISTS photographers" +
                    "(photographerID INTEGER PRIMARY KEY," +
                    "firstName TEXT, lastName TEXT, email TEXT" +
                    "birthday TEXT, notes TEXT)";

    private static final String CREATE_PICTURES =
            "CREATE TABLE IF NOT EXISTS pictures" +
                    "(path TEXT PRIMARY KEY, photographerID INTEGER," +
                    "focalRatio TEXT, exposureTime TEXT, orientation TEXT," +
                    "make TEXT, model TEXT, fileFormat TEXT, dateCreated TEXT," +
                    "country TEXT, byLine TEXT, caption TEXT," +
                    "FOREIGN KEY(photographerID) REFERENCES photographers(photographerID))";

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
    public boolean addNewPicture(PictureModel picture) {
        return false;
    }

    @Override
    public boolean addNewPhotographer(PhotographerModel photographer) {
        return false;
    }

    @Override
    public boolean updatePicture(PictureModel picture) {
        return false;
    }

    @Override
    public boolean updatePhotographer(PhotographerModel photographer) {
        return false;
    }

    @Override
    public List<String> getPathsFromSearchString(String search) {
        return null;
    }

    @Override
    public PictureModel getPictureModelFromPath(String path) {
        return null;
    }

    @Override
    public void close() {

    }
}
