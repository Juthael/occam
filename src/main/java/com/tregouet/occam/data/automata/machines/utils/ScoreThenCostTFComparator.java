package com.tregouet.occam.data.automata.machines.utils;
import java.util.Comparator;

import com.tregouet.occam.data.automata.machines.IAutomaton;

public class ScoreThenCostTFComparator implements Comparator<IAutomaton> {

	public static final ScoreThenCostTFComparator INSTANCE = new ScoreThenCostTFComparator();
	
	private ScoreThenCostTFComparator() {
	}

	@Override
	public int compare(IAutomaton tF1, IAutomaton tF2) {
		if (tF1 == tF2)
			return 0;
		double tF1Score = tF1.getScore();
		double tF2Score = tF2.getScore();
		if (tF1Score < tF2Score)
			return 1;
		if (tF1Score > tF2Score)
			return -1;
		double tF1Cost = tF1.getCost();
		double tF2Cost = tF2.getCost();
		if (tF1Cost < tF2Cost)
			return -1;
		if (tF1Cost > tF2Cost)
			return 1;
		//meaningless, only used to prevent element deletion in TreeSets
		return (System.identityHashCode(tF1) - System.identityHashCode(tF2));
	}

}
