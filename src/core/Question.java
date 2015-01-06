package core;

import java.util.List;

public abstract class Question {
    protected AnswerValidator av;
    protected Object ans;
    protected List<?> options;

    public Question(AnswerValidator av, List<Integer> options2) {
        this.av = av;
        this.options = options2;
    }

    public boolean validate() {
        return this.av.validate(this.ans);
    }

    public void setAnswer(Object ans) {
        this.ans = ans;
    }

    public List<?> getOptions() {
        return this.options;
    }
}
