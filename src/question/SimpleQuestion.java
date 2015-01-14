package question;

import java.util.List;

import core.AnswerValidator;
import core.Question;

public class SimpleQuestion extends Question {

    protected String description;

    public SimpleQuestion(AnswerValidator av, List<String> options) {
        super(av, options);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
