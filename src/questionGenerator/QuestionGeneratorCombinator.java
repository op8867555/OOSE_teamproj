package questionGenerator;

import core.Question;
import core.QuestionGenerator;

abstract public class QuestionGeneratorCombinator extends QuestionGenerator {

    protected QuestionGenerator qg;

    public QuestionGeneratorCombinator(QuestionGenerator qg) {
        this.qg = qg;
    }

    @Override
    abstract public Question getNextQuestion();

}
