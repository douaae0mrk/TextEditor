package com.texteditor.ui;

import javafx.scene.control.ToolBar;
import com.texteditor.editor.EditorController;

/**
 *  Represents the toolbar of the text editor.
 *
 *  Responsible for providing user interface controls
 *  for common editor actions, such as opening, saving,
 *  undoing, redoing and searching documents.
 *
 *  The EditorToolbar delegates actions to the appropriate
 *  controllers or components and does not implement
 *  the underlying editing logic.
 */

public class EditorToolbar extends ToolBar {

    private final EditorController controller;

    // Creates the toolbar associated with the specified controller.
    public EditorToolbar(EditorController controller);

    // Initializes and arranges the toolbar buttons.
    public void initialize();

    // Enables or disables the Undo button.
    public void setUndoEnabled(boolean enabled);

    // Enables or disables the Redo button.
    public void setRedoEnabled(boolean enabled);

}