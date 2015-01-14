package demo.swing;

import java.awt.BorderLayout;
import java.awt.Font;
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
        JPanel ptext = new JPanel();
        p.setLayout(new BorderLayout());
        JButton nextBtn = new JButton("繼續");
        JLabel result = new JLabel();
        Answer a = (Answer) this.result;
        result.setText(a.validate() ? "正確" : "錯誤");
        
        //調整字體
        Font font = result.getFont();
        result.setFont(new Font(font.getName(), Font.PLAIN, 40));
        
        nextBtn.addActionListener(event -> {
            callback.accept(this);
        });
        ptext.add(result, BorderLayout.CENTER);
        p.add(nextBtn, BorderLayout.SOUTH);
        p.add(ptext);
        return p;
    }


    public void setCallback(Consumer<Object> callback) {
        this.callback = callback;
    }

}
