
package com.texteditor.rope;

import java.util.ArrayList;
import java.util.List;

public class Rope {
    private RopeNode root;

    // Creates an empty Rope.
    public Rope() {
        this.root = null;
    }

    // Creates a Rope from an existing tree.
    private Rope(RopeNode root) {
        this.root = root;
    }

    // Creates a Rope containing the provided text.
    // to keep it efficient the creation of a leaf in a Rope should be bounded
    // by a maxLength so that the insertions on a certain Leaf would not be expensive.
    public Rope(String text) {
        if (text == null || text.isEmpty()) {
            this.root = null;
        } else {
            this.root = createTree(text);
        }
    }

    // Returns the number of characters stored in the Rope.
    public int length() {
        if (root == null) {
            return 0;
        }
        if (root.isLeaf()) {
            return ((RopeLeaf) root).length();
        }
        // If it's not null and not a leaf, it is a RopeBranch.
        // A RopeBranch typically keeps track of its left subtree's weight
        // plus its right subtree's length.
        return root.length();
    }

    // Returns true if the Rope contains no characters.
    public boolean isEmpty() {
        return (root == null) || (root.length() == 0);
    }

    // Returns the character at the specified index.
    public char charAt(int index) {
        return root.charAt(index);
    }

    // Returns the text between start and end (end exclusive).
    public String substring(int start, int end) {
        StringBuilder s = new StringBuilder();
        for (int i = start; i < end; i++) {
            s.append(root.charAt(i));
        }
        return s.toString();
    }

    // Inserts text at the specified index.
    public void insert(int index, String text) {
        if (text == null || text.isEmpty()) {
            return;
        }
        if (this.root == null) {
            this.root = createTree(text);
            return;
        }
        Rope[] branches = this.split(index);
        RopeNode leftBranch = branches[0].getRoot();
        RopeNode rightBranch = branches[1].getRoot();
        RopeNode temp = RopeBalancer.join(leftBranch, createTree(text));
        this.root = RopeBalancer.join(temp, rightBranch);

    }

    // Deletes the characters in [start, end).
    public void delete(int start, int end) {
        if (start < 0 || end > length() || start >= end) {
            return;
        }
        Rope[] partitionA = this.split(start);
        Rope leftKeep = partitionA[0];
        Rope right = partitionA[1];
        // split the remaining right part at (end - start)
        Rope[] partitionB = right.split(end - start);
        Rope remove = partitionB[0];     // This is the text being deleted
        Rope rightKeep = partitionB[1];  // This is the remaining right part we want to keep
        // 3. Join the left and right kept parts
        this.root = RopeBalancer.join(
                leftKeep != null ? leftKeep.getRoot() : null,
                rightKeep != null ? rightKeep.getRoot() : null
        );
    }


    // Splits the Rope at index into two Ropes.
    // The original Rope remains unchanged.
    public Rope[] split(int index) {
        if (index < 0 || index > length()) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Length: " + length()
            );
        }
        if (index == 0) {
            return new Rope[]{new Rope(), new Rope(this.root)};
        }
        if (index == length()) {
            return new Rope[]{new Rope(this.root), new Rope()};
        }
        RopeNode[] parts = splitNode(this.root, index);
        return new Rope[]{new Rope(parts[0]), new Rope(parts[1])};
    }

    // Concatenates another Rope to the end of this Rope.
    public void concatenate(Rope other) {
        if (other == null || other.isEmpty()) {
            return;
        }
        this.root = RopeBalancer.join(root, other.getRoot());
    }

    // Returns the complete text represented by the Rope.
    @Override
    public String toString() {
        if (root == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        appendToString(root, stringBuilder);
        return stringBuilder.toString();
    }


    // Returns a snapshot of the tree for visualization.
    public RopeNode getRoot() {
        return this.root;
    }

    //HELPER FUNCTIONS

    private static RopeNode createTree(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        List<RopeLeaf> leaves = new ArrayList<>();
        for (int i = 0; i < text.length(); i += RopeLeaf.MAX_LEAF_SIZE) {
            int end = Math.min(
                    i + RopeLeaf.MAX_LEAF_SIZE,
                    text.length()
            );
            leaves.add(new RopeLeaf(text.substring(i, end)));
        }
        return RopeBalancer.buildBalanced(leaves);
    }

    private static RopeNode[] splitNode(RopeNode node, int index) {

        if (node == null) {
            return new RopeNode[]{null, null};
        }

        if (index == 0) {
            return new RopeNode[]{null, node};
        }
        if (index == node.length()) {
            return new RopeNode[]{node, null};
        }
        // splitting a leaf.
        if (node.isLeaf()) {
            RopeLeaf leaf = (RopeLeaf) node;
            String text = leaf.getText();

            String leftText = text.substring(0, index);
            String rightText = text.substring(index);

            RopeNode leftNode = new RopeLeaf(leftText);
            RopeNode rightNode = new RopeLeaf(rightText);
            return new RopeNode[]{leftNode, rightNode};
        }

        // splitting a branch.
        RopeBranch branch = (RopeBranch) node;

        RopeNode leftChild = branch.getLeft();
        RopeNode rightChild = branch.getRight();

        int leftLength = leftChild.length();
        //  1Split exactly between the two children
        if (index == leftLength) {
            return new RopeNode[]{leftChild, rightChild};
        }
        //2Split is inside the left subtree
        if (index < leftLength) {
            RopeNode[] parts = splitNode(leftChild, index);
            RopeNode newRight = RopeBalancer.join(parts[1], rightChild);
            return new RopeNode[]{parts[0], newRight};
        }
        //Split is inside the right subtree.
        else {
            RopeNode[] parts = splitNode(rightChild, index - leftLength);
            RopeNode newLeft = RopeBalancer.join(leftChild, parts[0]);
            return new RopeNode[]{newLeft, parts[1]};
        }
    }

    private static void appendToString(RopeNode node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        if (node.isLeaf()) {
            sb.append(((RopeLeaf) node).getText());
        } else {
            RopeBranch branch = (RopeBranch) node;
            appendToString(branch.getLeft(), sb);
            appendToString(branch.getRight(), sb);
        }
    }
}
