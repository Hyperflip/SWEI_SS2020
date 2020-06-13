package at.technikum_wien.if18b072.models;

import at.technikum_wien.if18b072.models.ThumbnailModel;
import javafx.beans.property.SimpleStringProperty;

public class ThumbnailViewModel {

    public SimpleStringProperty parentPicturePathProperty;
    public SimpleStringProperty pathProperty;

    // HINT: passing the ThumbnailModel to the constructor does NOT save the model (good/bad?)
    public ThumbnailViewModel(ThumbnailModel thumbnail) {
        parentPicturePathProperty = new SimpleStringProperty();
        pathProperty = new SimpleStringProperty();

        parentPicturePathProperty.set(thumbnail.getParentPicturePath());
        pathProperty.set(thumbnail.getPath());
    }
}
