package demo.swing;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import core.AnsweringScreen;
import core.Question;

class JRadioButtonValued extends JRadioButton {
    protected Object value;

    public JRadioButtonValued(Object value) {
        super();
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

public class AnsweringScreenSwing
    extends AnsweringScreen
    implements SwingRenderable {

    protected Consumer<AnsweringScreen> callback = s -> {};

    public AnsweringScreenSwing(Question q) {
        super(q);
    }

    @Override
    public JComponent render() {
        JPanel panel = new JPanel();
        JPanel options = new JPanel();
        JButton submitBtn = new JButton("submit");
        options.removeAll();
        List<JRadioButton> btns = new ArrayList<JRadioButton>();
        ButtonGroup bg = new ButtonGroup();

        q.getOptions().forEach(obj -> {
            JRadioButton btn = new JRadioButtonValued(obj);
            btn.setText(obj.toString());
            //調整字體
            Font font = btn.getFont();
            btn.setFont(new Font(font.getName(), Font.PLAIN, 20));

            btns.add(btn);
            options.add(btn);
            bg.add(btn);
        });
        submitBtn.addActionListener(e -> {
            q.setAnswer(btns.stream().filter(JRadioButton::isSelected).findFirst()
                    .map(x -> ((JRadioButtonValued)x).getValue())
                    .orElse(null));
            callback.accept(this);
        });

        panel.removeAll();
        panel.setLayout(new BorderLayout());
        JComponent question = ((SwingRenderable) q).render();
        panel.add(question, BorderLayout.NORTH);
        panel.add(options, BorderLayout.CENTER);
        panel.add(submitBtn, BorderLayout.SOUTH);
        return panel;
    }

    public void setCallback(Consumer<AnsweringScreen> callback) {
        this.callback = callback;
    }

}
