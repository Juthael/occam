package com.tregouet.occam.data.logical_structures.scores.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.logical_structures.scores.IScore;

public class LecticScore implements IScore<LecticScore> {

	protected List<Double> values;

	public LecticScore(List<Double> values) {
		this.values = values;
	}

	@Override
	public int compareTo(LecticScore o) {
		Iterator<Double> thisIte = values.iterator();
		Iterator<Double> otherIte = o.values.iterator();
		int comparison = iterativelyCompareTo(thisIte, otherIte);
		if (comparison == 0)
			// for consistency with Object.equals(), which is not overloaded
			return System.identityHashCode(this) - System.identityHashCode(o);
		return comparison;
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		sB.append("(");
		Iterator<Double> valueIte = values.iterator();
		while (valueIte.hasNext()) {
			sB.append(IScore.round(valueIte.next()));
			if (valueIte.hasNext())
				sB.append(" ; ");
		}
		sB.append(")");
		return sB.toString();
	}

	private int iterativelyCompareTo(Iterator<Double> thisIte, Iterator<Double> otherIte) {
		while (thisIte.hasNext()) {
			if (otherIte.hasNext()) {
				int localComparison = Double.compare(thisIte.next(), otherIte.next());
				if (localComparison == 0)
					return iterativelyCompareTo(thisIte, otherIte);
				else
					return localComparison;
			}
			return 1;
		}
		if (otherIte.hasNext())
			return -1;
		return 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(values);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof LecticScore))
			return false;
		LecticScore other = (LecticScore) obj;
		return this.compareTo(other) == 0;
	}

}
