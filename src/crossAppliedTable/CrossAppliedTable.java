package crossAppliedTable;

import java.util.ArrayList;
import java.util.List;

abstract public class CrossAppliedTable <T> {
    protected List<CrossApplication<T>> ls;

    public CrossAppliedTable(List<T> as, List<T> bs) {
        apply(as, bs);
    }

    abstract protected T apply(T a, T b);

    public void apply(List<T> as, List<T> bs) {
        ls = new ArrayList<CrossApplication<T>>();
        for (T a : as)
            for (T b : bs)
                ls.add(new CrossApplication<T>(a, b, apply(a, b)));
    }

    public CrossApplication<T> get(int idx) {
        return ls.get(idx);
    }

    public int size() {
        return ls.size();
    }

}
