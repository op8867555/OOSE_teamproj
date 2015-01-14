package crossAppliedTable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class CrossAppliedTable <T> {
    protected List<CrossApplication<T>> ls;
    protected BinaryOperator<T> applyFunction;
    
    public CrossAppliedTable(BinaryOperator<T> applyFunction, List<T> as, List<T> bs) {
        this.applyFunction = applyFunction;
    	apply(as, bs);
    }

    public void apply(List<T> as, List<T> bs) {
        ls = new ArrayList<CrossApplication<T>>();
        for (T a : as)
            for (T b : bs)
                ls.add(new CrossApplication<T>(a, b, applyFunction.apply(a, b)));
    }

    public CrossApplication<T> get(int idx) {
        return ls.get(idx);
    }

    public int size() {
        return ls.size();
    }

}
