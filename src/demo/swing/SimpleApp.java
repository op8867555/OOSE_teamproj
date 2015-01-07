package demo.swing;

import java.awt.FlowLayout;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import core.*;
import questionGenerator.*;
import crossAppliedTable.*;
import screenGenerator.*;
import demo.*;

public class SimpleApp {

    JFrame frame = new JFrame();
    JButton startBtn = new JButton("開始作答");
    JButton exitBtn  = new JButton("離開");


    public SimpleApp() {

        frame.setLayout(new FlowLayout());
        exitBtn.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            System.exit(0);
        });
        frame.add(startBtn);
        frame.add(exitBtn);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }

    public void render(Screen screen) {

        if (screen == EndScreen.getInstance()) {
            JOptionPane.showMessageDialog(null, "bye");
            frame.setVisible(false);
            frame.dispose();
            System.exit(0);
        }

        SwingRenderable r = (SwingRenderable) screen;
        frame.getContentPane().removeAll();
        frame.add(r.render());
        frame.pack();
        frame.setVisible(true);
    }

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
                        new BinaryMultiplicationQuestionSwingGenerator(tab,
                            optionsGen)));

        SimpleApp app = new SimpleApp();

        ScreenGenerator sg = new AlwaysShowResultScreenGenerator(qg) {
            @Override
            protected AnsweringScreen createAnsweringScreen(Question q) {
                AnsweringScreenSwing s = new AnsweringScreenSwing(q);
                s.setCallback(scr -> {
                    getNextScreen(scr.getQuestion());
                    app.render(getNextScreen(null));
                });
                return s;
            }
            @Override
            protected ResultScreen createResultScreen(Object o) {
                ResultScreenSwing s = new ResultScreenSwing(o);
                return s;
            }
        };

        app.startBtn.addActionListener(e -> {
            app.render(sg.getNextScreen(null));
        });
    }

}

