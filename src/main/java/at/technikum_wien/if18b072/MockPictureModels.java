package at.technikum_wien.if18b072;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockPictureModels {
    private List<PictureModel> pictureModels = new ArrayList<PictureModel>();

    public MockPictureModels() {

        File dir = new File("src/main/resources/at/technikum_wien/if18b072/stock_images");
        File[] files = dir.listFiles();

        assert files != null;
        for(File file : files) {
            PictureModel pm = new PictureModel(file.getPath());
            pm.setExif(new HashMap<>(){{
                put("auhtor", "philipp");
                put("date", "10.05.2020");
            }});
            this.pictureModels.add(pm);
        }
    }

    public List<PictureModel> getAllPictureModels() {
        return this.pictureModels;
    }
}
