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
    public Selection();

    // Creates a selection with the specified boundaries.
    public Selection(int start, int end);

    // Returns the starting position.
    public int getStart();

    // Returns the ending position.
    public int getEnd();

    // Sets the selection boundaries.
    public void setSelection(int start, int end);

    // Clears the selection.
    public void clear();

    // Returns true if no characters are selected.
    public boolean isEmpty();

    // Returns the number of selected characters.
    public int getLength();

}