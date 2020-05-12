package at.technikum_wien.if18b072;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    private MockPictureModels mpm;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // test shit
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");

        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
        button.setText("Click me!");
        button.setOnAction(this::myClickEvent);


        // get mock pictures
        this.mpm = new MockPictureModels();

        this.fillScrollPane();

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

    private void fillScrollPane() {

        for(PictureViewModel pvm : this.mpm.getAllPictureViewModels()) {

            System.out.println(pvm.pathProperty.getValue());

            Image img = new Image("file:///" + pvm.pathProperty.getValue());
            ImageView imgView = new ImageView(img);
            imgView.setFitWidth(200);
            imgView.setPreserveRatio(true);

            this.imgScrollPaneHBox.getChildren().add(imgView);
        }
    }

    private void myClickEvent(Event e) {
        System.out.println("You clicked the button!");
    }
}
