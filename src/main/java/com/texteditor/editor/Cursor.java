package com.texteditor.editor;

/**
 *  Cursor represents the current insertion position in the document
 *  using zero-based indexing.
 *
 *  It is responsible for tracking the cursor position and providing
 *  basic cursor movement operations.
 *
 *  Note that the cursor does not modify the document's text.
 */

public class Cursor {
    //position of the cursor
    private int position;

    // Creates a cursor at position 0.
    public Cursor(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Creates a cursor at the specified position.
    public Cursor(int position){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the current cursor position.
    public int getPosition(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Sets the cursor position.
    public void setPosition(int position){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Moves the cursor one position to the left.
    public void moveLeft(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Moves the cursor one position to the right.
    public void moveRight(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Moves the cursor to the beginning of the document.
    public void moveToStart(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Moves the cursor to the end of the document.
    public void moveToEnd(int documentLength){
        throw new UnsupportedOperationException("Not implemented yet");
    }

}

