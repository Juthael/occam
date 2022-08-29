package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.DifferentiationSetBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.differentiation.DifferentiationBuilder;
import com.tregouet.occam.data.structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiationSet;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.impl.DifferentiationSet;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;

public class HeuristicOrderOverProperties implements DifferentiationSetBuilder {

	private static class PreferenceOrder implements Comparator<IProperty> {

		static final PreferenceOrder INSTANCE = new PreferenceOrder();

		private PreferenceOrder() {
		}

		@Override
		public int compare(IProperty o1, IProperty o2) {
			int comparison = betterIfLessSignificantComputations(o1, o2);
			if (comparison == 0) {
				comparison = betterIfHeavier(o1, o2);
				if (comparison == 0) {
					comparison = betterIfInstantiatesLessVariables(o1, o2);
					if (comparison == 0) {
						comparison = betterIfInstantiatedVarsLexicographicallySmaller(o1, o2);
						if (comparison == 0) //never happens
							return System.identityHashCode(o1) - System.identityHashCode(o2);
					}
				}
			}
			return comparison;
		}

		private static int betterIfHeavier(IProperty o1, IProperty o2) {
			if (o1.weight() > o2.weight())
				return -1;
			else if (o2.weight() > o1.weight())
				return 1;
			else return 0;
		}

		private static int betterIfInstantiatesLessVariables(IProperty o1, IProperty o2) {
			return nbOfVarInstantiated(o1) - nbOfVarInstantiated(o2);
		}

		private static int betterIfLessSignificantComputations(IProperty o1, IProperty o2) {
			return o1.getNbOfSignificantComputations() - o2.getNbOfSignificantComputations();
		}
		
		private static int betterIfInstantiatedVarsLexicographicallySmaller(IProperty o1, IProperty o2) {
			String o1Bindings = toString(o1.getFunction().getVariables());
			String o2Bindings = toString(o2.getFunction().getVariables());
			return o1Bindings.compareTo(o2Bindings);
		}

		private static int nbOfVarInstantiated(IProperty p) {
			Set<AVariable> variables = new HashSet<>();
			variables.addAll(p.getFunction().getVariables());
			for (IComputation computation : p.getComputations())
				variables.removeAll(computation.getOutput().getVariables());
			return variables.size();
		}
		
		private static String toString(List<AVariable> vars) {
			if (vars.size() == 1)
				return vars.get(0).toString();
			String varString = new String();
			for (AVariable var : vars)
				varString.concat(var.toString());
			return varString;
		}

	}

	private DifferentiationBuilder differentiationBuilder = null;

	public HeuristicOrderOverProperties() {
	}

	@Override
	public IDifferentiationSet apply(Set<IProperty> properties) {
		List<IProperty> propList = new ArrayList<>(properties);
		Collections.sort(propList, PreferenceOrder.INSTANCE);
		List<IDifferentiation> differentiationList = new ArrayList<>(1);
		differentiationList.add(differentiationBuilder.apply(propList));
		return new DifferentiationSet(differentiationList);
	}

	@Override
	public DifferentiationSetBuilder setUp(DifferentiationBuilder differentiationBuilder) {
		this.differentiationBuilder = differentiationBuilder;
		return this;
	}

}
