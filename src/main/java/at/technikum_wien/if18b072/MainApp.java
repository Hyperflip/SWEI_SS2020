package at.technikum_wien.if18b072;

import at.technikum_wien.if18b072.database.SQLiteService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        
        stage.setTitle("JavaFX and Gradle");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Constants.DATABASE = new SQLiteService();
        launch(args);
    }

}
