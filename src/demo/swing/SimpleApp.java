package demo.swing;

import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import org.json.JSONArray;
import org.json.JSONObject;

import core.*;
import questionGenerator.*;
import crossAppliedTable.*;
import screenGenerator.*;
import question.SimpleQuestion;
import answerValidator.*;
import demo.*;

public class SimpleApp {

    JFrame startingFrame = new JFrame();
    JFrame frame = new JFrame();
    JButton startBtn = new JButton("開始作答");
    JButton exitBtn = new JButton("離開");
    JButton learnBtn = new JButton("學習");

    public SimpleApp() {

        startingFrame.setLayout(new FlowLayout());
        JLabel welcomeLabel = new JLabel("歡迎使用這個有點鳥的幼教系統~");

        Font font = welcomeLabel.getFont();
        welcomeLabel.setFont(new Font(font.getName(), Font.PLAIN, 50));
        startBtn.setFont(new Font(font.getName(), Font.PLAIN, 30));
        learnBtn.setFont(new Font(font.getName(), Font.PLAIN, 30));
        exitBtn.setFont(new Font(font.getName(), Font.PLAIN, 30));

        exitBtn.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            System.exit(0);
        });

        learnBtn.addActionListener(e -> {

            File path =  new File("img", "九九乘法表.jpg");
            JOptionPane.showMessageDialog(null,
                    new ImageIcon(path.getAbsolutePath()),
                    "學習", JOptionPane.PLAIN_MESSAGE);


        });

        startingFrame.add(welcomeLabel);
        startingFrame.add(startBtn);
        startingFrame.add(learnBtn);
        startingFrame.add(exitBtn);
        startingFrame.setSize(800,600);
        startingFrame.setVisible(true);
    }

    public void render(Screen screen) {

        if (screen == EndScreen.getInstance()) {
            JOptionPane.showMessageDialog(null, "bye");
            frame.setVisible(false);
            startingFrame.setVisible(true);
            return;
        }

        SwingRenderable r = (SwingRenderable) screen;
        frame.getContentPane().removeAll();
        frame.add(r.render());
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) throws Exception {

        Config config = new Config("config");
        SimpleApp app = new SimpleApp();
        QuestionGenerator qg = config.createQuestionGenerator();
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
            app.startingFrame.setVisible(false);
            app.render(sg.getNextScreen(null));
        });
    }

}

class Config {

    JSONObject config;

    public Config(String configFile) throws IOException {
        String raw_config = Files.lines(
                FileSystems.getDefault().getPath(configFile)).collect(
                Collectors.joining());
        config = new JSONObject(raw_config);
    }

    public String getLearningImagePath() {
        if (config.has("learning_image"))
            return config.getString("learning_image");
        else
            return "";
    }

    public String getQuestionGeneratorString() {
        return config.getString("question");
    }


    public QuestionGenerator createBinaryMultiplicationQuestionGenerator() {
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

    public QuestionGenerator createQuestionGenerator() throws Exception {
        QuestionGenerator qg = null;
        String questionGenerator = getQuestionGeneratorString();
        if (questionGenerator.equals("9x9")) {
            qg = createBinaryMultiplicationQuestionGenerator();
        } else if (questionGenerator.equals("simple")) {
            qg = new JsonConfiguredSimpleQuestionGenerator(config);
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
    }

    public QuestionGenerator getDefaultQuestionGenerator() {
        QuestionGenerator qg =
            new LimitedQuestionGenerator(5,
                    new RandonizedQuestionGenerator(
                        createBinaryMultiplicationQuestionGenerator()));
        return qg;
    }
}

class JsonConfiguredSimpleQuestionGenerator
    extends JsonConfiguredQuestionGenerator {
    public JsonConfiguredSimpleQuestionGenerator(JSONObject o) {
        super(o);
    }

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
}
