package com.tregouet.occam.data.representations.evaluation.facts;

import java.util.Iterator;

import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.logical_structures.languages.words.IWord;
import com.tregouet.occam.data.logical_structures.orders.partial.PartiallyComparable;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;

public interface IFact extends IWord<IContextualizedProduction>, PartiallyComparable<IFact> {

	ILambdaExpression asLambda();
	
	@Override
	default Integer compareTo(IFact other) {
		Iterator<IContextualizedProduction> thisIte = this.asList().iterator();
		Iterator<IContextualizedProduction> otherIte = other.asList().iterator();
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
