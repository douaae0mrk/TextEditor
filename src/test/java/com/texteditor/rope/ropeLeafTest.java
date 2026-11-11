package com.texteditor.rope;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RopeLeafTest {

    @Test
    void testValidLeafCreation() {
        RopeLeaf leaf = new RopeLeaf("Hello");
        assertEquals("Hello", leaf.getText());
        assertEquals(5, leaf.length());
        assertEquals(0, leaf.height());
        assertTrue(leaf.isLeaf());
    }

    @Test
    void testCharAt() {
        RopeLeaf leaf = new RopeLeaf("Java");
        assertEquals('J', leaf.charAt(0));
        assertEquals('v', leaf.charAt(2));
        assertThrows(StringIndexOutOfBoundsException.class, () -> leaf.charAt(10));
    }

    @Test
    void testTooLargeTextThrowsException() {
        // Exceeds the new MAX_LEAF_SIZE limit of 512 characters
        String longText = "A".repeat(513);
        assertThrows(Exception.class, () -> new RopeLeaf(longText));
    }

    @Test
    void testToString() {
        RopeLeaf leaf = new RopeLeaf("Test");
        assertEquals("Test", leaf.toString());
    }
}