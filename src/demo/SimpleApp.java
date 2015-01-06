package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import questionGenerator.LimitedQuestionGenerator;
import questionGenerator.RandonizedQuestionGenerator;
import screenGenerator.AlwaysShowResultScreenGenerator;
import core.*;
import crossAppliedTable.CrossApplication;

public class SimpleApp {
    public static void main(String[] args) {
        List<Integer> xs =
            IntStream.range(1, 10).boxed()
                     .collect(Collectors.toList());
        BinaryMultiplicationCrossTable tab =
            new BinaryMultiplicationCrossTable(xs, xs);

        Function<CrossApplication<Integer>, List<Integer>> optionsGen =
            app -> {
                List<Integer> opts =
                    Arrays.asList((app.getA() + 1) * app.getB(),
                            (app.getA() - 1) * app.getB(),
                            app.getA() * (app.getB() - 1),
                            1,
                            app.getA() * (app.getB() + 1));
                Collections.shuffle(opts);
                opts = opts.stream().limit(3).collect(Collectors.toList());
                opts.add(app.getX());
                Collections.shuffle(opts);
                return opts;
            };


        QuestionGenerator qg =
            new LimitedQuestionGenerator(5,
                    new RandonizedQuestionGenerator(
                        new BinaryMultiplicationQuestionGenerator(tab,
                            optionsGen)));

        ScreenGenerator sg = new AlwaysShowResultScreenGenerator(qg);

        MainView view = new MainView(sg);

        view.render(null);
    }
}

class MainView {
    protected JFrame frame = new JFrame();
    protected ScreenGenerator sg;

    public MainView(ScreenGenerator sg) {
        this.sg = sg;
    }

    public void render(Question answered) {
        Screen s = sg.getNextScreen(answered);
        if (s instanceof AnsweringScreen) {
            AnsweringScreenView asv =
                new AnsweringScreenView(scr -> {
                    render(scr.getQuestion());
                });
            frame.getContentPane().removeAll();
            JPanel p = asv.render((AnsweringScreen) s);
            frame.add(p);
            frame.pack();
            frame.setVisible(true);
        } else if (s instanceof ResultScreen) {
            Question q = (Question) ((ResultScreen) s).getResult();
            JOptionPane.showMessageDialog(null, q.validate() ? "正確" : "錯誤");
            render(null);
        } else {
            JOptionPane.showMessageDialog(null, "作答結束");
            frame.setVisible(false);
            frame.dispose();
            System.exit(0);
        }
    }
}

class AnsweringScreenView {
    protected JPanel panel;
    protected JPanel options;
    protected Consumer<AnsweringScreen> callback;

    public AnsweringScreenView(Consumer<AnsweringScreen> callback) {
        this.panel = new JPanel();
        this.options = new JPanel();
        this.callback = callback;
    }

    public JPanel render(AnsweringScreen scr) {
        JButton submitBtn = new JButton("submit");
        Question q = scr.getQuestion();
        options.removeAll();

        List<JRadioButton> btns = new ArrayList<JRadioButton>();
        ButtonGroup bg = new ButtonGroup();

        q.getOptions().forEach(obj -> {
            Integer o = (Integer) obj;
            JRadioButton btn = new JRadioButton(o.toString());
            btns.add(btn);
            options.add(btn);
            bg.add(btn);
        });

        submitBtn.addActionListener(e -> {
            for (JRadioButton btn : btns)
                if (btn.isSelected())
                    q.setAnswer(new Integer(btn.getText()));
            callback.accept(scr);
        });

        this.panel.removeAll();
        this.panel.add(displayQuestion(q));
        this.panel.add(options);
        this.panel.add(submitBtn);
        return this.panel;
    }

    static JComponent displayQuestion(Question rq) {
        BinaryMultiplicationQuestion q = (BinaryMultiplicationQuestion) rq;
        return new JLabel(q.getA() + " * " + q.getB() + " = " + " ?\n", JLabel.CENTER);
    }
}

