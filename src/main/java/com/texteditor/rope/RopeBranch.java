
package com.texteditor.rope;

public class RopeBranch extends RopeNode {
    private RopeNode left;
    private RopeNode right;

    // Number of characters in the left subtree.
    private int weight;
    private int height;
    private int length;

    // Creates a branch from two subtrees.
    public RopeBranch(RopeNode left, RopeNode right){
        this.left=left;
        this.right=right;
        this.weight=left.length();
        this.height= Math.max(left.height(),right.height())+1;
        this.length=  weight+right.length();
    }

    // Returns the left child.
    public RopeNode getLeft(){
        return this.left;
    }

    // Returns the right child.
    public RopeNode getRight(){
    return this.right;
    }

    // Returns the weight of the left subtree.
    public int getWeight(){
    return this.weight;
    }

    // Returns total characters in both subtrees.
    @Override
    public int length(){
        return this.length;
    }

    // Returns the height of the subtree.
    @Override
    public int height() {
        return height;
    }

    // Finds a character by navigating through the tree.
    @Override
    public char charAt(int index){
        if(left.length()>index){
            return left.charAt(index);
        }
        else{
            return right.charAt(index-this.weight);
        }
    }

    // Returns the concatenated text of both subtrees.
    @Override
    public String toString(){
        return left.toString()+right.toString();
    }

    // Returns false.
    @Override
    public boolean isLeaf(){
        return false;
    }

}
