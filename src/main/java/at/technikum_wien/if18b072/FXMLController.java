package at.technikum_wien.if18b072;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static at.technikum_wien.if18b072.Constants.*;
import at.technikum_wien.if18b072.models.PictureModel;
import at.technikum_wien.if18b072.models.PictureViewModel;
import at.technikum_wien.if18b072.models.ThumbnailViewModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class FXMLController implements Initializable {

    @FXML
    public MenuBar topMenuBar;
    @FXML
    public TextField searchBar;
    @FXML
    public ImageView imgActive;
    @FXML
    public AnchorPane imgActiveContainer;
    @FXML
    public HBox imgScrollPaneHBox;

    // test labels
    @FXML
    private Label label;
    @FXML
    private Button button;

    private List<PictureModel> pictures;
    private List<ThumbnailViewModel> thumbnailViewModels = new ArrayList<ThumbnailViewModel>();

    private PictureViewModel activePictureViewModel;

    private void prepareUI() {

        // generic test stuff
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
        button.setText("Click me!");
        button.setOnAction(this::myClickEvent);

        // set event for searchbar
        searchBar.setOnKeyReleased(event -> {
            loadThumbnails();
            updateScrollPane();
        });

        // bind size of active images's ImageView to its container's size
        imgActive.fitWidthProperty().bind(imgActiveContainer.widthProperty());
        imgActive.fitHeightProperty().bind(imgActiveContainer.heightProperty());
    }

    private void loadPicturesFromMock() {
        this.pictures = new MockPictureModels().getPictureModels();
    }


    // TODO: implement search string functionality
    private void loadThumbnails() {

        // clear
        thumbnailViewModels.clear();

        ThumbnailFactory thf = new ThumbnailFactory();
        for(String path : DATABASE.getPathsFromSearchString(searchBar.getText())) {
            thumbnailViewModels.add(
                    new ThumbnailViewModel(
                            thf.getThumbnailModel(path)
                    )
            );
        }
    }

    private void updateScrollPane() {

        // clear
        imgScrollPaneHBox.getChildren().clear();

        for(ThumbnailViewModel thvm : thumbnailViewModels) {

            Image img = new Image("file:" + thvm.pathProperty.getValue());
            ImageView imgView = new ImageView(img);
            imgView.getStyleClass().add("thumb-view");
            imgView.setFitWidth(200);
            imgView.setPreserveRatio(true);

            // TODO: implement proper EventHandler class
            imgView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                // set new PictureModel for the active ViewModel
                activePictureViewModel.setPictureModel(
                        // get PictureModel from database
                        DATABASE.getPictureModelFromPath(
                                // from the clicked thumbnails's parent picture path
                                thvm.parentPicturePathProperty.getValue()
                        )
                );

                // update active ViewModel's properties based on its new picture
                activePictureViewModel.updateProperties();
                // update UI elements (ImageView, etc.)
                updateActiveImage();

                event.consume();
            });

            imgScrollPaneHBox.getChildren().add(imgView);
        }
    }

    private void initializeActivePicture() {
        // set activePictureViewModel
        activePictureViewModel = new PictureViewModel(
                // get PictureModel from database
                DATABASE.getPictureModelFromPath(
                        // from first thumbnail's parent picture path
                        thumbnailViewModels.get(0)
                                .parentPicturePathProperty.getValue()
               )
        );

        updateActiveImage();
    }

    private void updateActiveImage() {
        Image image = null;
        try {
            image = new Image(new FileInputStream(activePictureViewModel.pathProperty.getValue()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imgActive.setImage(image);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        prepareUI();

        /*
        1. load PATHS of PictureModels (from db, mock, etc.)
           create list of ThumbnailViewModels
        2. fill scrollpane with images (ThumbnailViewModel)
        3. initialize PictureViewModel for the image preview (first picture in list)
           (this includes binding properties to text fields in EXIF, IPTC, Photographer panes)
        */

        // 1. CREATE THUMBNAILS
        loadThumbnails();

        // 2. FILL SCROLLPANE WITH THUMBNAILS
        updateScrollPane();

        // 3. initialize activePictureViewModel
        // and updateActiveImage (in UI)
        initializeActivePicture();

    }

    // TODO: hey, später gibst du in andere Ding rein (... Klasse)

    private void myClickEvent(Event e) {
        System.out.println("You clicked the button!");
    }
}
