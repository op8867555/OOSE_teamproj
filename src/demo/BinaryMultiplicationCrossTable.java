package demo;

import java.util.List;

import crossAppliedTable.CrossAppliedTable;

class BinaryMultiplicationCrossTable extends CrossAppliedTable<Integer> {

    public BinaryMultiplicationCrossTable(List<Integer> as, List<Integer> bs) {
        super(as, bs);
    }

    @Override
    protected Integer apply(Integer a, Integer b) {
        return a * b;
    }
}
