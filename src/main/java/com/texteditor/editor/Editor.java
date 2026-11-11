package com.texteditor.editor;

import com.texteditor.rope.Rope;

/**
 *  Editor represents the core editing component of the text editor.
 *
 *  It is responsible for coordinating text modifications, managing
 *  the cursor and selection, and interacting with the Rope
 *  data structure to manipulate and retrieve document content.
 *
 *  Acts as the central interface between the document's
 *  underlying text storage and the other editor components.
 */

public class Editor {

    private final Rope rope;
    private final Cursor cursor;
    private final Selection selection;

    // Creates an Editor using the provided Rope.
    public Editor(Rope rope){
        this.rope = rope;
        cursor = new Cursor();
        selection = new Selection();
    }

    // Inserts text at the current cursor position.
    public void insertText(String text){
        int position = getCursorPosition();
        rope.insert(position, text);
        setCursorPosition(position + text.length());
        clearSelection();
    }

    // Deletes the text between start and end (end exclusive).
    public void deleteText(int start, int end) throws IllegalArgumentException{
        rope.delete(start , end);
        setCursorPosition(start);
        clearSelection();
    }

    // Replaces the text between start and end with the provided text.
    public void replaceText(int start, int end, String text){
       deleteText(start, end);
       insertText(text);
    }

    // Returns the complete text stored in the editor.
    public String getText(){
        return rope.toString();
    }

    // Returns the number of characters in the document.
    public int getLength(){
        return rope.length();
    }

    // Cursor information
    public int getCursorPosition() {
        return cursor.getPosition();
    }

    // Selection information
    public int getSelectionStart() {
        return selection.getStart();
    }

    public int getSelectionEnd() {
        return selection.getEnd();
    }

    // Sets the cursor to the specified position.
    public void setCursorPosition(int position) throws IllegalArgumentException{
        if (position > rope.length())  throw new IllegalArgumentException( "Invalid Cursor Position");
        cursor.setPosition(position);
    }

    // Returns true if text is currently selected.
    public boolean hasSelection(){
        return !selection.isEmpty();
    }

    // Clears the current selection.
    public void clearSelection(){
        selection.clear(cursor.getPosition());
    }

}