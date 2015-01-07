package demo.swing;

import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import core.*;
import questionGenerator.*;
import crossAppliedTable.*;
import screenGenerator.*;
import question.SimpleQuestion;
import answerValidator.*;
import demo.*;

public class SimpleApp {

    JFrame frame = new JFrame();
    JButton startBtn = new JButton("開始作答");
    JButton exitBtn = new JButton("離開");

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

    static QuestionGenerator createBinaryMultiplicationQuestionGenerator() {
        List<Integer> xs = IntStream.range(1, 10).boxed()
                .collect(Collectors.toList());
        BinaryMultiplicationCrossTable tab = new BinaryMultiplicationCrossTable(
                xs, xs);

        Function<CrossApplication<Integer>, List<Integer>> optionsGen = app -> {
            List<Integer> opts = Arrays.asList((app.getA() + 1) * app.getB(),
                    (app.getA() - 1) * app.getB(), app.getA()
                            * (app.getB() - 1), 1, app.getA()
                            * (app.getB() + 1));
            Collections.shuffle(opts);
            opts = opts.stream().limit(3).collect(Collectors.toList());
            opts.add(app.getX());
            Collections.shuffle(opts);
            return opts;
        };

        return new BinaryMultiplicationQuestionSwingGenerator(tab, optionsGen);
    }

    static QuestionGenerator createQuestionGenerator(String configFile) {
        QuestionGenerator qg = null;
        try {
            String raw_config = Files.lines(
                    FileSystems.getDefault().getPath(configFile)).collect(
                    Collectors.joining());
            JSONObject config = new JSONObject(raw_config);
            String questionGenerator = config.getString("question");
            if (questionGenerator.equals("9x9")) {
                qg = createBinaryMultiplicationQuestionGenerator();
            } else if (questionGenerator.equals("simple")) {
                qg = new JsonConfiguredQuestionGenerator(config) {
                    @Override
                    public Question parseQuestion(JSONObject o) {
                        String qs = o.getString("question");
                        String a = o.getString("right_answer");
                        List<String> options =
                            new ArrayList<String>();
                        JSONArray raw_options = o.getJSONArray("options");
                        for (int i = 0; i < raw_options.length(); i++) {
                            options.add(raw_options.get(i).toString());
                        }
                        options.add(o.getString("right_answer"));
                        Collections.shuffle(options);
                        SimpleQuestion q = new SimpleQuestionSwing(
                                new EqualityAnswerValidator<String>(a),
                                options
                                );
                        q.setDescription(qs);
                        return q;
                    }
                };
            } else {
                throw new Exception("error");
            }
            if (config.has("random") && config.getBoolean("random"))
                qg = new RandonizedQuestionGenerator(qg);

            if (config.has("limit")) {
                int limit = config.getInt("limit");
                qg = new LimitedQuestionGenerator(limit, qg);
            }
            return qg;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "configFile Error");
            System.exit(0);
        } catch (Exception e) {
        }
        return qg;
    }

    static QuestionGenerator getDefaultQuestionGenerator() {
        QuestionGenerator qg =
            new LimitedQuestionGenerator(5,
                    new RandonizedQuestionGenerator(
                        createBinaryMultiplicationQuestionGenerator()));
        return qg;
    }

    public static void main(String[] args) {

        SimpleApp app = new SimpleApp();
        QuestionGenerator qg = createQuestionGenerator("config");
        //getDefaultQuestionGenerator();
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
                JOptionPane.showMessageDialog(null, ((Question) s.getResult()).validate() ? "正確" : "錯誤");
                return s;
            }
        };

        app.startBtn.addActionListener(e -> {
            app.render(sg.getNextScreen(null));
        });
    }

}

