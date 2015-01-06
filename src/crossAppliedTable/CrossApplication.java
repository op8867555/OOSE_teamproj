package crossAppliedTable;

public class CrossApplication <T> {
    protected T a, b, x;

    public CrossApplication(T a, T b, T x) {
        this.a = a;
        this.b = b;
        this.x = x;
    }

    public T getA() {
        return a;
    }

    public T getB() {
        return b;
    }

    public T getX() {
        return x;
    }

}
