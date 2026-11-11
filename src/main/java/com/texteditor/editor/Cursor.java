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
        this.position = 0;
    }

    // Creates a cursor at the specified position.
    public Cursor(int position) throws IllegalArgumentException{
        // position can not be negative
        if (position < 0) throw new IllegalArgumentException("Cursor Position must be a positive integer");

        this.position = position;

    }

    // Returns the current cursor position.
    public int getPosition(){

        return position;
    }

    // Sets the cursor position.
    public void setPosition(int position) throws IllegalArgumentException{
        if (position < 0 ) throw new IllegalArgumentException("Cursor Position must be a positive integer");

        this.position = position;
    }

    // Moves the cursor one position to the left.
    public void moveLeft(){

        if( position != 0) position--;
    }

    // Moves the cursor one position to the right.
    public void moveRight(){
        position++;
    }

    // Moves the cursor to the beginning of the document.
    public void moveToStart(){
        position = 0;
    }

    // Moves the cursor to the end of the document.
    public void moveToEnd(int documentLength) throws IllegalArgumentException{
        if (documentLength < 0 ) throw new IllegalArgumentException("A document's length can not be negative. ");
        position = documentLength;// the position where a new char would be added
    }

}

