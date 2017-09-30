package com.igor.logsearcher;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        MainScene controller = new MainScene();
        stage.setScene(new Scene(controller));
        
        stage.getScene().getStylesheets().add("/styles/Styles.css");
        stage.setMinWidth(300.0);
        stage.setMinHeight(200.0);
        stage.setTitle("Log Searcher");
        
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
