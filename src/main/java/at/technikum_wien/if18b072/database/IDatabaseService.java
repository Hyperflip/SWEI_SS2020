package at.technikum_wien.if18b072.database;

import at.technikum_wien.if18b072.models.PhotographerModel;
import at.technikum_wien.if18b072.models.PictureModel;

import java.util.List;

public interface IDatabaseService {

    boolean addNewPicture(PictureModel picture);
    boolean addNewPhotographer(PhotographerModel photographer);

    boolean updatePicture(PictureModel picture);
    boolean updatePhotographer(PhotographerModel photographer);

    /* this function is useful for the creation of thumbnails, as it is not
    necessary to get whole PictureModels at this step */
    List<String> getPathsFromSearchString(String search);

    PictureModel getPictureModelFromPath(String path);

    // close DB connection
    void close();
}
