package com.texteditor.editor;

import com.texteditor.rope.Rope;

/**
 * Editor represents the core editing component of the text editor.
 *
 * It coordinates text modifications, cursor movement, and selection
 * management while interacting with the Rope data structure.
 *
 * Acts as the central interface between the document's underlying
 * text storage and the other editor components.
 */
public class Editor {

    private final Rope rope;
    private final Cursor cursor;
    private final Selection selection;

    // Creates an Editor using the provided Rope.
    public Editor(Rope rope) {
        if (rope == null) {
            throw new IllegalArgumentException("Rope cannot be null");
        }

        this.rope = rope;
        this.cursor = new Cursor();
        this.selection = new Selection();
    }

    // Inserts text at the cursor position.
    // If text is selected, it replaces the selection.
    public void insertText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }

        int position = cursor.getPosition();

        // Replace selected text if there is an active selection.
        if (hasSelection()) {
            position = selection.getStart();
            rope.delete(selection.getStart(), selection.getEnd());
        }

        rope.insert(position, text);

        setCursorPosition(position + text.length());
        clearSelection();
    }

    // Deletes the text between start and end (end exclusive).
    public void deleteText(int start, int end) {
        rope.delete(start, end);

        setCursorPosition(start);
        clearSelection();
    }

    // Replaces the text between start and end with the provided text.
    public void replaceText(int start, int end, String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }

        rope.delete(start, end);
        rope.insert(start, text);

        setCursorPosition(start + text.length());
        clearSelection();
    }

    // Returns the complete text stored in the editor.
    public String getText() {
        return rope.toString();
    }

    // Returns the number of characters in the document.
    public int getLength() {
        return rope.length();
    }

    // Returns the current cursor position.
    public int getCursorPosition() {
        return cursor.getPosition();
    }

    // Returns the start of the current selection.
    public int getSelectionStart() {
        return selection.getStart();
    }

    // Returns the end of the current selection.
    public int getSelectionEnd() {
        return selection.getEnd();
    }

    // Sets the cursor to the specified position.
    public void setCursorPosition(int position) {
        if (position < 0 || position > rope.length()) {
            throw new IllegalArgumentException("Invalid cursor position");
        }

        cursor.setPosition(position);
    }

    // Moves the cursor one position to the left.
    public void moveCursorLeft() {
        cursor.moveLeft();
        clearSelection();
    }

    // Moves the cursor one position to the right.
    public void moveCursorRight() {
        if (cursor.getPosition() < rope.length()) {
            cursor.moveRight();
        }
        clearSelection();
    }

    // Moves the cursor to the beginning of the document.
    public void moveCursorToStart() {
        cursor.moveToStart();
        clearSelection();
    }

    // Moves the cursor to the end of the document.
    public void moveCursorToEnd() {
        cursor.moveToEnd(rope.length());
        clearSelection();
    }

    // Updates the current selection.
    public void selectText(int start, int end) {
        if (start < 0 || end < start || end > rope.length()) {
            throw new IllegalArgumentException("Invalid selection range");
        }

        selection.setSelection(start, end);
        setCursorPosition(end);
    }

    // Returns true if text is currently selected.
    public boolean hasSelection() {
        return !selection.isEmpty();
    }

    // Clears the current selection at the cursor position.
    public void clearSelection() {
        selection.clear(cursor.getPosition());
    }
}