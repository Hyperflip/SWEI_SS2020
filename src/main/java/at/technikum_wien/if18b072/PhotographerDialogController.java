package at.technikum_wien.if18b072;

import at.technikum_wien.if18b072.models.PhotographerModel;
import at.technikum_wien.if18b072.models.PhotographerViewModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import static at.technikum_wien.if18b072.Constants.*;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.tinylog.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The JavaFX controller class responsible for the 'Manage photographers' dialog stage.
 */
public class PhotographerDialogController implements Initializable {

    // list of photographers
    @FXML
    public VBox photographersScrollPaneVBox;
    // photographer info
    @FXML
    public VBox photographerInfo;
    @FXML
    public TextField firstName;
    @FXML
    public TextField lastName;
    @FXML
    public Text photographerEmail;
    @FXML
    public TextField birthday;
    @FXML
    public TextField notes;
    @FXML
    public Button savePhotographerInfo;

    private PhotographerViewModel activePhotographerViewModel;

    private ArrayList<String> photographerEmails = new ArrayList<>();

    /**
     * This function loads up the photographers and sets up the UI elements.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Logger.debug("Initializing PhotographerDialogController");

        // set event handler for button
        savePhotographerInfo.setOnAction(this::handleSavePhotographerInfo);

        loadPhotographers();
        createPhotographerButtons();
        initializeActivePhotographer();
    }

    /**
     * This function loads all photographers email addresses and stores them in a local variable.
     */
    private void loadPhotographers() {
        // clear
        photographerEmails.clear();
        // add from database query
        photographerEmails.addAll(DATABASE.getPhotographerEmails());
    }

    /**
     * This function creates a button for each photographer loaded.
     */
    private void createPhotographerButtons() {
        for(String email : photographerEmails) {
            // create new button
            Button button = new Button();
            // set button ID to photographerEmail
            button.setId(email);
            // get full name from database from email
            ArrayList<String> name = DATABASE.getFullNameFromEmail(email);
            // set button text
            button.setText(name.get(0) + " " + name.get(1));
            // set event handler
            button.setOnAction(this::handleClickedPhotographerButton);
            // append to UI element
            photographersScrollPaneVBox.getChildren().add(button);
        }
    }

    /**
     * This function initializes the active PhotographerViewModel and binds its properties
     * to the corresponding UI elements.
     */
    private void initializeActivePhotographer() {
        // set activePhotographerViewModel
        activePhotographerViewModel = new PhotographerViewModel(
                // get PhotographerModel from database
                DATABASE.getPhotographerFromEmail(
                        // from first email in list
                        photographerEmails.get(0)
                )
        );

        // bind photographer info
        firstName.textProperty().bindBidirectional(activePhotographerViewModel.firstNameProperty);
        lastName.textProperty().bindBidirectional(activePhotographerViewModel.lastNameProperty);
        photographerEmail.textProperty().bindBidirectional(activePhotographerViewModel.photographerEmailProperty);
        birthday.textProperty().bindBidirectional(activePhotographerViewModel.birthdayProperty);
        notes.textProperty().bindBidirectional(activePhotographerViewModel.notesProperty);

        updateAssociatedPictures();
    }

    /**
     * This function updates the list of pictures shot by the active photographer.
     */
    private void updateAssociatedPictures() {
        // clear
        photographerInfo.getChildren().clear();

        for(String path : DATABASE.getPicturePathsFromEmail(activePhotographerViewModel
                .photographerEmailProperty.getValue())) {
            Text pathText = new Text();
            pathText.setText(path);
            photographerInfo.getChildren().add(pathText);
        }
    }

    /**
     * This function handles the click of the photographer buttons, from the photographer's
     * email address it retrieves a new PhotographerModel from the database and set it for
     * the active PhotographerViewModel.
     * @param e
     */
    private void handleClickedPhotographerButton(Event e) {
        // get email from button id
        String email = ((Button)e.getSource()).getId();
        // set new active PhotographerModel
        activePhotographerViewModel.setPhotographerModel(
                // get PhotographerModel from database by email
                DATABASE.getPhotographerFromEmail(
                        email
                )
        );

        // update properties
        activePhotographerViewModel.updateProperties();

        // update associated pictures
        updateAssociatedPictures();
    }

    /**
     * This function handles the click event of the 'Save Photographer Info' buttom. It
     * sets the new info to the active PhotographerModel and updates the active
     * PhotographerViewModel's properties based on the new PhotographerModel.
     * It also synchronizes with the database.
     * @param e
     */
    private void handleSavePhotographerInfo(Event e) {
        // PhotographerModel to be updated
        PhotographerModel phm = activePhotographerViewModel.getPhotographerModel();
        // set new info
        phm.setFirstName(firstName.getText());
        phm.setLastName(lastName.getText());
        phm.setBirthday(birthday.getText());
        phm.setNotes(notes.getText());

        DATABASE.updatePhotographer(phm);

        e.consume();
    }

}
