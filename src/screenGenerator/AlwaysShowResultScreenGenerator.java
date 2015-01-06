package screenGenerator;

import core.AnsweringScreen;
import core.EndScreen;
import core.Question;
import core.QuestionGenerator;
import core.ResultScreen;
import core.Screen;
import core.ScreenGenerator;

public class AlwaysShowResultScreenGenerator extends ScreenGenerator {

    State st;
    QuestionGenerator qg;
    Question lastAnswered;

    public AlwaysShowResultScreenGenerator(QuestionGenerator qg) {
        this.qg = qg;
        this.st = State.START;
    }

    @Override
    public Screen getNextScreen(Question answered) {
        if (st.equals(State.START) || st.equals(State.ANSWERING)) {
            lastAnswered = answered;
            Question q = qg.getNextQuestion();
            return new AnsweringScreen(q);
        } else if (st.equals(State.RESULT)) {
            return new ResultScreen(answered);
        } else {
            return EndScreen.getInstance();
        }
    }

}

enum State {
    START, ANSWERING, RESULT
}
