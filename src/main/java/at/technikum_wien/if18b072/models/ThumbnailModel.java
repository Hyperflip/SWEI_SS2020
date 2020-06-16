package at.technikum_wien.if18b072.models;

/**
 * The ThumbnaiLmodel holds all information of a thumbnail and contains
 * get/set access for each field. It also holds its parent picture's path.
 */
public class ThumbnailModel {

    private final String path;
    private final String parentPicturePath;
    private final String extension;

    public ThumbnailModel(String path, String parentPicturePath, String extension) {
        this.path = path;
        this.parentPicturePath = parentPicturePath;
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public String getPath() {
        return path;
    }

    public String getParentPicturePath() {
        return parentPicturePath;
    }
}
