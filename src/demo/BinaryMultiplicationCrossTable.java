package demo;

import java.util.List;

import crossAppliedTable.CrossAppliedTable;

public class BinaryMultiplicationCrossTable extends CrossAppliedTable<Integer> {

	public BinaryMultiplicationCrossTable(List<Integer> as,
			List<Integer> bs) {
		super((a, b) -> a * b, as, bs);
	}

}
