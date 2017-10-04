package com.igor.logsearcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SearchWindow extends AnchorPane {
    
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
    private TextField searchField;
    
    @FXML
    private void handleClickMeButton(ActionEvent event) {
        System.out.println("You clicked me!");
        searchField.setText("Hello!");
    }
    
    /*@FXML
    private void handleNewTabButton(ActionEvent event) {
        newTab.run();
        System.out.println("New tab opened!");
        searchField.setText("Good buy!");
    }*/
}
