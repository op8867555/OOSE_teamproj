package demo.swing;

import java.util.function.*;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import core.Question;
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
        return new JPanel();
    }

    public void setCallback(Consumer<Object> callback) {
        Question q = (Question) this.result;
        this.callback = o -> {
            JOptionPane.showMessageDialog(null, q.validate() ? "正確" : "錯誤");
            callback.accept(o);
        };
    }

}
