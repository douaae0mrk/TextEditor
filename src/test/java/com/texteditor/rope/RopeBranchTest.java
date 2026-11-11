package com.texteditor.rope;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RopeBranchTest {

    @Test
    void testBranchOperations() {
        RopeLeaf left = new RopeLeaf("Hello ");
        RopeLeaf right = new RopeLeaf("World!");
        RopeBranch branch = new RopeBranch(left, right);

        // Length: "Hello " (6) + "World!" (6) = 12
        assertEquals(12, branch.length());

        // Height: 1 + max(0, 0) = 1
        assertEquals(1, branch.height());

        // Branch should not be a leaf
        assertFalse(branch.isLeaf());

        // charAt lookups spanning both left and right subtrees
        assertEquals('H', branch.charAt(0));
        assertEquals(' ', branch.charAt(5));
        assertEquals('W', branch.charAt(6));
        assertEquals('!', branch.charAt(11));

        // toString concatenation (now raw text fragments)
        assertEquals("Hello World!", branch.toString());
    }

    @Test
    void testNestedBranches(){
        RopeLeaf l1 = new RopeLeaf("A");
        RopeLeaf l2 = new RopeLeaf("B");
        RopeBranch b1 = new RopeBranch(l1, l2); // Represents "AB", height 1

        RopeLeaf l3 = new RopeLeaf("C");
        RopeBranch b2 = new RopeBranch(b1, l3); // Represents "ABC", height 2

        assertEquals(3, b2.length());
        assertEquals(2, b2.height());
        assertEquals('A', b2.charAt(0));
        assertEquals('B', b2.charAt(1));
        assertEquals('C', b2.charAt(2));
        assertEquals("ABC", b2.toString());
    }
}