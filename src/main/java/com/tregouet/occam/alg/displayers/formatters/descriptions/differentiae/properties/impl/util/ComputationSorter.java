package com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.properties.impl.util;

import java.util.Comparator;

import com.tregouet.occam.data.structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.structures.representations.productions.IProduction;
import com.tregouet.occam.data.structures.representations.transitions.impl.stack_default.vars.This;

public class ComputationSorter implements Comparator<IComputation> {
	
	public static final ComputationSorter INSTANCE = new ComputationSorter();
	
	private ComputationSorter() {
	}

	@Override
	public int compare(IComputation o1, IComputation o2) {
		int comparison = betterIfRelational(o1, o2);
		if (comparison == 0) {
			comparison = betterIfVarIsNotThis(o1, o2);
			if (comparison == 0) {
				comparison = betterIfFirstVarIsAlpabeticallySmaller(o1, o2);
				if (comparison == 0)
					comparison = System.identityHashCode(o1) - System.identityHashCode(o2);
			}
		}
		return comparison;
	}
	
	private static int betterIfFirstVarIsAlpabeticallySmaller(IComputation o1, IComputation o2) {
		AVariable first = o1.getOperator().getBindings().getBoundVariables().get(0);
		AVariable second = o2.getOperator().getBindings().getBoundVariables().get(0);
		return first.getName().compareTo(second.getName());
	}
	
	private static int betterIfRelational(IComputation o1, IComputation o2) {
		if (isRelational(o1)) {
			if (isRelational(o2)) {
				return 0;
			}
			return -1;
		}
		if (isRelational(o2))
			return 1;
		return 0;
	}
	
	private static int betterIfVarIsNotThis(IComputation o1, IComputation o2) {
		if (o1.getOperator().getBindings().getBoundVariables().get(0).equals(This.INSTANCE)) {
			if (o2.getOperator().getBindings().getBoundVariables().get(0).equals(This.INSTANCE)) {
				return 0;
			}
			return 1;
		}
		if (o2.getOperator().getBindings().getBoundVariables().get(0).equals(This.INSTANCE))
			return -1;
		return 0;
	}
	
	private static boolean isRelational(IComputation o1) {
		for (IProduction prod : o1.getOperator().getArguments()) {
			if (prod.isRelational())
				return true;
		}
		return false;
	}
	
	

}
