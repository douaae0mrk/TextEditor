
package com.texteditor.rope;

public abstract class RopeNode {

    // Returns the number of characters represented by this node.
    public abstract int length();

    // Returns the height of the subtree rooted at this node.
    public abstract int height();

    // Returns the character at a local index.
    public abstract char charAt(int index);

    // Returns the text represented by this subtree.
    public abstract String toString();

    // Indicates whether this node is a leaf.
    public abstract boolean isLeaf();

}
