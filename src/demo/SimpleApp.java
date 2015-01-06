package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import screenGenerator.AlwaysShowResultScreenGenerator;
import core.*;
import crossAppliedTable.CrossApplication;

public class SimpleApp {
    public static void main(String[] args) {
        JPanel options = new JPanel();
        SingleChoice sc = new SingleChoice(options);
        List<Integer> xs =
            IntStream.range(1, 10).boxed()
                     .collect(Collectors.toList());
        BinaryMultiplicationCrossTable tab =
            new BinaryMultiplicationCrossTable(xs, xs);

        Function<CrossApplication<Integer>, List<Integer>> optionsGen =
            app -> Arrays.asList(app.getX(), app.getA() + 1, app.getB() - 1);

        BinaryMultiplicationQuestionGenerator qg =
            new BinaryMultiplicationQuestionGenerator(tab,
                    sc,
                    optionsGen);

        AlwaysShowResultScreenGenerator sg = new AlwaysShowResultScreenGenerator(qg);

        AnsweringScreenView asv = new AnsweringScreenView(options);
        JFrame f = new JFrame();
        f.add(asv.render((AnsweringScreen) sg.getNextScreen(null)));
        f.pack();
        f.setVisible(true);
    }
}

class MainView {
    protected JPanel panel;
    protected JFrame frame;
    protected JPanel options;
    protected ScreenGenerator sg;

    public MainView(ScreenGenerator sg, JPanel options) {
        this.options = options;
        this.sg = sg;
    }

    public void render() {
        Screen s = sg.getNextScreen(null);
        if (s instanceof AnsweringScreen) {
            JPanel options = new JPanel();
            AnsweringScreenView asv = new AnsweringScreenView();
            f.add(.render((AnsweringScreen) s));
        } else if (s instanceof ResultScreen) {
            ResultScreen scr = (ResultScreen) s;
            JPanel p = new JPanel();
            return p;
        }
        return new JPanel();
    }
}

class AnsweringScreenView {
    protected JPanel panel;
    protected JPanel options;

    public AnsweringScreenView(JPanel options) {
        this.panel = new JPanel();
        this.options = options;
    }

    public JPanel render(AnsweringScreen scr) {
        JButton submitBtn = new JButton("submit");
        Question q = scr.getQuestion();
        submitBtn.addActionListener(e -> {
            q.answer();
            JOptionPane.showMessageDialog(null, q.validate());
        });

        this.panel.removeAll();
        this.panel.add(Displayer.displayQuestion(q));
        this.panel.add(options);
        this.panel.add(submitBtn);
        return this.panel;
    }

}

class SingleChoice extends AnswerType {

    List<JRadioButton> btns = new ArrayList<JRadioButton>();
    JPanel p;
    ButtonGroup bg = new ButtonGroup();

    public SingleChoice(JPanel p) {
        this.p = p;
        p.removeAll();
    }

    @Override
    public void addOption(Object o) {
        JRadioButton btn = new JRadioButton(o.toString());
        btns.add(btn);
        p.add(btn);
        bg.add(btn);
    }

    @Override
    public Object answer() {
        for (JRadioButton btn : btns)
            if (btn.isSelected())
                return new Integer(btn.getText());
        return null;
    }

    public JPanel getPanel() {
        return this.p;
    }
}


class Displayer {
    static JComponent displayScreen(Screen s) {
        if (s instanceof AnsweringScreen) {
            AnsweringScreen scr = (AnsweringScreen) s;
            JPanel p = new JPanel();
            p.add(Displayer.displayQuestion(scr.getQuestion()));
            return p;
        } else if (s instanceof ResultScreen) {
            ResultScreen scr = (ResultScreen) s;
            JPanel p = new JPanel();
            return p;
        }
        return new JPanel();
    }

    static JComponent displayQuestion(Question rq) {
        BinaryMultiplicationQuestion q = (BinaryMultiplicationQuestion) rq;
        return new JLabel(q.getA() + " * " + q.getB() + " = " + " ?\n", JLabel.CENTER);
    }

    static JComponent displayOptions(List<Integer> xs) {
        ButtonGroup group = new ButtonGroup();
        Stream<JRadioButton> btns =
            xs.stream().map(x -> new JRadioButton(x+""));
        JPanel p = new JPanel();
        btns.forEach(group::add);
        btns.forEach(p::add);
        return p;
    }
}
