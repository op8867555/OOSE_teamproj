package core;

public class EndScreen extends Screen {
    private EndScreen() {}
    private static EndScreen inst;
    public static EndScreen getInstance() {
        if (inst == null)
            inst = new EndScreen();
        return inst;
    }
}
