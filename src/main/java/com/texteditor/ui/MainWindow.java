package com.texteditor.ui;

import javafx.stage.Stage;
import com.texteditor.editor.Editor;
import com.texteditor.editor.EditorController;

/**
 *  Represents the main application window of the text editor.
 *
 *  Responsible for initializing and arranging the main
 *  user interface components, including the editor view
 *  and toolbar.
 *
 *  Acts as the main container for the application's
 *  graphical user interface.
 */

public class MainWindow {

    private final Stage stage;
    private final EditorView editorView;
    private final EditorToolbar toolbar;

    // Creates the main window with the specified editor and controller.
    public MainWindow(Stage stage, Editor editor,
                      EditorController controller);

    // Displays the application window.
    public void show();

    // Sets the application window title.
    public void setTitle(String title);

    // Closes the application window.
    public void close();

}