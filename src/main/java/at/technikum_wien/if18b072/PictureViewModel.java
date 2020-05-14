package at.technikum_wien.if18b072;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;

public class PictureViewModel {
    // the PictureModel for this ViewModel
    private PictureModel picture;
    // property of the image path
    private SimpleStringProperty pathProperty;
    // EXIF properties
    private SimpleIntegerProperty focalRatioProperty;
    private SimpleIntegerProperty exposureTimeProperty;
    private SimpleStringProperty orientionProperty;
    private SimpleStringProperty makeProperty;
    private SimpleStringProperty modelProperty;
    // IPTC properties
    private SimpleStringProperty fileFormatProperty;
    private SimpleStringProperty dateCreatedProperty;
    private SimpleStringProperty countryProperty;
    private SimpleStringProperty byLineProperty;
    private SimpleStringProperty captionProperty;

    public PictureViewModel(PictureModel picture) {
        this.picture = picture;

        pathProperty = new SimpleStringProperty();

        focalRatioProperty = new SimpleIntegerProperty();
        exposureTimeProperty = new SimpleIntegerProperty();
        orientionProperty = new SimpleStringProperty();
        makeProperty = new SimpleStringProperty();
        modelProperty = new SimpleStringProperty();

        fileFormatProperty = new SimpleStringProperty();
        dateCreatedProperty = new SimpleStringProperty();
        countryProperty = new SimpleStringProperty();
        byLineProperty = new SimpleStringProperty();
        captionProperty = new SimpleStringProperty();

        updateProperties();
    }

    public void updateProperties() {
        pathProperty.set(picture.getPath());

        focalRatioProperty.set(picture.getFocalRatio());
        exposureTimeProperty.set(picture.getExposureTime());
        orientionProperty.set(picture.getOrientation());
        makeProperty.set(picture.getMake());
        modelProperty.set(picture.getMake());

        fileFormatProperty.set(picture.getFileFormat());
        dateCreatedProperty.set(picture.getDateCreated());
        countryProperty.set(picture.getCountry());
        byLineProperty.set(picture.getByLine());
        captionProperty.set(picture.getCaption());
    }
}
