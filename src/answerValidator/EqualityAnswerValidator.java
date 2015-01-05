package answerValidator;

import core.AnswerValidator;

public class EqualityAnswerValidator implements AnswerValidator {

    Object rightAns;

    public EqualityAnswerValidator(Object rightAns) {
        this.rightAns = rightAns;
    }

    @Override
    public boolean validate(Object ans) {
        return ans.equals(rightAns);
    }

}
