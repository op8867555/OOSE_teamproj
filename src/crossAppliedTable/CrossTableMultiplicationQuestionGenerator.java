package crossAppliedTable;

import java.util.List;

import core.AnswerType;
import core.Question;
import core.QuestionGenerator;

abstract public class CrossTableMultiplicationQuestionGenerator <T>
    extends QuestionGenerator {

    protected CrossAppliedTable<T> t;
    protected AnswerType at;

    protected int currentQuestionIndex = 0;


    public CrossTableMultiplicationQuestionGenerator(CrossAppliedTable<T> t,
            AnswerType at) {
        this.t = t;
        this.at = at;
    }

    abstract protected Question generateQuestion(CrossApplication<T> app);
    abstract protected List<T>  generateOptions (CrossApplication<T> app);

    @Override
    public Question getNextQuestion() {
        Question q = generateQuestion(t.get(currentQuestionIndex));
        currentQuestionIndex += 1;
        return q;
    }

}
