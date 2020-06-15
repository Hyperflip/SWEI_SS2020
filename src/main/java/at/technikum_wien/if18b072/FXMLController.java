package at.technikum_wien.if18b072;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static at.technikum_wien.if18b072.Constants.*;

import at.technikum_wien.if18b072.models.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.tinylog.Logger;

public class FXMLController implements Initializable {

    // top menu
    @FXML
    public MenuBar topMenuBar;
    @FXML
    public MenuItem managePhotographers;
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

    private ArrayList<ThumbnailViewModel> thumbnailViewModels = new ArrayList<ThumbnailViewModel>();

    private PictureViewModel activePictureViewModel;

    private void prepareUI() {

        // set event for menu item managePhotographers
        managePhotographers.setOnAction(this::handleClickedManagePhotographers);

        // set event for searchbar
        searchBar.setOnKeyReleased(event -> {
            loadThumbnails();
            updateScrollPane();
        });

        // set event for save iptc button
        buttonSaveIPTC.setOnAction(this::handleSaveIPTC);

        // bind size of active images's ImageView to its container's size
        imgActive.fitWidthProperty().bind(imgActiveContainer.widthProperty());
        imgActive.fitHeightProperty().bind(imgActiveContainer.heightProperty());
    }

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

            // set event for thumbnail imageviews
            imgView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleClickedThumbnail(event, thvm));

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

        // bind image
        imgActive.imageProperty().bind(activePictureViewModel.imageProperty);
        // bind iptc info
        fileFormat.textProperty().bindBidirectional(activePictureViewModel.fileFormatProperty);
        dateCreated.textProperty().bindBidirectional(activePictureViewModel.dateCreatedProperty);
        country.textProperty().bindBidirectional(activePictureViewModel.countryProperty);
        byLine.textProperty().bindBidirectional(activePictureViewModel.byLineProperty);
        caption.textProperty().bindBidirectional(activePictureViewModel.captionProperty);
        // bind exif info
        focalRatio.textProperty().bind(activePictureViewModel.focalRatioProperty);
        exposureTime.textProperty().bind(activePictureViewModel.exposureTimeProperty);
        orientation.textProperty().bind(activePictureViewModel.orientionProperty);
        make.textProperty().bind(activePictureViewModel.makeProperty);
        model.textProperty().bind(activePictureViewModel.modelProperty);
    }

    private void handleSaveIPTC(Event e) {
        // picture model to be updated
        PictureModel pm = activePictureViewModel.getPictureModel();
        // call functions to update Picture Model IPTC info
        pm.setFileFormat(fileFormat.getText());
        pm.setDateCreated(dateCreated.getText());
        pm.setCountry(country.getText());
        pm.setByLine(byLine.getText());
        pm.setCaption(caption.getText());

        DATABASE.updatePicture(pm);

        e.consume();
    }

    private void handleClickedThumbnail(Event e, ThumbnailViewModel thvm) {
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

        e.consume();
    }

    private void handleClickedManagePhotographers(Event e) {
        Logger.debug("Initializing 'Manage Photographers' dialog.");

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(((MenuItem)e.getTarget()).getParentPopup().getOwnerWindow());

        try {
            Parent root = FXMLLoader.load(getClass().getResource("dialogScene.fxml"));

            Scene dialogScene = new Scene(root);
            dialogScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

            Logger.debug("Successfully loaded new scene (dialogScene.fxml).");

            dialog.setScene(dialogScene);
            dialog.show();

        } catch (IOException ex) {
            Logger.debug("Failed at loading new scene (dialogScene.fxml).");
            Logger.trace(ex);

        }

        e.consume();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // set up appearance, initialize UI, set event handling
        prepareUI();

        // create thumbnails
        loadThumbnails();

        // fill scroll pane with thumbnails
        updateScrollPane();

        // initialize activePictureViewModel
        initializeActivePicture();

    }
}
