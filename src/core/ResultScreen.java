package core;

public class ResultScreen extends Screen {
    protected Object result;

    public ResultScreen(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return this.result;
    }
}
