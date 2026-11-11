package com.texteditor.rope;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RopeBalancerTest {

    // Helper to create a leaf from a string chunk
    private RopeLeaf leaf(String text) {
        return new RopeLeaf(text);
    }

    @Test
    void testNullAndEdgeCases() {
        assertNull(RopeBalancer.join(null, null));

        RopeLeaf node = leaf("hello");
        assertEquals(node, RopeBalancer.join(null, node));
        assertEquals(node, RopeBalancer.join(node, null));

        assertEquals(-1, RopeBalancer.height(null));
        assertTrue(RopeBalancer.isBalanced(null));
        assertTrue(RopeBalancer.isBalanced(node));
    }

    @Test
    void testBuildBalancedAndIsBalanced() {
        List<RopeLeaf> leaves = Arrays.asList(
                leaf("a"), leaf("b"), leaf("c"), leaf("d"),
                leaf("e"), leaf("f"), leaf("g")
        );

        RopeNode root = RopeBalancer.buildBalanced(leaves);

        assertNotNull(root);
        assertTrue(RopeBalancer.isBalanced(root), "Built tree should satisfy AVL balance");
    }

    @Test
    void testJoinCompatibleHeights() {
        // Two balanced trees of height 0 (single leaves)
        RopeNode left = leaf("left");
        RopeNode right = leaf("right");

        RopeNode joined = RopeBalancer.join(left, right);

        assertTrue(RopeBalancer.isBalanced(joined), "Joined tree with compatible heights should be balanced");
        assertEquals(1, RopeBalancer.height(joined));
    }

    @Test
    void testJoinSignificantlyDifferentHeightsLeftTaller() {
        // Build a taller left tree (height 2)
        List<RopeLeaf> leftLeaves = Arrays.asList(leaf("1"), leaf("2"), leaf("3"), leaf("4"));
        RopeNode leftTree = RopeBalancer.buildBalanced(leftLeaves); // height 2

        // Single leaf right tree (height 0)
        RopeNode rightTree = leaf("5");

        // Heights differ by 2 (> 1), triggering Case 2 (Left tree significantly taller)
        RopeNode joined = RopeBalancer.join(leftTree, rightTree);

        assertTrue(RopeBalancer.isBalanced(joined), "Join must rebalance when left tree is much taller");
    }

    @Test
    void testJoinSignificantlyDifferentHeightsRightTaller() {
        // Single leaf left tree (height 0)
        RopeNode leftTree = leaf("1");

        // Build a taller right tree (height 2)
        List<RopeLeaf> rightLeaves = Arrays.asList(leaf("2"), leaf("3"), leaf("4"), leaf("5"));
        RopeNode rightTree = RopeBalancer.buildBalanced(rightLeaves); // height 2

        // Heights differ by 2 (> 1), triggering Case 3 (Right tree significantly taller)
        RopeNode joined = RopeBalancer.join(leftTree, rightTree);

        assertTrue(RopeBalancer.isBalanced(joined), "Join must rebalance when right tree is much taller");
    }

    @Test
    void testBalanceUnbalancedTree() {
        // Manually construct a heavily skewed (unbalanced) tree: (((A, B), C), D)
        RopeNode root = new RopeBranch(
                new RopeBranch(
                        new RopeBranch(leaf("A"), leaf("B")),
                        leaf("C")
                ),
                leaf("D")
        );

        assertFalse(RopeBalancer.isBalanced(root), "Manually constructed tree should start unbalanced");

        RopeNode balanced = RopeBalancer.balance(root);

        assertTrue(RopeBalancer.isBalanced(balanced), "Balance function must convert skewed tree into an AVL-balanced tree");
    }
}