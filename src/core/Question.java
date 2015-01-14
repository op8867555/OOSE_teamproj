package core;

import java.util.List;

public abstract class Question {

    public Question(AnswerValidator av, List<?> options) {
        this.av = av;
        this.options = options;
    }

    protected AnswerValidator av;
    protected List<?> options;


    public List<?> getOptions() {
        return this.options;
    }

    public Boolean validate(Object ans) {
        return this.av.validate(ans);
    }

}
