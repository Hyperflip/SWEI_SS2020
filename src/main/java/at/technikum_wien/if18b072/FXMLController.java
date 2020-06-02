package at.technikum_wien.if18b072;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class FXMLController implements Initializable {

    @FXML
    public MenuBar topMenuBar;
    @FXML
    public TextField searchBar;
    @FXML
    public ImageView imgPreview;
    @FXML
    public AnchorPane imgPrevContainer;
    @FXML
    public HBox imgScrollPaneHBox;
    @FXML
    private Label label;
    @FXML
    private Button button;

    private List<PictureModel> pictures;
    private List<ThumbnailViewModel> thumbnailViewModels = new ArrayList<ThumbnailViewModel>();

    private void loadThumbnails() {
        for(PictureModel picture : pictures) {
            thumbnailViewModels.add(new ThumbnailViewModel(new ThumbnailFactory(picture).getThumbnailModel()));
        }
    }

    private void loadPicturesFromMock() {
        this.pictures = new MockPictureModels().getPictureModels();
    }

    // TODO:
    // private void loadPicturesFromDB() {}

    private void fillScrollPane() {

        for(ThumbnailViewModel thvm : thumbnailViewModels) {

            System.out.println(thvm.pathProperty.getValue());

            Image img = new Image("file:" + thvm.pathProperty.getValue());
            ImageView imgView = new ImageView(img);
            imgView.setFitWidth(200);
            imgView.setPreserveRatio(true);

            imgScrollPaneHBox.getChildren().add(imgView);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // test stuff
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");

        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
        button.setText("Click me!");
        button.setOnAction(this::myClickEvent);

        /*
        1. load list of PictureModels (from db, mock, etc.)
        3. create list of ThumbnailViewModels
        3. fill scrollpane with images (ThumbnailViewModel)
        4. initialize PictureViewModel for the image preview (first picture in list)
           (this includes binding properties to text fields in EXIF, IPTC, Photographer panes)
        5. initialization done... handle events from now on
        */


        // 1. LOAD PICTURES
        loadPicturesFromMock();

        // 2. CREATE THUMBNAILS
        loadThumbnails();

        // 3. FILL SCROLLPANE WITH THUMBNAILS
        fillScrollPane();

        // img preview
        imgPreview.fitWidthProperty().bind(imgPrevContainer.widthProperty());
        imgPreview.fitHeightProperty().bind(imgPrevContainer.heightProperty());

        Image image = null;
        try {
            image = new Image(new FileInputStream("glass_orb.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imgPreview.setImage(image);

    }

    private void myClickEvent(Event e) {
        System.out.println("You clicked the button!");
    }
}
