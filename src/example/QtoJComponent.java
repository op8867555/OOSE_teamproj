package example;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

import javax.swing.*;

import core.AnswerType;
import core.AnswerValidator;
import core.AnsweringScreen;
import core.Question;
import core.Screen;

public class QtoJComponent extends Question {
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 640;
	public QtoJComponent(AnswerValidator av, AnswerType at) {
		super(av, at);
		// TODO Auto-generated constructor stub
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setTitle("test");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout( ));
		
		Answers at = new Answers();
		Seq sg = new Seq(new BinaryMultiplicationQuestionGenerator(at));
		Screen s = sg.getNextScreen(null);
		AnsweringScreen as = (AnsweringScreen) s;
		BinaryMultiplicationQuestion q;
		q = (BinaryMultiplicationQuestion) as.getQuestion();
		
		JLabel Qlable = new JLabel(q.getX() + " * " + q.getY() + " = " + " ?\n", JLabel.CENTER);
		
		ButtonGroup group1 = new ButtonGroup();
		JRadioButton radio1 = new JRadioButton("r1");
		JRadioButton radio2 = new JRadioButton("r2");
		JRadioButton radio3 = new JRadioButton("r3");
		JRadioButton radio4 = new JRadioButton("r4");
		group1.add(radio1);
		group1.add(radio2);
		group1.add(radio3);
		group1.add(radio4);
		
		frame.add(Qlable);
		frame.add(radio1);
		frame.add(radio2);
		frame.add(radio3);
		frame.add(radio4);
		
		frame.pack();
		frame.setVisible(true);
	}
	
}

