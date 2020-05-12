package at.technikum_wien.if18b072;

import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;

public class PictureViewModel {
    public SimpleStringProperty pathProperty;
    public SimpleMapProperty<String, String> exifProperty;
    public SimpleMapProperty<String, String> iptcProperty;

    public PictureViewModel(PictureModel picture) {
        this.pathProperty = new SimpleStringProperty();
        this.exifProperty = new SimpleMapProperty<>();
        this.iptcProperty = new SimpleMapProperty<>();

        this.pathProperty.bind(picture.pathProperty());
        this.exifProperty.bindBidirectional(picture.exifProperty());
        this.iptcProperty.bindBidirectional(picture.iptcProperty());
    }
}
