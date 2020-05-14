package at.technikum_wien.if18b072;

public class PictureModel {
    // relative path to the image file
    private String path;
    // EXIF data
    private int focalRatio;         // aka f-number
    private int exposureTime;       // exposure time
    private String orientation;     // img orientation
    private String make;            // camera manufacturer
    private String model;           // camera model
    // IPTC DATA
    private String fileFormat;      // img file format
    private String dateCreated;     // img creation date
    private String country;         // origin country
    private String byLine;          // author name
    private String caption;         // img caption

    // getters and setters
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getFocalRatio() {
        return focalRatio;
    }

    public void setFocalRatio(int focalRatio) {
        this.focalRatio = focalRatio;
    }

    public int getExposureTime() {
        return exposureTime;
    }

    public void setExposureTime(int exposureTime) {
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
