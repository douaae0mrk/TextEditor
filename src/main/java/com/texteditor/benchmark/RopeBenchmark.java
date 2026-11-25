
package com.texteditor.benchmark;

import com.texteditor.rope.Rope;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1)
@Measurement(iterations = 2)
@Fork(1)
public class RopeBenchmark {

    private static final int DELETE_LENGTH = 1000;
    private static final int SUBSTRING_LENGTH = 1000;
    private static final String SINGLE_CHAR = "x";

    private static String generateText(int size) {
        return "a".repeat(size);
    }

    @State(Scope.Thread)
    public static class RopeReadState {

        @Param({"5242880"})
        public int documentSize;

        Rope rope;
        int index;

        @Setup(Level.Trial)
        public void setup() {
            String text = generateText(documentSize);

            rope = new Rope(text);
            index = documentSize / 2;
        }
    }

    @State(Scope.Thread)
    public static class StringBuilderReadState {

        @Param({"5242880"})
        public int documentSize;

        StringBuilder builder;
        int index;

        @Setup(Level.Trial)
        public void setup() {
            String text = generateText(documentSize);

            builder = new StringBuilder(text);
            index = documentSize / 2;
        }
    }

    // Character access.

    @Benchmark
    public char benchmarkRopeCharAt(RopeReadState state) {
        return state.rope.charAt(state.index);
    }

    @Benchmark
    public char benchmarkStringBuilderCharAt(StringBuilderReadState state) {
        return state.builder.charAt(state.index);
    }


    @Benchmark
    public int benchmarkRopeLength(RopeReadState state) {
        return state.rope.length();
    }

    @Benchmark
    public int benchmarkStringBuilderLength(StringBuilderReadState state) {
        return state.builder.length();
    }


    @Benchmark
    public int benchmarkRopeHeight(RopeReadState state) {
        return state.rope.getRoot().height();
    }


    @Benchmark
    public String benchmarkRopeSubstring(RopeReadState state) {
        return state.rope.substring(
                state.index,
                state.index + SUBSTRING_LENGTH
        );
    }

    @Benchmark
    public String benchmarkStringBuilderSubstring(StringBuilderReadState state) {
        return state.builder.substring(
                state.index,
                state.index + SUBSTRING_LENGTH
        );
    }

    @Benchmark
    public String benchmarkRopeToString(RopeReadState state) {
        return state.rope.toString();
    }

    @Benchmark
    public String benchmarkStringBuilderToString(StringBuilderReadState state) {
        return state.builder.toString();
    }


    @State(Scope.Thread)
    public static class RopeEditState {

        @Param({"5242880"})
        public int documentSize;

        String text;
        String insertedText;
        Rope rope;
        Rope otherRope;
        int index;

        @Setup(Level.Trial)
        public void prepareText() {
            text = generateText(documentSize);

            // The inserted text and concatenation operand are both 5 MB.
            insertedText = generateText(documentSize);

            index = documentSize / 2;

            otherRope = new Rope(insertedText);
        }

        @Setup(Level.Invocation)
        public void resetData() {
            rope = new Rope(text);
        }
    }

    @State(Scope.Thread)
    public static class StringBuilderEditState {

        @Param({"5242880"})
        public int documentSize;

        String text;
        String insertedText;
        StringBuilder builder;
        String otherText;
        int index;

        @Setup(Level.Trial)
        public void prepareText() {
            text = generateText(documentSize);

            // Same 5 MB inserted text as the Rope benchmark.
            insertedText = generateText(documentSize);

            index = documentSize / 2;

            // Same 5 MB concatenation operand as the Rope benchmark.
            otherText = insertedText;
        }

        @Setup(Level.Invocation)
        public void resetData() {
            builder = new StringBuilder(text);
        }
    }

    // Insertion: insert 5 MB into the middle of a 5 MB document.

    @Benchmark
    public void benchmarkRopeInsert(RopeEditState state) {
        state.rope.insert(state.index, state.insertedText);
    }

    @Benchmark
    public void benchmarkStringBuilderInsert(StringBuilderEditState state) {
        state.builder.insert(state.index, state.insertedText);
    }

    // Single-character insertion: insert one character into the middle of a 5 MB document.

    @Benchmark
    public int benchmarkRopeSingleCharInsert(RopeEditState state) {
        state.rope.insert(state.index, SINGLE_CHAR);
        return state.rope.length();
    }

    @Benchmark
    public int benchmarkStringBuilderSingleCharInsert(StringBuilderEditState state) {
        state.builder.insert(state.index, SINGLE_CHAR);
        return state.builder.length();
    }

    // Deletion: delete 1000 characters from the middle.

    @Benchmark
    public void benchmarkRopeDelete(RopeEditState state) {
        state.rope.delete(
                state.index,
                state.index + DELETE_LENGTH
        );
    }

    @Benchmark
    public void benchmarkStringBuilderDelete(StringBuilderEditState state) {
        state.builder.delete(
                state.index,
                state.index + DELETE_LENGTH
        );
    }

    // Concatenation: concatenate two 5 MB structures/strings.

    @Benchmark
    public void benchmarkRopeConcatenate(RopeEditState state) {
        state.rope.concatenate(state.otherRope);
    }

    @Benchmark
    public void benchmarkStringBuilderConcatenate(StringBuilderEditState state) {
        state.builder.append(state.otherText);
    }


    @State(Scope.Thread)
    public static class InputState {

        @Param({"5242880"})
        public int documentSize;

        String text;

        @Setup(Level.Trial)
        public void prepareText() {
            text = generateText(documentSize);
        }
    }

    @Benchmark
    public Rope benchmarkRopeConstruction(InputState state) {
        return new Rope(state.text);
    }

    @Benchmark
    public StringBuilder benchmarkStringBuilderConstruction(InputState state) {
        return new StringBuilder(state.text);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(RopeBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }
}
