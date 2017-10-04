package com.igor.logsearcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class SearchWindow extends AnchorPane {
    public static final Image FOLDER_OPEN = new Image(SearchWindow.class.getResourceAsStream("/icons/folder-open.bmp"));
    public static final Image FOLDER_ERROR = new Image(SearchWindow.class.getResourceAsStream("/icons/folder-error.bmp"));
    public static final Image FOLDER = new Image(SearchWindow.class.getResourceAsStream("/icons/folder.bmp"));
    
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
    private TreeView<String> fsView;
    @FXML
    private TextArea fileView;
    
    @FXML
    private void handleClickMeButton(ActionEvent event) {
        System.out.println("You clicked me!");
        searchField.setText("Hello!");
    }
    
    private final Lock treeViewLock = new ReentrantLock();
    
    @FXML
    private void handleEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            new Thread(this::loadTreeViewAsync).start();
        }
    }
    
    private Path getPathFromTreeItem(TreeItem<String> item) {
        List<String> names = new ArrayList<>();
        names.add(item.getValue());
        while((item = item.getParent()) != null) {
            names.add(item.getValue());
        }
        Collections.reverse(names);
        names.remove(0);
        String first = names.get(0);
        names.remove(0);
        return Paths.get(first, names.toArray(new String[0]));
    }
    
    private final ChangeListener fileChangeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            TreeItem<String> selectedItem = (TreeItem<String>) newValue;
            System.out.println("Selected Text : " + selectedItem.getValue());
            Path path = getPathFromTreeItem(selectedItem);
            System.out.println("Path : " + path.toString());
            if (Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)) {
                try {
                    String content = new String(Files.readAllBytes(path), Charset.defaultCharset());
                    fileView.setText(content);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                
            }
        }
    };
    
    private void loadTreeViewAsync() {
        if (!treeViewLock.tryLock()) {
            return;
        }
        LoadFilesToTree loader;
        try {
            loader = new LoadFilesToTree(System.getProperty("user.dir"));
        }
        catch (IOException exc) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error loading directory");
            alert.setContentText(exc.getMessage());

            alert.showAndWait();
            return;
        }
        TreeItem<String> root = loader.load();
        Platform.runLater( () -> fsView.setRoot(root) );
        Platform.runLater(() -> fsView.getSelectionModel().selectedItemProperty().addListener(fileChangeListener));
        treeViewLock.unlock();
    }
    /*@FXML
    private void handleNewTabButton(ActionEvent event) {
        newTab.run();
        System.out.println("New tab opened!");
        searchField.setText("Good buy!");
    }*/
}
