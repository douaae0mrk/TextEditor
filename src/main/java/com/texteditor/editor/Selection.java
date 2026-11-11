package com.texteditor.editor;

/**
 *  Selection represents a range of selected text within the document
 *  using zero-based indexing and half-open intervals [start, end).
 *
 *  It is responsible for tracking the selection boundaries and
 *  providing basic selection management operations.
 *
 *  The Selection does not modify the document's text.
 */

public class Selection {

    private int start;
    private int end;

    // Creates an empty selection.
    public Selection(){
        start = 0;
        end = 0;
    }

    // Creates a selection with the specified boundaries.
    public Selection(int start, int end) throws IllegalArgumentException{

        if(start <0 || (end < start))  throw new IllegalArgumentException("Invalid selection boundaries");
        this.start  = start;
        this.end = end;
    }

    // Returns the starting position.
    public int getStart(){
        return start;
    }

    // Returns the ending position.
    public int getEnd(){
        return end;
    }

    // Sets the selection boundaries.
    public void setSelection(int start, int end) throws IllegalArgumentException{

        if(start <0 || (end < start))  throw new IllegalArgumentException( "Invalid selection boundaries");
        this.start  = start;
        this.end = end;
    }

    // Clears the selection.
    public void clear(int cursorPosition) throws IllegalArgumentException{
        if (cursorPosition <0 )  throw new IllegalArgumentException("Invalid Cursor Position ");
        start = cursorPosition;
        end = cursorPosition;

    }

    // Returns true if no characters are selected.
    public boolean isEmpty(){
        return start == end ;
    }

    // Returns the number of selected characters.
    public int getLength(){
        return end - start;
    }

}