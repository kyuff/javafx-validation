package dk.kyuff.javafx.validation.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * User: swi
 * Date: 25/10/14
 * Time: 12.39
 */
public class DemoApp extends Application {

    public static void main(String[] args) {
        launch(DemoApp.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/demo-app.fxml"));

        Scene scene = new Scene(root, 300, 275);

        stage.setTitle("FXML Validator Demo");
        stage.setScene(scene);
        stage.show();
    }
}
