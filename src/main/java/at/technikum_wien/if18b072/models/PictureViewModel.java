package at.technikum_wien.if18b072.models;

import at.technikum_wien.if18b072.models.PictureModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

/**
 * The PictureViewModel contains all Simple _ Properties of a PictureModel and
 * contains get/set access for the underlying PictureModel.
 */
public class PictureViewModel {
    // the PictureModel for this ViewModel
    private PictureModel picture;
    // property of the image
    public SimpleStringProperty pathProperty;
    public SimpleObjectProperty<Image> imageProperty;
    // EXIF properties
    public SimpleStringProperty focalRatioProperty;
    public SimpleStringProperty exposureTimeProperty;
    public SimpleStringProperty orientionProperty;
    public SimpleStringProperty makeProperty;
    public SimpleStringProperty modelProperty;
    // IPTC properties
    public SimpleStringProperty fileFormatProperty;
    public SimpleStringProperty dateCreatedProperty;
    public SimpleStringProperty countryProperty;
    public SimpleStringProperty byLineProperty;
    public SimpleStringProperty captionProperty;

    public PictureViewModel(PictureModel picture) {
        this.picture = picture;

        pathProperty = new SimpleStringProperty();
        imageProperty = new SimpleObjectProperty<>();

        focalRatioProperty = new SimpleStringProperty();
        exposureTimeProperty = new SimpleStringProperty();
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

    public void setPictureModel(PictureModel picture) {
        this.picture = picture;
    }

    public PictureModel getPictureModel() {
        return picture;
    }

    /**
     * This function updates the properties with the current PictureModel.
     */
    public void updateProperties() {
        pathProperty.set(picture.getPath());
        imageProperty.set(new Image("file:" + picture.getPath()));

        focalRatioProperty.set(picture.getFocalRatio());
        exposureTimeProperty.set(picture.getExposureTime());
        orientionProperty.set(picture.getOrientation());
        makeProperty.set(picture.getMake());
        modelProperty.set(picture.getModel());

        fileFormatProperty.set(picture.getFileFormat());
        dateCreatedProperty.set(picture.getDateCreated());
        countryProperty.set(picture.getCountry());
        byLineProperty.set(picture.getByLine());
        captionProperty.set(picture.getCaption());
    }
}
