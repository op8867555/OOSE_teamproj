package core;

public class EndScreen extends Screen {
    protected EndScreen() {}
    private static EndScreen inst;
    public static EndScreen getInstance() {
        if (inst == null)
            inst = new EndScreen();
        return inst;
    }
}
