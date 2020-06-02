package at.technikum_wien.if18b072;

import java.util.ArrayList;
import java.util.List;
import static at.technikum_wien.if18b072.Constants.*;

public class MockPictureModels {
    private List<PictureModel> pictures = new ArrayList<PictureModel>();

    public MockPictureModels() {

        PictureModel pic1 = new PictureModel(IMAGES_PATH_REL + "architecture.png");
        // exif
        pic1.setFocalRatio("f/1.4");
        pic1.setExposureTime("1/659 s");
        pic1.setOrientation("top-left");
        pic1.setMake("Canon");
        pic1.setModel("Canon EOS 40D");
        // iptc
        pic1.setFileFormat("png");
        pic1.setDateCreated("20200531");
        pic1.setCountry("austria");
        pic1.setByLine("Philipp Andert");
        pic1.setCaption("Wunderschöne Fassade");

        PictureModel pic2 = new PictureModel(IMAGES_PATH_REL + "hotel.png");
        // exif
        pic2.setFocalRatio("f/1.6");
        pic2.setExposureTime("1/500 s");
        pic2.setOrientation("top-left");
        pic2.setMake("Canon");
        pic2.setModel("Canon EOS 40D");
        // iptc
        pic2.setFileFormat("png");
        pic2.setDateCreated("20200529");
        pic2.setCountry("austria");
        pic2.setByLine("Philipp Andert");
        pic2.setCaption("Wunderschönes Hotel");

        PictureModel pic3 = new PictureModel(IMAGES_PATH_REL + "meditating.png");
        // exif
        pic3.setFocalRatio("f/1.5");
        pic3.setExposureTime("1/472 s");
        pic3.setOrientation("top-left");
        pic3.setMake("Canon");
        pic3.setModel("Canon EOS 40D");
        // iptc
        pic3.setFileFormat("png");
        pic3.setDateCreated("20200530");
        pic3.setCountry("japan");
        pic3.setByLine("Philipp Andert");
        pic3.setCaption("Wunderschöner Mönch");

        PictureModel pic4 = new PictureModel(IMAGES_PATH_REL + "restaurant.png");
        // exif
        pic4.setFocalRatio("f/1.4");
        pic4.setExposureTime("1/665 s");
        pic4.setOrientation("top-left");
        pic4.setMake("Canon");
        pic4.setModel("Canon EOS 40D");
        // iptc
        pic4.setFileFormat("png");
        pic4.setDateCreated("20200525");
        pic4.setCountry("austria");
        pic4.setByLine("Philipp Andert");
        pic4.setCaption("Wunderschönes Restaurant");

        PictureModel pic5 = new PictureModel(IMAGES_PATH_REL + "savings.png");
        // exif
        pic5.setFocalRatio("f/1.6");
        pic5.setExposureTime("1/501 s");
        pic5.setOrientation("top-left");
        pic5.setMake("Canon");
        pic5.setModel("Canon EOS 40D");
        // iptc
        pic5.setFileFormat("png");
        pic5.setDateCreated("20200531");
        pic5.setCountry("austria");
        pic5.setByLine("Philipp Andert");
        pic5.setCaption("Wunderschönes Sparschwein");

        this.pictures.add(pic1);
        this.pictures.add(pic2);
        this.pictures.add(pic3);
        this.pictures.add(pic4);
        this.pictures.add(pic5);
    }

    public List<PictureModel> getPictureModels() {
        return this.pictures;
    }
}