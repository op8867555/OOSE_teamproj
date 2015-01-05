package core;

@FunctionalInterface
public interface AnswerValidator {
    public abstract boolean validate(Object ans);
}
