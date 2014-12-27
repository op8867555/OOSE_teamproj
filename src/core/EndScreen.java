package core;

public class EndScreen extends Screen {
    private EndScreen() {}
    private EndScreen inst;
    public EndScreen getInstance() {
        if (inst == null)
            inst = new EndScreen();
        return inst;
    }
}
