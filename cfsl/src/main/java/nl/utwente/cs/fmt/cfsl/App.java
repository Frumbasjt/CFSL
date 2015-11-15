package nl.utwente.cs.fmt.cfsl;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.utwente.cs.fmt.cfsl.gui.main.MainController;


public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = MainController.getInstance().getView();
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
