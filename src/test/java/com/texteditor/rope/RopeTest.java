
package com.texteditor.rope;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RopeTest {
    @Test
    void emptyConstructorCreatesEmptyRope() {

        Rope rope = new Rope();

        assertEquals(0, rope.length());
        assertTrue(rope.isEmpty());
        assertEquals("", rope.toString());
        assertNull(rope.getRoot());
    }

    @Test
    void stringConstructorCreatesRope() {

        Rope rope = new Rope("Hello World");

        assertEquals(11, rope.length());
        assertFalse(rope.isEmpty());
        assertEquals("Hello World", rope.toString());
    }

    @Test
    void stringConstructorHandlesEmptyString() {

        Rope rope = new Rope("");

        assertTrue(rope.isEmpty());
        assertEquals(0, rope.length());
        assertNull(rope.getRoot());
    }

    @Test
    void stringConstructorHandlesNull() {

        Rope rope = new Rope(null);

        assertTrue(rope.isEmpty());
        assertEquals(0, rope.length());
    }

    @Test
    void stringConstructorHandlesLargeText() {

        String text = "A".repeat(100_000);

        Rope rope = new Rope(text);

        assertEquals(text.length(), rope.length());
        assertEquals(text, rope.toString());
        assertTrue(RopeBalancer.isBalanced(rope.getRoot()));
        assertLeavesRespectMaximumSize(rope.getRoot());
    }

    @Test
    void lengthReturnsCorrectValue() {

        Rope rope = new Rope("ABCDE");

        assertEquals(5, rope.length());

        rope.insert(2, "XYZ");

        assertEquals(8, rope.length());
    }

    @Test
    void isEmptyReturnsCorrectValue() {

        Rope rope = new Rope();

        assertTrue(rope.isEmpty());

        rope.insert(0, "Hello");

        assertFalse(rope.isEmpty());

        rope.delete(0, 5);

        assertTrue(rope.isEmpty());
    }
    @Test
    void charAtReturnsCorrectCharacters() {

        Rope rope = new Rope("ABCDEFGHIJ");

        assertEquals('A', rope.charAt(0));
        assertEquals('E', rope.charAt(4));
        assertEquals('J', rope.charAt(9));
    }

    @Test
    void charAtWorksAcrossDifferentLeaves() {

        String text = "A".repeat(2000);

        Rope rope = new Rope(text);

        assertEquals('A', rope.charAt(0));
        assertEquals('A', rope.charAt(511));
        assertEquals('A', rope.charAt(512));
        assertEquals('A', rope.charAt(1500));
        assertEquals('A', rope.charAt(1999));
    }

    @Test
    void charAtRejectsInvalidIndexes() {

        Rope rope = new Rope("Hello");

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> rope.charAt(-1)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> rope.charAt(5)
        );
    }


    @Test
    void substringReturnsCorrectText() {

        Rope rope = new Rope("ABCDEFGHIJ");

        assertEquals("BCD", rope.substring(1, 4));
        assertEquals("EFGHI", rope.substring(4, 9));
        assertEquals("ABCDEFGHIJ", rope.substring(0, 10));
        assertEquals("", rope.substring(3, 3));
    }

    @Test
    void substringWorksAcrossLeaves() {

        String text = "A".repeat(1000);

        Rope rope = new Rope(text);

        assertEquals(
                text.substring(400, 700),
                rope.substring(400, 700)
        );
    }

    @Test
    void insertAtBeginning() {

        Rope rope = new Rope("World");

        rope.insert(0, "Hello ");

        assertEquals("Hello World", rope.toString());
    }

    @Test
    void insertAtEnd() {

        Rope rope = new Rope("Hello");

        rope.insert(5, " World");

        assertEquals("Hello World", rope.toString());
    }

    @Test
    void insertInMiddle() {

        Rope rope = new Rope("ABCD");

        rope.insert(2, "XY");

        assertEquals("ABXYCD", rope.toString());
    }

    @Test
    void insertIntoEmptyRope() {

        Rope rope = new Rope();

        rope.insert(0, "Hello");

        assertEquals("Hello", rope.toString());
        assertEquals(5, rope.length());
    }

    @Test
    void insertEmptyTextDoesNothing() {

        Rope rope = new Rope("Hello");

        rope.insert(2, "");

        assertEquals("Hello", rope.toString());
    }

    @Test
    void insertNullTextDoesNothing() {

        Rope rope = new Rope("Hello");

        rope.insert(2, null);

        assertEquals("Hello", rope.toString());
    }

    @Test
    void insertLargeText() {

        Rope rope = new Rope("START");

        String inserted = "X".repeat(100_000);

        rope.insert(2, inserted);

        assertEquals("ST" + inserted + "ART", rope.toString());
        assertTrue(RopeBalancer.isBalanced(rope.getRoot()));
        assertLeavesRespectMaximumSize(rope.getRoot());
    }
    @Test
    void deleteFromBeginning() {

        Rope rope = new Rope("ABCDEFGHIJ");

        rope.delete(0, 3);

        assertEquals("DEFGHIJ", rope.toString());
    }

    @Test
    void deleteFromEnd() {

        Rope rope = new Rope("ABCDEFGHIJ");

        rope.delete(7, 10);

        assertEquals("ABCDEFG", rope.toString());
    }

    @Test
    void deleteFromMiddle() {

        Rope rope = new Rope("ABCDEFGHIJ");

        rope.delete(3, 7);

        assertEquals("ABCHIJ", rope.toString());
    }

    @Test
    void deleteEntireText() {

        Rope rope = new Rope("HELLO");

        rope.delete(0, 5);

        assertTrue(rope.isEmpty());
        assertEquals("", rope.toString());
        assertNull(rope.getRoot());
    }

    @Test
    void deleteInvalidRangeDoesNothing() {

        Rope rope = new Rope("HELLO");

        rope.delete(-1, 2);
        rope.delete(2, 10);
        rope.delete(4, 2);

        assertEquals("HELLO", rope.toString());
    }

    @Test
    void deleteLargeRange() {

        String text = "A".repeat(100_000);

        Rope rope = new Rope(text);

        rope.delete(10_000, 90_000);

        assertEquals(20_000, rope.length());
        assertEquals("A".repeat(20_000), rope.toString());
        assertTrue(RopeBalancer.isBalanced(rope.getRoot()));
    }

    @Test
    void splitInsideText() {

        Rope rope = new Rope("ABCDEFGHIJ");

        Rope[] result = rope.split(4);

        assertEquals("ABCD", result[0].toString());
        assertEquals("EFGHIJ", result[1].toString());
    }

    @Test
    void splitAtBeginning() {

        Rope rope = new Rope("HELLO");

        Rope[] result = rope.split(0);

        assertEquals("", result[0].toString());
        assertEquals("HELLO", result[1].toString());
    }

    @Test
    void splitAtEnd() {

        Rope rope = new Rope("HELLO");

        Rope[] result = rope.split(5);

        assertEquals("HELLO", result[0].toString());
        assertEquals("", result[1].toString());
    }

    @Test
    void splitDoesNotModifyOriginal() {

        Rope rope = new Rope("ABCDEFGHIJ");

        rope.split(5);

        assertEquals("ABCDEFGHIJ", rope.toString());
    }

    @Test
    void splitLargeRopeMaintainsBalance() {

        String text = "A".repeat(100_000);

        Rope rope = new Rope(text);

        Rope[] result = rope.split(45_000);

        assertEquals(45_000, result[0].length());
        assertEquals(55_000, result[1].length());

        assertEquals(
                text,
                result[0].toString() + result[1].toString()
        );

        assertTrue(RopeBalancer.isBalanced(result[0].getRoot()));
        assertTrue(RopeBalancer.isBalanced(result[1].getRoot()));
    }

    @Test
    void splitRejectsInvalidIndex() {

        Rope rope = new Rope("HELLO");

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> rope.split(-1)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> rope.split(6)
        );
    }

    @Test
    void concatenateTwoRopes() {

        Rope first = new Rope("Hello ");
        Rope second = new Rope("World");

        first.concatenate(second);

        assertEquals("Hello World", first.toString());
    }

    @Test
    void concatenateEmptyRopes() {

        Rope first = new Rope();
        Rope second = new Rope();

        first.concatenate(second);

        assertTrue(first.isEmpty());
    }

    @Test
    void concatenateWithEmptyRope() {

        Rope first = new Rope("Hello");
        Rope second = new Rope();

        first.concatenate(second);

        assertEquals("Hello", first.toString());
    }

    @Test
    void concatenateIntoEmptyRope() {

        Rope first = new Rope();
        Rope second = new Rope("World");

        first.concatenate(second);

        assertEquals("World", first.toString());
    }

    @Test
    void concatenateLargeRopesMaintainsBalance() {

        Rope first = new Rope("A".repeat(100_000));
        Rope second = new Rope("B".repeat(100_000));

        first.concatenate(second);

        assertEquals(200_000, first.length());

        assertEquals(
                "A".repeat(100_000) + "B".repeat(100_000),
                first.toString()
        );

        assertTrue(RopeBalancer.isBalanced(first.getRoot()));
    }

    @Test
    void getRootReturnsNullForEmptyRope() {

        Rope rope = new Rope();

        assertNull(rope.getRoot());
    }

    @Test
    void getRootReturnsTreeForNonEmptyRope() {

        Rope rope = new Rope("Hello");

        assertNotNull(rope.getRoot());
        assertEquals(5, rope.getRoot().length());
    }

    @Test
    void randomInsertionsAndDeletionsMatchStringBuilder() {

        Rope rope = new Rope();
        StringBuilder expected = new StringBuilder();

        Random random = new Random(42);

        for (int i = 0; i < 500; i++) {

            if (expected.isEmpty() || random.nextBoolean()) {

                // Random insertion.
                int index = random.nextInt(expected.length() + 1);

                String text = "X".repeat(random.nextInt(10) + 1);

                rope.insert(index, text);
                expected.insert(index, text);

            } else {

                // Random deletion.
                int start = random.nextInt(expected.length());

                int end = start
                        + random.nextInt(expected.length() - start + 1);

                rope.delete(start, end);
                expected.delete(start, end);
            }

            assertEquals(expected.toString(), rope.toString());
            assertEquals(expected.length(), rope.length());

            assertTrue(
                    RopeBalancer.isBalanced(rope.getRoot()),
                    "Tree became unbalanced at iteration " + i
            );
        }
    }

    private void assertLeavesRespectMaximumSize(RopeNode node) {

        if (node == null) {
            return;
        }

        if (node.isLeaf()) {

            RopeLeaf leaf = (RopeLeaf) node;

            assertTrue(
                    leaf.length() <= RopeLeaf.MAX_LEAF_SIZE
            );

            return;
        }

        RopeBranch branch = (RopeBranch) node;

        assertLeavesRespectMaximumSize(branch.getLeft());
        assertLeavesRespectMaximumSize(branch.getRight());
    }
}
