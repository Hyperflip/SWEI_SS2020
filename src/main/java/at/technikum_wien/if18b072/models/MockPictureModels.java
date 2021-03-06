package at.technikum_wien.if18b072.models;

import at.technikum_wien.if18b072.models.PhotographerModel;
import at.technikum_wien.if18b072.models.PictureModel;

import java.util.ArrayList;
import java.util.List;
import static at.technikum_wien.if18b072.Constants.*;

/**
 * This class holds the mock information for pictures and photographers
 * and calls the corresponding insert methods of the database service.
 * It will only be called if the database gets reset.
 */
public class MockPictureModels {
    private List<PictureModel> pictures = new ArrayList<PictureModel>();

    public MockPictureModels() {

        PhotographerModel phot1 = new PhotographerModel();
        phot1.setPhotographerEmail("andert.philipp@gmail.com");
        phot1.setFirstName("Philipp");
        phot1.setLastName("Andert");
        phot1.setBirthday("1999-01-15");
        phot1.setNotes("Genius. Ahead of our time. ~New York Times");

        PhotographerModel phot2 = new PhotographerModel();
        phot2.setPhotographerEmail("fredi.emsenhuber@live.at");
        phot2.setFirstName("Alfred");
        phot2.setLastName("Emsenhuber");
        phot2.setBirthday("1996-10-18");
        phot2.setNotes("This is outrageous, it's unfair. How can he " +
                "be on the photographer's council but not a genius?");

        PictureModel pic1 = new PictureModel(IMAGES_PATH_REL + "architecture.png");
        // exif
        pic1.setPhotographerEmail("andert.philipp@gmail.com");
        pic1.setFocalRatio("f/1.4");
        pic1.setExposureTime("1/659 s");
        pic1.setOrientation("top-left");
        pic1.setMake("Nikon");
        pic1.setModel("Nikon D7500");
        // iptc
        pic1.setFileFormat("png");
        pic1.setDateCreated("20200531");
        pic1.setCountry("austria");
        pic1.setByLine("Philipp Andert");
        pic1.setCaption("Wunderschöne Fassade");

        PictureModel pic2 = new PictureModel(IMAGES_PATH_REL + "hotel.png");
        // exif
        pic2.setPhotographerEmail("fredi.emsenhuber@live.at");
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
        pic3.setPhotographerEmail("andert.philipp@gmail.com");
        pic3.setFocalRatio("f/1.5");
        pic3.setExposureTime("1/472 s");
        pic3.setOrientation("top-left");
        pic3.setMake("Nikon");
        pic3.setModel("Nikon D7500");
        // iptc
        pic3.setFileFormat("png");
        pic3.setDateCreated("20200530");
        pic3.setCountry("japan");
        pic3.setByLine("Philipp Andert");
        pic3.setCaption("Wunderschöner Mönch");

        PictureModel pic4 = new PictureModel(IMAGES_PATH_REL + "restaurant.png");
        // exif
        pic4.setPhotographerEmail("fredi.emsenhuber@live.at");
        pic4.setFocalRatio("f/1.4");
        pic4.setExposureTime("1/665 s");
        pic4.setOrientation("top-left");
        pic4.setMake("Canon");
        pic4.setModel("Canon EOS 40D");
        // iptc
        pic4.setFileFormat("png");
        pic4.setDateCreated("20200525");
        pic4.setCountry("austria");
        pic4.setByLine("Alfred Emsenhuber");
        pic4.setCaption("Wunderschönes Restaurant");

        PictureModel pic5 = new PictureModel(IMAGES_PATH_REL + "savings.png");
        // exif
        pic5.setPhotographerEmail("andert.philipp@gmail.com");
        pic5.setFocalRatio("f/1.6");
        pic5.setExposureTime("1/501 s");
        pic5.setOrientation("top-left");
        pic5.setMake("Nikon");
        pic5.setModel("Nikon D7500");
        // iptc
        pic5.setFileFormat("png");
        pic5.setDateCreated("20200531");
        pic5.setCountry("austria");
        pic5.setByLine("Alfred Emsenhuber");
        pic5.setCaption("Wunderschönes Sparschwein");

        // add photographers
        DATABASE.addNewPhotographer(phot1);
        DATABASE.addNewPhotographer(phot2);
        // add pictures
        DATABASE.addNewPicture(pic1);
        DATABASE.addNewPicture(pic2);
        DATABASE.addNewPicture(pic3);
        DATABASE.addNewPicture(pic4);
        DATABASE.addNewPicture(pic5);

    }
}