package com.igor.logsearcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class LoadFilesToTree {
    private final Path rootDirectory;
    
    public LoadFilesToTree(String rootDirectoryPath) throws IOException {
        rootDirectory = Paths.get(rootDirectoryPath);
        if (!Files.exists(rootDirectory, LinkOption.NOFOLLOW_LINKS)) {
            throw new IOException("Specified directory does not exist");
        }
        if (!Files.isDirectory(rootDirectory, LinkOption.NOFOLLOW_LINKS)) {
            throw new IOException("Specified file is not a directory");
        }
    }
    
    public TreeItem<String> load() {
        return loadHelper(rootDirectory);
    }
    
    private TreeItem<String> loadHelper(Path directory) {
        TreeItem<String> root = new TreeItem<>(directory.getFileName().toString(), new ImageView(SearchWindow.FOLDER));
        try (Stream<Path> entries = Files.list(directory)) {
            entries.forEach(entry -> {
                if (Files.isRegularFile(entry, LinkOption.NOFOLLOW_LINKS)) {
                    root.getChildren().add(new TreeItem<>(entry.getFileName().toString()));
                }
                else if (Files.isDirectory(entry, LinkOption.NOFOLLOW_LINKS)) {
                    root.getChildren().add(loadHelper(entry));
                }
            });
        } 
        catch (IOException e) {
            root.setGraphic(new ImageView(SearchWindow.FOLDER_ERROR));
            System.err.println("IOException " + e.getMessage());
        }
        return root;
    }
}