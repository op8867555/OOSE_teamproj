package core;

public abstract class ScreenGenerator {
    protected Question qs;
    public abstract Screen getNextScreen(Question q);
}
