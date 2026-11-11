
package com.texteditor.rope;

public class RopeLeaf extends RopeNode {

    // Maximum number of characters stored in a leaf.
    public static final int MAX_LEAF_SIZE=512;//Placeholder value

    // Text fragment stored in this leaf.
    private String text;

    // Creates a leaf containing a text fragment.
    public RopeLeaf(String text) {
        if (text.length() <= MAX_LEAF_SIZE) {
            this.text = text;
        } else {
            throw new IllegalArgumentException("Text size exceeds maximum leaf limit.");
        }
    }

    // Returns the fragment stored in the leaf.
    public String getText(){
        return this.text;
    }

    // Returns the number of characters in this leaf.
    @Override
    public int length(){
        return this.text.length();
    }

    // Returns the height of a leaf.
    @Override
    public int height(){
    return 0;
    }

    // Returns the character at a local index.
    @Override
    public char charAt(int index){
        return this.text.charAt(index);
    }

    // Returns the text fragment.
    @Override
    public String toString(){
        return text ;
    }

    // Returns true.
    @Override
    public boolean isLeaf(){
        return true;
    }

}
