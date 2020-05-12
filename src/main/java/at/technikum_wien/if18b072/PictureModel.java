package at.technikum_wien.if18b072;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

import java.util.HashMap;

public class PictureModel {

    private SimpleStringProperty pathProperty;
    private MapProperty<String, String> exifProperty;
    private MapProperty<String, String> iptcProperty;

    public PictureModel(String path) {
        this.pathProperty = new SimpleStringProperty(path);
        this.exifProperty = new SimpleMapProperty<>(FXCollections.observableHashMap());
        this.iptcProperty = new SimpleMapProperty<>(FXCollections.observableHashMap());
    }

    public void setExif(HashMap<String, String> exif) {
        this.exifProperty.putAll(exif);
    }

    public void setIptc(HashMap<String, String> iptc) {
        this.iptcProperty.putAll(iptc);
    }

    public SimpleStringProperty pathProperty() {
        return this.pathProperty;
    }

    public MapProperty<String, String> exifProperty() {
        return this.exifProperty;
    }

    public MapProperty<String, String> iptcProperty() {
        return this.iptcProperty;
    }

    public String getPath() {
        return this.pathProperty.getValue();
    }

    /*
    public HashMap<String, String> getExif() {
        return this.exif;
    }
     */

    /*
    public HashMap<String, String> getIptc() {
        return this.iptc;
    }
     */

}
