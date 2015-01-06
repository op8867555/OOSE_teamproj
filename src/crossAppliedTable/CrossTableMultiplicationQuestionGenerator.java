package crossAppliedTable;

import java.util.List;

import core.Question;
import core.QuestionGenerator;

abstract public class CrossTableMultiplicationQuestionGenerator <T>
    extends QuestionGenerator {

    protected CrossAppliedTable<T> t;

    protected int currentQuestionIndex = 0;


    public CrossTableMultiplicationQuestionGenerator(CrossAppliedTable<T> t) {
        this.t = t;
    }

    abstract protected Question generateQuestion(CrossApplication<T> app);
    abstract protected List<T>  generateOptions (CrossApplication<T> app);

    @Override
    public Question getNextQuestion() {
        if (currentQuestionIndex >= t.size())
            return null;
        Question q = generateQuestion(t.get(currentQuestionIndex));
        currentQuestionIndex += 1;
        return q;
    }

}
