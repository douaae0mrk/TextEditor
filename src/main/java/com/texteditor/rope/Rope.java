
package com.texteditor.rope;

public class Rope {

    // Creates an empty Rope.
    public Rope(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Creates a Rope containing the provided text.
    public Rope(String text){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the number of characters stored in the Rope.
    public int length(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns true if the Rope contains no characters.
    public boolean isEmpty(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the character at the specified index.
    public char charAt(int index){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the text between start and end (end exclusive).
    public String substring(int start, int end){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Inserts text at the specified index.
    public void insert(int index, String text){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Deletes the characters in [start, end).
    public void delete(int start, int end){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Splits the Rope at index into two Ropes.
    // The original Rope remains unchanged.
    public Rope[] split(int index){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Concatenates another Rope to the end of this Rope.
    public void concatenate(Rope other){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the complete text represented by the Rope.
    @Override
    public String toString(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns a snapshot of the tree for visualization.
    public RopeNode getRoot(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
