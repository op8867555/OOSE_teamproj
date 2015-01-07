package demo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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


        TestView view = new TestView(sg);
        MainCtrl ctrl = new MainCtrl(view);

        ctrl.getFrame().setVisible(true);
    }
}

abstract class View {
    protected JFrame frame = new JFrame();
    public JFrame getFrame() {
        return frame;
    }

}


class MainCtrl extends View {
    protected TestView testView;
    public MainCtrl(TestView testView) {
        super();
        this.frame.setLayout(new FlowLayout());
        this.testView = testView;
        JLabel welcom = new JLabel("歡迎使用這個有點鳥的幼教系統~");
        JButton startBtn = new JButton("開始作答");
        JButton learnBtn = new JButton("學習");
        JButton exitBtn  = new JButton("離開");
        startBtn.addActionListener(e -> {
            this.frame.setVisible(false);
            this.testView.render(null);
        });
        
        Font font = welcom.getFont();
        welcom.setFont(new Font(font.getName(), Font.PLAIN, 50));
        startBtn.setFont(new Font(font.getName(), Font.PLAIN, 30));
        learnBtn.setFont(new Font(font.getName(), Font.PLAIN, 30));
        exitBtn.setFont(new Font(font.getName(), Font.PLAIN, 30));
        
        learnBtn.addActionListener(e -> {
        	startBtn.setVisible(false);
        	exitBtn.setVisible(false);
        	learnBtn.setVisible(false);
        	
        	JButton backBtn = new JButton("返回");
        	frame.add(backBtn);
        	
        	backBtn.setFont(new Font(font.getName(), Font.PLAIN, 30));
        	
        	JLabel img = new JLabel(new ImageIcon(".\\img\\九九乘法表.jpg"));
        	frame.add(img);
        	
        	backBtn.addActionListener(event -> {
        		startBtn.setVisible(true);
            	exitBtn.setVisible(true);
            	learnBtn.setVisible(true);
            	backBtn.setVisible(false);
            	img.setVisible(false);
        	});
        });

        exitBtn.addActionListener(e -> {
            this.frame.setVisible(false);
            this.frame.dispose();
            System.exit(0);
        });
        frame.add(welcom);
        frame.add(startBtn);
        frame.add(learnBtn);
        frame.add(exitBtn);
        frame.setSize(800, 640);
    }

}

class TestView extends View {

    protected ScreenGenerator sg;

    public TestView(ScreenGenerator sg) {
        super();
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
            //調整字體
            Font font = btn.getFont();
            btn.setFont(new Font(font.getName(), Font.PLAIN, 20));

            btns.add(btn);
            options.add(btn);
            bg.add(btn);
        });

        submitBtn.addActionListener(e -> {
            q.setAnswer(btns.stream().filter(JRadioButton::isSelected).findFirst()
                    .map(JRadioButton::getText)
                    .map(Integer::new).orElse(null));
            callback.accept(scr);
        });

        this.panel.removeAll();
        this.panel.setLayout(new BorderLayout());
        this.panel.add(displayQuestion(q), BorderLayout.NORTH);
        this.panel.add(options, BorderLayout.CENTER);
        this.panel.add(submitBtn, BorderLayout.SOUTH);
        return this.panel;
    }

    static JComponent displayQuestion(Question rq) {
        BinaryMultiplicationQuestion q = (BinaryMultiplicationQuestion) rq;
        JLabel l = new JLabel(q.getA() + " * " + q.getB() + " = " + " ?\n", JLabel.CENTER);
        //調整字體
        Font font = l.getFont();
        l.setFont(new Font(font.getName(), Font.BOLD, 32));
        return l;
    }
}

