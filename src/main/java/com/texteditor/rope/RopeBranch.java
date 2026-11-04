
package com.texteditor.rope;

public class RopeBranch extends RopeNode {

    // Left subtree.
    private RopeNode left;

    // Right subtree.
    private RopeNode right;

    // Number of characters in the left subtree.
    private int weight;

    // Creates a branch from two subtrees.
    public RopeBranch(RopeNode left, RopeNode right){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the left child.
    public RopeNode getLeft(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the right child.
    public RopeNode getRight(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the weight of the left subtree.
    public int getWeight(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns total characters in both subtrees.
    @Override
    public int length(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the height of the subtree.
    @Override
    public int height(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Finds a character by navigating through the tree.
    @Override
    public char charAt(int index){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns the concatenated text of both subtrees.
    @Override
    public String toString(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Returns false.
    @Override
    public boolean isLeaf(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
