package questionGenerator;

import core.Question;
import core.QuestionGenerator;

public class LimitedQuestionGenerator extends QuestionGeneratorCombinator {

	protected int max = 10;
    protected int count = 0;

    public LimitedQuestionGenerator(QuestionGenerator qg) {
        super(qg);
    }

    public LimitedQuestionGenerator(int max, QuestionGenerator qg) {
        super(qg);
        this.max = max;
        this.count = 0;
    }

    @Override
    public Question getNextQuestion() {
        if (count >= max)
            return null;
        count += 1;
        return qg.getNextQuestion();
    }

}
