package example;

import java.util.Arrays;

import core.*;

public class BinaryMultiplicationAnswerValidator implements AnswerValidator {

    int rightAns;

    public BinaryMultiplicationAnswerValidator(int rightAns) {
        this.rightAns = rightAns;
    }

    @Override
    public boolean validate(Object ans) {
        return (Integer) rightAns == ans;
    }

    public static BinaryMultiplicationAnswerValidator of(int rightAns) {
        return new BinaryMultiplicationAnswerValidator(rightAns);
    }

    public static void main (String[] args) {
        AnswerType at = new AnswerType () {
            public void addOption(Object o) {
            }

            @Override
            public Object answer() {
                return null;
            }

        };
        BinaryMultiplicationQuestion x =
            new BinaryMultiplicationQuestion(at,
                    1, 3,
                    Arrays.asList(3,2,6,1));
        x.getOptions().forEach(System.out::println);
        x.setAnswer(3);
        System.out.println(x.validate());
    }

}
