package core;

public class AnsweringScreen extends Screen {
    protected Question q;
    protected Answer a;

    public AnsweringScreen(Question q) {
        this.q = q;
    }

    public Question getQuestion() {
        return q;
    }

    public Answer getAnswer() {
        return a;
    }

}
