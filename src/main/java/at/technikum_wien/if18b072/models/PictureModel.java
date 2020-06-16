package at.technikum_wien.if18b072.models;

/**
 * The PictureModel holds all information of a picture and contains
 * get/set access for each field.
 */
public class PictureModel {
    // relative path to the image file
    private String path;                    // PRIMARY key in database
    // has get and set access, as the photographer can be changed
    private String photographerEmail;       // FOREIGN key in database
    // EXIF data
    String focalRatio;                      // aka f-number
    private String exposureTime;            // exposure time in seconds
    private String orientation;             // img orientation
    private String make;                    // camera manufacturer
    private String model;                   // camera model
    // IPTC Data
    private String fileFormat;              // img file format
    private String dateCreated;             // img creation date
    private String country;                 // origin country
    private String byLine;                  // author name
    private String caption;                 // img caption

    public PictureModel() {}

    public PictureModel(String path) {
        this.setPath(path);
    }

    // getters and setters
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPhotographerEmail() {
        return photographerEmail;
    }

    public void setPhotographerEmail(String photographerEmail) {
        this.photographerEmail = photographerEmail;
    }

    public String getFocalRatio() {
        return focalRatio;
    }

    public void setFocalRatio(String focalRatio) {
        this.focalRatio = focalRatio;
    }

    public String getExposureTime() {
        return exposureTime;
    }

    public void setExposureTime(String exposureTime) {
        this.exposureTime = exposureTime;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getByLine() {
        return byLine;
    }

    public void setByLine(String byLine) {
        this.byLine = byLine;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
