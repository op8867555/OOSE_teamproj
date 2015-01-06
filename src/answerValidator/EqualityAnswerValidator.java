package answerValidator;

import core.AnswerValidator;

public class EqualityAnswerValidator<T> implements AnswerValidator {

    T rightAns;

    public EqualityAnswerValidator(T rightAns) {
        this.rightAns = rightAns;
    }

    @Override
    public boolean validate(Object ans) {
        return rightAns.equals(ans);
    }

}
