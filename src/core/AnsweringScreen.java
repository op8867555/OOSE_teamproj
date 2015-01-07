package core;

public class AnsweringScreen extends Screen {
    protected Question q;

    public AnsweringScreen(Question q) {
        this.q = q;
    }

    public Question getQuestion() {
        return q;
    }

}
