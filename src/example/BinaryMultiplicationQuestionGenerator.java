package example;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import core.AnswerType;
import core.Question;
import core.QuestionGenerator;

public class BinaryMultiplicationQuestionGenerator extends QuestionGenerator
        implements Iterable<Question> {

    Queue<Integer> xs;
    Queue<Integer> ys;

    AnswerType at;

    public BinaryMultiplicationQuestionGenerator(AnswerType at) {
        List<Integer> ns = IntStream.range(1, 10).boxed()
                .collect(Collectors.toList());
        List<Integer> ms = ns.stream().collect(Collectors.toList());
        Collections.copy(ms, ns);
        Collections.shuffle(ns);
        Collections.shuffle(ms);
        xs = new LinkedList<Integer>(ms);
        ys = new LinkedList<Integer>(ns);
        this.at = at;
    }

    @Override
    public Question getNextQuestion() {
        if (xs.isEmpty())
            return null;
        int x = xs.poll();
        int y = ys.poll();
        return new BinaryMultiplicationQuestion(at, x, y, generateOptions(x, y));
    }

    private List<Integer> generateOptions(int x, int y) {
        ArrayList<Integer> xs = new ArrayList<Integer>(Arrays.asList(x * y,
                (x + 1) * y, (y + 1) * x, x + y));
        Collections.shuffle(xs);
        return xs;
    }

    public static void main(String[] args) {
        BinaryMultiplicationQuestionGenerator qg = new BinaryMultiplicationQuestionGenerator(
                new AnswerType() {
                    @Override
                    public void addOption(Object o) {
                    }

                    @Override
                    public Object answer() {
                        return null;
                    }});
        qg.stream().forEach(System.out::println);
    }

    @Override
    public Iterator<Question> iterator() {
        return new Iterator<Question> () {
            @Override
            public boolean hasNext() {
                return !xs.isEmpty();
            }
            @Override
            public Question next() {
                return getNextQuestion();
            }
        };
    }

    public Stream<Question> stream() {
        return Stream.iterate(getNextQuestion(), un -> getNextQuestion());
    }
}
