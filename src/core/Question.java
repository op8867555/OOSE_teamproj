package core;

public abstract class Question {
    protected AnswerValidator av;
    protected AnswerType at;
    protected Object ans;
    public abstract void addOptions();
    public boolean validate() {
        return this.av.validate(ans);
    }
}
