package com.texteditor.editor;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * EditorController handles user interactions and coordinates actions
 * between the user interface and the Editor.
 *
 * It processes user input and translates keyboard actions and editing
 * commands into operations performed by the Editor.
 *
 * The EditorController does not directly manipulate the document's
 * underlying text storage or the internal Cursor and Selection objects.
 */
public class EditorController {

    private final Editor editor;
    private final Clipboard clipboard = Clipboard.getSystemClipboard();

    // Creates a controller for the specified editor.
    public EditorController(Editor editor) {
        if (editor == null) {
            throw new IllegalArgumentException("Editor cannot be null");
        }

        this.editor = editor;
    }

    // Handles text input.
    public void handleTextInput(String text) {
        editor.insertText(text);
    }

    // Handles the Backspace key.
    public void handleBackspace() {
        if (editor.hasSelection()) {
            editor.deleteText(
                    editor.getSelectionStart(),
                    editor.getSelectionEnd()
            );
        } else {
            int position = editor.getCursorPosition();

            if (position > 0) {
                editor.deleteText(position - 1, position);
            }
        }
    }

    // Handles the Delete key.
    public void handleDelete() {
        if (editor.hasSelection()) {
            editor.deleteText(
                    editor.getSelectionStart(),
                    editor.getSelectionEnd()
            );
        } else {
            int position = editor.getCursorPosition();

            if (position < editor.getLength()) {
                editor.deleteText(position, position + 1);
            }
        }
    }

    // Handles left arrow navigation.
    public void handleArrowLeft() {
        editor.moveCursorLeft();
    }

    // Handles right arrow navigation.
    public void handleArrowRight() {
        editor.moveCursorRight();
    }

    // Handles the Home key.
    public void handleHome() {
        editor.moveCursorToStart();
    }

    // Handles the End key.
    public void handleEnd() {
        editor.moveCursorToEnd();
    }

    // Updates the current selection.
    public void handleSelection(int start, int end) {
        editor.selectText(start, end);
    }

    // Selects the entire document.
    public void handleSelectAll() {
        editor.selectText(0, editor.getLength());
    }

    // Clears the current selection.
    public void handleClearSelection() {
        editor.clearSelection();
    }

    // Copies selected text to the system clipboard.
    public void handleCopy() {
        if (!editor.hasSelection()) {
            return;
        }

        String selectedText = editor.getText().substring(
                editor.getSelectionStart(),
                editor.getSelectionEnd()
        );

        ClipboardContent content = new ClipboardContent();
        content.putString(selectedText);

        clipboard.setContent(content);
    }

    // Cuts selected text and copies it to the clipboard.
    public void handleCut() {
        if (!editor.hasSelection()) {
            return;
        }

        handleCopy();

        editor.deleteText(
                editor.getSelectionStart(),
                editor.getSelectionEnd()
        );
    }

    // Inserts clipboard content.
    public void handlePaste() {
        if (!clipboard.hasString()) {
            return;
        }

        String text = clipboard.getString();
        editor.insertText(text);
    }
}