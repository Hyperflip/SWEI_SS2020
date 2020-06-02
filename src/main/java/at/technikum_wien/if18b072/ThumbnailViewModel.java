package at.technikum_wien.if18b072;

import javafx.beans.property.SimpleStringProperty;

public class ThumbnailViewModel {

    public SimpleStringProperty parentPicturePathProperty;
    public SimpleStringProperty pathProperty;

    public ThumbnailViewModel(ThumbnailModel thumbnail) {
        parentPicturePathProperty = new SimpleStringProperty();
        pathProperty = new SimpleStringProperty();

        parentPicturePathProperty.set(thumbnail.getParentPicturePath());
        pathProperty.set(thumbnail.getPath());
    }
}
