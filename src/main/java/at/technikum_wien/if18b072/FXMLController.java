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
import javafx.scene.text.Text;

public class FXMLController implements Initializable {

    // top menu
    @FXML
    public MenuBar topMenuBar;
    // searchbar
    @FXML
    public TextField searchBar;
    // active image + its container to manage size
    @FXML
    public ImageView imgActive;
    @FXML
    public AnchorPane imgActiveContainer;
    // list of thumbnails at the bottom
    @FXML
    public HBox imgScrollPaneHBox;
    // iptc info
    @FXML
    public TextField fileFormat;
    @FXML
    public TextField dateCreated;
    @FXML
    public TextField country;
    @FXML
    public TextField byLine;
    @FXML
    public TextField caption;
    // exif info
    @FXML
    public Text focalRatio;
    @FXML
    public Text exposureTime;
    @FXML
    public Text orientation;
    @FXML
    public Text make;
    @FXML
    public Text model;
    // buttons
    @FXML
    public Button buttonSaveIPTC;

    private List<PictureModel> pictures;
    private List<ThumbnailViewModel> thumbnailViewModels = new ArrayList<ThumbnailViewModel>();

    private PictureViewModel activePictureViewModel;

    private void prepareUI() {

        // set event for searchbar
        searchBar.setOnKeyReleased(event -> {
            loadThumbnails();
            updateScrollPane();
        });

        // set event for save iptc button
        buttonSaveIPTC.setOnAction(event -> {
            // picture model to be updated
            PictureModel pm = activePictureViewModel.getPictureModel();
            // call functions to update Picture Model IPTC info
            pm.setFileFormat(fileFormat.getText());
            pm.setDateCreated(dateCreated.getText());
            pm.setCountry(country.getText());
            pm.setByLine(byLine.getText());
            pm.setCaption(caption.getText());

            DATABASE.updatePicture(pm);

            activePictureViewModel.updateProperties();
            // this should be enough for the display to be updated?
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

        // set image
        imgActive.setImage(image);
        // set iptc info
        fileFormat.setText(activePictureViewModel.fileFormatProperty.getValue());
        dateCreated.setText(activePictureViewModel.dateCreatedProperty.getValue());
        country.setText(activePictureViewModel.countryProperty.getValue());
        byLine.setText(activePictureViewModel.byLineProperty.getValue());
        caption.setText(activePictureViewModel.captionProperty.getValue());
        // set exif info
        focalRatio.setText(activePictureViewModel.focalRatioProperty.getValue());
        exposureTime.setText(activePictureViewModel.exposureTimeProperty.getValue());
        orientation.setText(activePictureViewModel.orientionProperty.getValue());
        make.setText(activePictureViewModel.makeProperty.getValue());
        model.setText(activePictureViewModel.modelProperty.getValue());
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
}
