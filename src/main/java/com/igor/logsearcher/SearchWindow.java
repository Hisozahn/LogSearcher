package com.igor.logsearcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import java.io.IOException;
import javafx.scene.layout.GridPane;

public class SearchWindow extends GridPane {
    
    private static final String FXML_PATH = "/fxml/Search.fxml";
    public SearchWindow(Runnable newTab) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_PATH));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        if (newTab == null) {
            throw new IllegalArgumentException("Null reference to 'new tab' method in SearchWindow");
        }
        this.newTab = newTab;
    }
    
    private final Runnable newTab;
    
    @FXML
    private Label label;
    
    @FXML
    private void handleClickMeButton(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello!");
    }
    
    @FXML
    private void handleNewTabButton(ActionEvent event) {
        newTab.run();
        System.out.println("New tab opened!");
        label.setText("Good buy!");
    }
}
