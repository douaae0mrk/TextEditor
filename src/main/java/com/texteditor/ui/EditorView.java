package com.texteditor.ui;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import com.texteditor.editor.Editor;
import com.texteditor.editor.EditorController;

/**
 *  EditorView represents the visual editing area of the text editor.
 *
 *  It is responsible for displaying the document's content,
 *  cursor, text selection and line numbers, and updating
 *  the visual representation when the document changes.
 *
 */

public class EditorView extends BorderPane {

    private final Editor editor;
    private final EditorController controller;
    private TextArea textArea;

    // Creates the editor view using the specified editor and controller.
    public EditorView(Editor editor, EditorController controller);

    // Refreshes the displayed document content.
    public void refresh();

    // Updates the visual cursor position.
    public void updateCursor();

    // Updates the visual text selection.
    public void updateSelection();

    // Updates the displayed line numbers.
    public void updateLineNumbers();

    // Enables or disables text editing.
    public void setEditable(boolean editable);

}