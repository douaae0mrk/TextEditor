
package com.texteditor.rope;

import java.util.List;

public final class RopeBalancer {
    private RopeBalancer() {
    }

    // Returns the height of the tree.
    public static int height(RopeNode root) {
        return (root == null) ? -1 : root.height();
    }

    // Returns height(left) - height(right).
    public static int balanceFactor(RopeNode node) {
        if (node == null || node.isLeaf()) {
            return 0;
        }

        RopeBranch branch = (RopeBranch) node;

        return height(branch.getLeft()) - height(branch.getRight());
    }

    public static RopeNode balance(RopeNode root) {
        if (root == null || root.isLeaf()) {
            return root;
        }
        RopeBranch branch = (RopeBranch) root;

        RopeNode balancedLeft = balance(branch.getLeft());
        RopeNode balancedRight = balance(branch.getRight());
        return join(balancedLeft, balancedRight);
    }

    // Right rotation.
    private static RopeNode rotateRight(RopeBranch y) {
        RopeBranch x = (RopeBranch) y.getLeft();
        RopeNode middleSubtree = x.getRight();
        RopeBranch newRight = new RopeBranch(
                middleSubtree,
                y.getRight()
        );
        return new RopeBranch(
                x.getLeft(),
                newRight
        );
    }

    // Left rotation.
    private static RopeNode rotateLeft(RopeBranch x) {
        RopeBranch y = (RopeBranch) x.getRight();

        RopeNode middleSubtree = y.getLeft();

        RopeBranch newLeft = new RopeBranch(
                x.getLeft(),
                middleSubtree
        );
        return new RopeBranch(
                newLeft,
                y.getRight()
        );
    }

    // Returns true if the entire tree satisfies AVL balance.
    public static boolean isBalanced(RopeNode root) {
        if (root == null || root.isLeaf()) {
            return true;
        }
        RopeBranch branch = (RopeBranch) root;
        return Math.abs(balanceFactor(branch)) <= 1
                && isBalanced(branch.getLeft())
                && isBalanced(branch.getRight());
    }

    // Rebuilds a balanced tree from a collection of leaves.
    public static RopeNode buildBalanced(List<RopeLeaf> leaves) {
        if (leaves == null || leaves.isEmpty()) {
            return null;
        }

        if (leaves.size() == 1) {
            return leaves.get(0);
        }
        int mid = leaves.size() / 2;
        RopeNode leftSubtree = buildBalanced(
                leaves.subList(0, mid)
        );
        RopeNode rightSubtree = buildBalanced(
                leaves.subList(mid, leaves.size())
        );
        return new RopeBranch(leftSubtree, rightSubtree);
    }

    private static RopeNode balanceSingle(RopeBranch branch) {
        int bf = balanceFactor(branch);
        // Left-heavy tree.
        if (bf > 1) {
            RopeBranch leftChild =
                    (RopeBranch) branch.getLeft();
            // Left-Right case.
            if (balanceFactor(leftChild) < 0) {
                RopeNode rotatedLeft = rotateLeft(leftChild);
                branch = new RopeBranch(
                        rotatedLeft,
                        branch.getRight()
                );
            }
            // Left-Left case.
            return rotateRight(branch);
        }
        // Right-heavy tree.
        if (bf < -1) {
            RopeBranch rightChild =
                    (RopeBranch) branch.getRight();
            // Right-Left case.
            if (balanceFactor(rightChild) > 0) {
                RopeNode rotatedRight = rotateRight(rightChild);
                branch = new RopeBranch(
                        branch.getLeft(),
                        rotatedRight
                );
            }
            // Right-Right case.
            return rotateLeft(branch);
        }
        return branch;
    }

    // Represents the result of removing an extreme leaf.
    private static class ExtractedLeaf {
        private final RopeNode remainingTree;
        private final RopeLeaf leaf;
        private ExtractedLeaf(RopeNode remainingTree, RopeLeaf leaf) {
            this.remainingTree = remainingTree;
            this.leaf = leaf;
        }
    }

