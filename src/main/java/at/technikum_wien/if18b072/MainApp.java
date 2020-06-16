package at.technikum_wien.if18b072;

import static at.technikum_wien.if18b072.Constants.*;
import at.technikum_wien.if18b072.database.SQLiteService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class loads the main scene and launches the stage. It also establishes
 * an SQLite database connection and closes it before program termination.
 */
public class MainApp extends Application {


    /**
     * Loads the scene and sets it and the main stage up.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        
        stage.setTitle("JavaFX and Gradle");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Establishes the SQLite database connection and launches the JavaFX application.
     * @param args
     */
    public static void main(String[] args) {
        DATABASE = new SQLiteService();
        launch(args);
    }

    /**
     * Closes the database connection upon termination.
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        DATABASE.close();
    }
}
