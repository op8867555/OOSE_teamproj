package example;

import java.util.ArrayList;
import java.util.List;

import core.AnswerType;
import core.AnswerValidator;
import core.Question;

public class BinaryMultiplicationQuestion extends Question {

    private Integer x;
    private Integer y;
    private Integer z;
    private ArrayList<Integer> options;

    public BinaryMultiplicationQuestion(AnswerValidator av, AnswerType at) {
        super(av, at);
    }

    public BinaryMultiplicationQuestion(AnswerType at,
            int x, int y, List<Integer> options) {
        super((i -> ((Integer) i) == x * y), at);
        this.x = x;
        this.y = y;
        this.z = x * y;
        this.options = new ArrayList<Integer>(options);
        this.options.forEach(i -> at.addOption(i));
    }

    public BinaryMultiplicationQuestion(AnswerValidator av, AnswerType at,
            int x, int y, int z, List<Integer> options) {
        super(av, at);
        this.x = x;
        this.y = y;
        this.z = z;
        this.options = new ArrayList<Integer>(options);
        this.options.forEach(i -> at.addOption(i));
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getZ() {
        return z;
    }

    public List<Integer> getOptions() {
        return options;
    }

}