    // Returns the rightmost leaf without modifying the tree.
    private static RopeLeaf rightmostLeaf(RopeNode node) {

        if (node.isLeaf()) {
            return (RopeLeaf) node;
        }

        RopeBranch branch = (RopeBranch) node;

        return rightmostLeaf(branch.getRight());
    }
    // Returns the leftmost leaf without modifying the tree.
    private static RopeLeaf leftmostLeaf(RopeNode node) {

        if (node.isLeaf()) {
            return (RopeLeaf) node;
        }

        RopeBranch branch = (RopeBranch) node;

        return leftmostLeaf(branch.getLeft());
    }

    // Removes the rightmost leaf and returns the remaining tree.
    private static ExtractedLeaf removeRightmostLeaf(RopeNode node) {
        if (node.isLeaf()) {
            return new ExtractedLeaf(null, (RopeLeaf) node);
        }
        RopeBranch branch = (RopeBranch) node;
        ExtractedLeaf extracted =
                removeRightmostLeaf(branch.getRight());
        RopeNode remaining = joinWithoutMerge(
                branch.getLeft(),
                extracted.remainingTree
        );
        return new ExtractedLeaf(
                remaining,
                extracted.leaf
        );
    }

    // Removes the leftmost leaf and returns the remaining tree.
    private static ExtractedLeaf removeLeftmostLeaf(RopeNode node) {
        if (node.isLeaf()) {
            return new ExtractedLeaf(null, (RopeLeaf) node);
        }
        RopeBranch branch = (RopeBranch) node;
        ExtractedLeaf extracted =
                removeLeftmostLeaf(branch.getLeft());
        RopeNode remaining = joinWithoutMerge(
                extracted.remainingTree,
                branch.getRight()
        );
        return new ExtractedLeaf(
                remaining,
                extracted.leaf
        );
    }

    // Combines two trees while preserving their text order.
    // First attempts to merge their neighboring boundary leaves.
    // Assumes both input trees are AVL-balanced.
    public static RopeNode join(RopeNode left, RopeNode right) {

        if (left == null) {
            return right;
        }

        if (right == null) {
            return left;
        }

        // Find the two leaves next to the joining boundary.
        RopeLeaf leftBoundary = rightmostLeaf(left);
        RopeLeaf rightBoundary = leftmostLeaf(right);

        String leftText = leftBoundary.getText();
        String rightText = rightBoundary.getText();

        // Merge the boundary leaves if their combined text fits.
        if (leftText.length() + rightText.length()
                <= RopeLeaf.MAX_LEAF_SIZE) {

            ExtractedLeaf extractedLeft =
                    removeRightmostLeaf(left);

            ExtractedLeaf extractedRight =
                    removeLeftmostLeaf(right);

            RopeLeaf mergedLeaf = new RopeLeaf(
                    leftText + rightText
            );
            RopeNode result = joinWithoutMerge(
                    extractedLeft.remainingTree,
                    mergedLeaf
            );
            return joinWithoutMerge(
                    result,
                    extractedRight.remainingTree
            );
        }

        // If merging is impossible, perform the ordinary AVL join.
        return joinWithoutMerge(left, right);
    }


    private static RopeNode joinWithoutMerge(
            RopeNode left,
            RopeNode right) {

        if (left == null) {
            return right;
        }

        if (right == null) {
            return left;
        }

        int leftHeight = height(left);
        int rightHeight = height(right);
        if (Math.abs(leftHeight - rightHeight) <= 1) {
            return new RopeBranch(left, right);
        }

        if (leftHeight > rightHeight) {
            RopeBranch leftBranch = (RopeBranch) left;

            RopeNode joinedRight = joinWithoutMerge(
                    leftBranch.getRight(),
                    right
            );
            RopeBranch rebuilt = new RopeBranch(
                    leftBranch.getLeft(),
                    joinedRight
            );
            return balanceSingle(rebuilt);
        }
        // Right tree is significantly taller.
        RopeBranch rightBranch = (RopeBranch) right;
        // Join into the left spine of the taller tree.
        RopeNode joinedLeft = joinWithoutMerge(
                left,
                rightBranch.getLeft()
        );
        RopeBranch rebuilt = new RopeBranch(
                joinedLeft,
                rightBranch.getRight()
        );
        return balanceSingle(rebuilt);
    }
}