package at.technikum_wien.if18b072;

import java.awt.*;
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
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
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

/**
 * The JavaFX controller class responsible for the main stage.
 */
public class FXMLController implements Initializable {

    // top menu
    @FXML
    public MenuBar topMenuBar;
    @FXML
    public MenuItem showImageFolder;
    @FXML
    public MenuItem managePhotographers;
    @FXML
    public MenuItem visitGithub;
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

    /**
     * This function prepares event handling and general settings for the
     * UI elements of the main stage.
     */
    private void prepareUI() {

        // set event for menu item showImageFolder
        showImageFolder.setOnAction(this::handleClickedShowImageFolder);

        // set event for menu item managePhotographers
        managePhotographers.setOnAction(this::handleClickedManagePhotographers);

        // ser event for menu item visitGithub
        visitGithub.setOnAction(this::handleClickedVisitGithub);

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

    /**
     * This function loads the thumbnail paths for each picture in the database.
     */
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

    /**
     * This function fills the scrollpane at the bottom of the scene with the loaded thumbnails.
     */
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

    /**
     * This function initializes the active picture's PictureViewModel and binds the properties to
     * the corresponding UI elements.
     */
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

    /**
     * This function handles the click event of the 'Save IPTC Info" button.
     * It updates the underlying data for the active PhotographerViewModel and
     * synchronizes the changes with the database.
     * @param e
     */
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

    /**
     * This function handles the click of a thumbnail's ImageView, it retrieves
     * a new PictureModel for the thumbnails parent picture and sets it as the
     * new PictureModel of the active PictureViewModel.
     * @param e
     * @param thvm
     */
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

    /**
     * This function handles the click event of the 'Manage Photographers' menu item.
     * It loads a new scene and sets it to a new stage, the dialog for the photographer management.
     * @param e
     */
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

    /**
     * This function handles the click event on the menu item showImageFolder.
     * It opens an alert with the relative path to the images.
     * @param e
     */
    private void handleClickedShowImageFolder(Event e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Relative path to images:");
        alert.setContentText(IMAGES_PATH_REL);

        alert.showAndWait();

        e.consume();
    }

    /**
     * This function handles the click event on the menu item visitGithub.
     * It opens the GitHub repo in the default browser.
     * @param e
     */
    private void handleClickedVisitGithub(Event e) {
        String urlString = "https://github.com/Hyperflip/SWEI_SS2020";

        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
            Logger.debug("Successfully opened GitHub page.");
        } catch (Exception ex){
            Logger.debug("Failed at opening GitHub page.");
            Logger.trace(ex);
        }

        e.consume();
    }

    /**
     * This function calls the corresponding class methods to load up the pictures
     * and initialize the UI elements.
     * @param url
     * @param rb
     */
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
