package demo.swing;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;

import core.AnswerValidator;
import question.SimpleQuestion;

public class SimpleQuestionSwing extends SimpleQuestion implements
        SwingRenderable {

    public SimpleQuestionSwing(AnswerValidator av, List<String> options2) {
        super(av, options2);
    }

    @Override
    public JComponent render() {
        return new JLabel(description);
    }

}
