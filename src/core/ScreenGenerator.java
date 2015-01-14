package core;

import java.util.List;

public abstract class ScreenGenerator {
    protected List<Answer> answers;
    protected Question qs;

    protected AnsweringScreen createAnsweringScreen(Question q) {
        return new AnsweringScreen(q);
    }

    protected ResultScreen createResultScreen(Object result) {
        return new ResultScreen(result);
    }

    public abstract Screen getNextScreen(Object q);

    public List<Answer> getAnswers() {
        return this.answers;
    }
}
