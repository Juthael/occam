package com.tregouet.occam.data.representations.evaluation.facts;

import java.util.Iterator;

import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.structures.languages.words.IWord;
import com.tregouet.occam.data.structures.partial_order.PartiallyComparable;

public interface IFact extends IWord<IAbstractionApplication>, PartiallyComparable<IFact> {

	ILambdaExpression asLambda();

	@Override
	default Integer compareTo(IFact other) {
		Iterator<IAbstractionApplication> thisIte = this.asList().iterator();
		Iterator<IAbstractionApplication> otherIte = other.asList().iterator();
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
