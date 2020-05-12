package at.technikum_wien.if18b072;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockPictureModels {
    private List<PictureViewModel> pictureViewModels = new ArrayList<PictureViewModel>();

    public MockPictureModels() {

        File dir = new File("src/main/resources/at/technikum_wien/if18b072/stock_images");
        File[] files = dir.listFiles();

        assert files != null;
        for(File file : files) {
            PictureModel pm = new PictureModel(file.getAbsolutePath());
            pm.setExif(new HashMap<>(){{
                put("author", "philipp");
                put("date", "10.05.2020");
            }});
            this.pictureViewModels.add(new PictureViewModel(pm));
        }
    }

    public List<PictureViewModel> getAllPictureViewModels() {
        return this.pictureViewModels;
    }
}
