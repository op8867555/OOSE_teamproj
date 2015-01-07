package demo.swing;

import java.util.List;
import java.util.function.Function;

import core.Question;
import crossAppliedTable.CrossApplication;
import crossAppliedTable.CrossAppliedTable;
import demo.BinaryMultiplicationQuestionGenerator;

public class BinaryMultiplicationQuestionSwingGenerator
    extends BinaryMultiplicationQuestionGenerator {

    public BinaryMultiplicationQuestionSwingGenerator(
            CrossAppliedTable<Integer> t,
            Function<CrossApplication<Integer>, List<Integer>> optionGenerator) {
        super(t, optionGenerator);
            }

    @Override
    protected Question generateQuestion(CrossApplication<Integer> app) {
        return new BinaryMultiplicationQuestionSwing(app.getA(),
                app.getB(), app.getX(), generateOptions(app));
    }


}
