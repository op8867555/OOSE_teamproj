package demo.swing;

import java.awt.BorderLayout;
import java.util.function.*;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.SwingConstants;

import core.Answer;
import core.ResultScreen;

public class ResultScreenSwing
    extends ResultScreen
    implements SwingRenderable {

    protected Consumer<Object> callback = s -> {};

    public ResultScreenSwing(Object result) {
        super(result);
    }

    @Override
    public JComponent render() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        JButton nextBtn = new JButton("繼續");
        JLabel result = new JLabel();
        Answer a = (Answer) this.result;
        result.setText(a.validate() ? "正確" : "錯誤");
        result.setAlignmentX(SwingConstants.CENTER);
        nextBtn.addActionListener(event -> {
            callback.accept(this);
        });
        p.add(result, BorderLayout.CENTER);
        p.add(nextBtn, BorderLayout.SOUTH);
        return p;
    }


    public void setCallback(Consumer<Object> callback) {
        this.callback = callback;
    }

}
