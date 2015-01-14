package screenGenerator;

import core.Answer;
import core.EndScreen;
import core.Question;
import core.QuestionGenerator;
import core.Screen;
import core.ScreenGenerator;

public class AlwaysShowResultScreenGenerator extends ScreenGenerator {

    State st;
    QuestionGenerator qg;
    Object lastAnswered;

    public AlwaysShowResultScreenGenerator(QuestionGenerator qg) {
        this.qg = qg;
        this.st = State.START;
    }

    @Override
    public Screen getNextScreen(Object answered) {
        if (st.equals(State.START) || st.equals(State.ANSWERING)) {
            lastAnswered = answered;
            Question q = qg.getNextQuestion();
            if (q == null)
                return EndScreen.getInstance();
            st = State.RESULT;
            return createAnsweringScreen(q);
        } else if (st.equals(State.RESULT)) {
            st = State.ANSWERING;
            return createResultScreen(answered);
        } else {
            return null;
        }
    }

}

enum State {
    START, ANSWERING, RESULT
}
