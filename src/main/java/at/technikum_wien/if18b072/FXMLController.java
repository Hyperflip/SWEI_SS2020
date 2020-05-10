package at.technikum_wien.if18b072;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class FXMLController implements Initializable {

    @FXML
    public MenuBar topMenuBar;
    @FXML
    public TextField searchBar;
    @FXML
    public ImageView imgPreview;
    @FXML
    public ScrollPane imgScrollPane;
    @FXML
    public AnchorPane imgPrevContainer;
    @FXML
    private Label label;
    @FXML
    private Button button;

    // private PictureModel[] pictureModels = {};

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // test shit
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");

        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
        button.setText("Click me!");
        button.setOnAction(this::myClickEvent);


        // fill ScrollPane

        MockPictureModels mpm = new MockPictureModels();
        for(PictureModel pm : mpm.getAllPictureModels()) {
            System.out.println(pm.getPath());
        }

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

    private void setMockPictureModels() {
    }

    private void myClickEvent(Event e) {
        System.out.println("You clicked the button!");
    }
}
