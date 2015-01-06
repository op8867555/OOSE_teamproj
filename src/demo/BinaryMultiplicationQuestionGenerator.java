package demo;

import java.util.List;
import java.util.function.Function;

import core.AnswerType;
import core.Question;
import crossAppliedTable.CrossApplication;
import crossAppliedTable.CrossAppliedTable;
import crossAppliedTable.CrossTableMultiplicationQuestionGenerator;

public class BinaryMultiplicationQuestionGenerator extends
        CrossTableMultiplicationQuestionGenerator<Integer> {

    protected Function<CrossApplication<Integer>, List<Integer>> optionGenerator;

    public BinaryMultiplicationQuestionGenerator(CrossAppliedTable<Integer> t,
            AnswerType at) {
        super(t, at);
    }

    public BinaryMultiplicationQuestionGenerator(
            CrossAppliedTable<Integer> t,
            AnswerType at,
            Function<CrossApplication<Integer>, List<Integer>> optionGenerator) {
        super(t, at);
        this.optionGenerator = optionGenerator;
    }

    @Override
    protected Question generateQuestion(CrossApplication<Integer> app) {
        return new BinaryMultiplicationQuestion(this.at, app.getA(),
                app.getB(), app.getX(), generateOptions(app));
    }

    @Override
    protected List<Integer> generateOptions(CrossApplication<Integer> app) {
        return optionGenerator.apply(app);
    }

}
