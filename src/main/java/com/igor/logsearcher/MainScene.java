package com.igor.logsearcher;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
/**
 *
 * @author romig
 */
public class MainScene extends AnchorPane {
    
    private static final String FXML_PATH = "/fxml/MainScene.fxml";
    public MainScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_PATH));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    @FXML
    private TabPane tabPane;
    
    private void addNewTab() {
        Tab tab = new Tab("New tab", new SearchWindow(this::addNewTab));
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }
    
    @FXML
    private void handleNewTabButton(ActionEvent event) {
        addNewTab();
    }
    /*
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addNewTab();
    }*/
}
