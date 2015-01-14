package demo;

import java.util.List;
import java.util.function.Function;

import core.Question;
import crossAppliedTable.CrossApplication;
import crossAppliedTable.CrossAppliedTable;
import crossAppliedTable.CrossAppliedTableQuestionGenerator;

public class BinaryMultiplicationQuestionGenerator extends
        CrossAppliedTableQuestionGenerator<Integer> {

    protected Function<CrossApplication<Integer>, List<Integer>> optionGenerator;

    public BinaryMultiplicationQuestionGenerator(CrossAppliedTable<Integer> t) {
        super(t);
    }

    public BinaryMultiplicationQuestionGenerator(
            CrossAppliedTable<Integer> t,
            Function<CrossApplication<Integer>, List<Integer>> optionGenerator) {
        super(t);
        this.optionGenerator = optionGenerator;
    }

    @Override
    protected Question generateQuestion(CrossApplication<Integer> app) {
        return new BinaryMultiplicationQuestion(app.getA(),
                app.getB(), app.getX(), generateOptions(app));
    }

    @Override
    protected List<Integer> generateOptions(CrossApplication<Integer> app) {
        return optionGenerator.apply(app);
    }

}
