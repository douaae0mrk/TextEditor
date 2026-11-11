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
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Creates a selection with the specified boundaries.
    public Selection(int start, int end){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the starting position.
    public int getStart(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the ending position.
    public int getEnd(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Sets the selection boundaries.
    public void setSelection(int start, int end){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Clears the selection.
    public void clear(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns true if no characters are selected.
    public boolean isEmpty(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the number of selected characters.
    public int getLength(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

}