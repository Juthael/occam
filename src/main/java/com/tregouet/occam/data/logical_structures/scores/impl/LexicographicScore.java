package com.tregouet.occam.data.logical_structures.scores.impl;

import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.logical_structures.scores.IScore;

public class LexicographicScore implements IScore<LexicographicScore> {

	private List<Double> values;
	
	public LexicographicScore(List<Double> values) {
		this.values = values;
	}
	
	@Override
	public int compareTo(LexicographicScore o) {
		Iterator<Double> thisIte = values.iterator();
		Iterator<Double> otherIte = o.values.iterator();
		while (thisIte.hasNext()) {
			if (otherIte.hasNext())
				return Double.compare(thisIte.next(), otherIte.next());
			else return 1;
		}
		if (otherIte.hasNext())
			return -1;
		return 0;
	}
	
	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		sB.append("(");
		Iterator<Double> valueIte = values.iterator();
		while(valueIte.hasNext()) {
			sB.append(valueIte.next());
			if (valueIte.hasNext())
				sB.append(" ; ");
		}
		sB.append(")");
		return sB.toString();
	}

}
