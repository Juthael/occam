package com.tregouet.occam.data.problem_space.states.evaluation.facts;

import java.util.Iterator;

import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.logical_structures.languages.words.IWord;
import com.tregouet.occam.data.logical_structures.partial_order.PartiallyComparable;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;

public interface IFact extends IWord<IApplication>, PartiallyComparable<IFact> {

	ILambdaExpression asLambda();

	@Override
	default Integer compareTo(IFact other) {
		Iterator<IApplication> thisIte = this.asList().iterator();
		Iterator<IApplication> otherIte = other.asList().iterator();
		while (thisIte.hasNext()) {
			if (otherIte.hasNext()) {
				if (!thisIte.next().equals(otherIte.next()))
					return null;
			}
			else return 1;
		}
		if (otherIte.hasNext())
			return -1;
		return null;
	}

}
