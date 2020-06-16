package at.technikum_wien.if18b072.database;

import at.technikum_wien.if18b072.models.PhotographerModel;
import at.technikum_wien.if18b072.models.PictureModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The interface for a Database Service class.
 */
public interface IDatabaseService {

    boolean addNewPhotographer(PhotographerModel photographer);
    boolean addNewPicture(PictureModel picture);

    boolean updatePicture(PictureModel picture);
    boolean updatePhotographer(PhotographerModel photographer);

    /* this function is useful for the creation of thumbnails, as it is not
    necessary to get whole PictureModels at this step */
    ArrayList<String> getPathsFromSearchString(String search);
    PictureModel getPictureModelFromPath(String path);

    ArrayList<String> getPhotographerEmails();
    ArrayList<String> getFullNameFromEmail(String email);
    PhotographerModel getPhotographerFromEmail(String email);
    ArrayList<String> getPicturePathsFromEmail(String email);

    void close();
}
