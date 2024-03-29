package demo;

import java.util.List;

import core.Question;
import answerValidator.*;

public class BinaryMultiplicationQuestion extends Question {

    private Integer a, b;

    public BinaryMultiplicationQuestion(Integer a, Integer b, Integer x, List<Integer> options) {
        super(new EqualityAnswerValidator<Integer>(x), options);
        this.a = a;
        this.b = b;
    }

    public Integer getA() {
        return a;
    }

    public Integer getB() {
        return b;
    }


}
