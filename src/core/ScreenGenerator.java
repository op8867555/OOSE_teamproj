package core;

public abstract class ScreenGenerator {
    protected Question qs;
    protected abstract Screen getNextScreen(Question q);
}
