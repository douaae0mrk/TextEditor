
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
        // if it's not null and not a leaf so  it is a RopeBranch.
        return root.length();
    }

    // Returns true if the Rope contains no characters.
    public boolean isEmpty() {
        return (root == null) || (root.length() == 0);
    }

    // Returns the character at the specified index.
    public char charAt(int index) {
        if (index < 0 || index >= length()) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Length: " + length()
            );
        }
        return root.charAt(index);
    }

    // Returns the text between start and end (end exclusive).
    /* ORIGINAL TRIAL BUT NOT OPTIMIZED AFTER CHECKING BENCHMARK
    public String substring(int start, int end) {
        if (start < 0 || end > length() || start > end) {
            throw new IndexOutOfBoundsException();
        }
        StringBuilder s = new StringBuilder();
        for (int i = start; i < end; i++) {
            s.append(root.charAt(i));
        }
        return s.toString();
    }*/
    public String substring(int start, int end) {

        if (start < 0 || end > length() || start > end) {
            throw new IndexOutOfBoundsException(
                    "Invalid range: [" + start + ", " + end + ")"
            );
        }

        char[] result = new char[end - start];

        copySubstring(root, start, end, result, 0);

        return new String(result);
    }

    // Inserts text at the specified index.

    public void insert(int index, String text) {
        int currentLength = length();
        if (index < 0 || index > currentLength) {
            throw new IndexOutOfBoundsException("Index:" + index + ", Length: " + currentLength);
        }
        if (text == null || text.isEmpty()) {
            return;
        }
        RopeNode insertedTree = createTree(text);
        // Empty Rope
        if (root == null) {
            root = insertedTree;
            return;
        }
        // insert at the beginning
        if (index == 0) {
            root = RopeBalancer.join(insertedTree, root);
            return;
        }
        // insert at the end
        if (index == currentLength) {
            root = RopeBalancer.join(root, insertedTree);
            return;
        }
        // insert in the middle
        RopeNode[] parts = splitNode(root, index);
        root = RopeBalancer.join(RopeBalancer.join(parts[0], insertedTree), parts[1]);
    }


    // Deletes the characters in [start, end).

    public void delete(int start, int end) {
        int currentLength = length();
        if (start < 0 || end > currentLength || start > end) {
            throw new IndexOutOfBoundsException(
                    "Invalid range: [" + start + ", " + end + ")"
            );
        }
        if (start == end) {
            return;
        }
        // delete everything
        if (start == 0 && end == currentLength) {
            root = null;
            return;
        }

        // Delete a prefix
        if (start == 0) {
            RopeNode[] parts = splitNode(root, end);
            root = parts[1];
            return;
        }

        // Delete a suffix
        if (end == currentLength) {
            RopeNode[] parts = splitNode(root, start);
            root = parts[0];
            return;
        }

        // Delete from the middle
        RopeNode[] firstSplit = splitNode(root, start);
        RopeNode leftKeep = firstSplit[0];
        RopeNode right = firstSplit[1];
        RopeNode[] secondSplit = splitNode(right, end - start);
        RopeNode rightKeep = secondSplit[1];
        root = RopeBalancer.join(leftKeep, rightKeep);
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
        if (other == null || other.root == null) {
            return;
        }
        if (this.root == null) {
            this.root = other.root;
            return;
        }
        this.root = RopeBalancer.join(this.root, other.root);
    }

    // Returns the complete text represented by the Rope.
    @Override
    public String toString() {
        if (root == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(this.length());//improved
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
        int maxLeafSize = RopeLeaf.MAX_LEAF_SIZE;
        int numberOfLeaves =
                (text.length() + maxLeafSize - 1) / maxLeafSize;
        List<RopeLeaf> leaves = new ArrayList<>(numberOfLeaves);//before we did not precise the capacity but this is more optimized(no reallocation needed )
        for (int i = 0; i < text.length(); i += maxLeafSize) {
            int end = Math.min(i + maxLeafSize, text.length());

            leaves.add(new RopeLeaf(text.substring(i, end)));
        }
        return RopeBalancer.buildBalanced(leaves);
    }

    private static int copySubstring(RopeNode node,int start,int end,char[] destination,int destinationIndex) {
        if (node == null || start == end) {
            return destinationIndex;
        }
        if (node.isLeaf()) {
            RopeLeaf leaf = (RopeLeaf) node;
            String text = leaf.getText();
            text.getChars(start, end, destination, destinationIndex);
            return destinationIndex + (end - start);
        }
        RopeBranch branch = (RopeBranch) node;
        int leftLength = branch.getLeft().length();
        if (start < leftLength) {
            int leftEnd = Math.min(end, leftLength);
            destinationIndex = copySubstring(branch.getLeft(), start,leftEnd,destination,destinationIndex);
        }
        if (end > leftLength) {

            int rightStart = Math.max(0, start - leftLength);
            int rightEnd = end - leftLength;

            destinationIndex = copySubstring(branch.getRight(),rightStart,rightEnd,destination,destinationIndex
            );
        }
        return destinationIndex;
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
