package core;

public abstract class ScreenGenerator {
    protected Question qs;
    protected AnsweringScreen createAnsweringScreen(Question q) {
        return new AnsweringScreen(q);
    }

    protected ResultScreen createResultScreen(Object result) {
        return new ResultScreen(result);
    }

    public abstract Screen getNextScreen(Question q);
}
