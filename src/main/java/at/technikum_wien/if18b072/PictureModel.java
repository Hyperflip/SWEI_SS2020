package at.technikum_wien.if18b072;

import java.util.HashMap;

public class PictureModel {

    private String path;
    private HashMap<String, String> exif;
    private HashMap<String, String> iptc;

    public PictureModel(String path) {
        this.path = path;
    }

    public void setExif(HashMap<String, String> exif) {
        this.exif = exif;
    }

    public void setIptc(HashMap<String, String> iptc) {
        this.iptc = iptc;
    }

    public String getPath() {
        return this.path;
    }

    public HashMap<String, String> getExif() {
        return this.exif;
    }

    public HashMap<String, String> getIptc() {
        return this.iptc;
    }

}
