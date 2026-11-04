
package com.texteditor.rope;

public class RopeLeaf extends RopeNode {

    // Maximum number of characters stored in a leaf.
    private static final int MAX_LEAF_SIZE;

    // Text fragment stored in this leaf.
    private String text;

    // Creates a leaf containing a text fragment.
    public RopeLeaf(String text){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the fragment stored in the leaf.
    public String getText(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the number of characters in this leaf.
    @Override
    public int length(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the height of a leaf.
    @Override
    public int height(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the character at a local index.
    @Override
    public char charAt(int index){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the text fragment.
    @Override
    public String toString(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns true.
    @Override
    public boolean isLeaf(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
