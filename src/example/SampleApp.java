package example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

import core.AnswerType;
import core.AnsweringScreen;
import core.EndScreen;
import core.Question;
import core.QuestionGenerator;
import core.Screen;
import core.ScreenGenerator;

public class SampleApp {
    public static void main(String[] args) {
        Answers at = new Answers();
        Seq sg =
            new Seq(new BinaryMultiplicationQuestionGenerator(at));

        BinaryMultiplicationQuestion q;
        for (Screen s = sg.getNextScreen(null);
                s != EndScreen.getInstance();
                s = sg.getNextScreen(q)) {
            AnsweringScreen as = (AnsweringScreen) s;
            q = (BinaryMultiplicationQuestion) as.getQuestion();
            System.out.printf("%d * %d = ?\n", q.getX(), q.getY());
            q.answer();
            System.out.println(q.validate());
        }
    }
}


class Seq extends ScreenGenerator {

    QuestionGenerator qg;

    public Seq(QuestionGenerator qg) {
        this.qg = qg;
    }

    @Override
    protected Screen getNextScreen(Question answered) {
        Question q = qg.getNextQuestion();
        if (q != null)
            return new AnsweringScreen(q);
        else
            return EndScreen.getInstance();
    }
}
