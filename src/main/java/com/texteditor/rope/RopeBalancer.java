
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
        // Join the already-balanced subtrees.
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


    // Combines two trees while preserving their text order.
    // Assumes both input trees are AVL-balanced.
    public static RopeNode join(RopeNode left, RopeNode right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        int leftHeight = height(left);
        int rightHeight = height(right);
        // Heights are compatible: directly create a branch.
        if (Math.abs(leftHeight - rightHeight) <= 1) {
            return new RopeBranch(left, right);
        }
        // Left tree is significantly taller.
        if (leftHeight > rightHeight) {
            RopeBranch leftBranch = (RopeBranch) left;
            // Join into the right spine of the taller tree.
            RopeNode joinedRight = join(
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
        RopeNode joinedLeft = join(
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