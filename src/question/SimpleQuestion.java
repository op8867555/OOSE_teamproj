package question;

import java.util.List;

import core.AnswerValidator;
import core.Question;

public class SimpleQuestion extends Question {

    protected String description;

    public SimpleQuestion(AnswerValidator av, List<String> options2) {
        super(av, options2);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
