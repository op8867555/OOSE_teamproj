package demo.swing;

import java.awt.Font;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;

import demo.BinaryMultiplicationQuestion;

public class BinaryMultiplicationQuestionSwing extends
        BinaryMultiplicationQuestion implements SwingRenderable {

    public BinaryMultiplicationQuestionSwing(Integer a, Integer b, Integer x,
            List<Integer> options) {
        super(a, b, x, options);
    }

    @Override
    public JComponent render() {
        JLabel l = new JLabel(getA() + " * " + getB() + " = " + " ?\n", JLabel.CENTER);
        //調整字體
        Font font = l.getFont();
        l.setFont(new Font(font.getName(), Font.BOLD, 32));
        return l;
    }

}
