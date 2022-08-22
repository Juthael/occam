package com.tregouet.occam.data.representations.evaluation.facts.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.abstr_app.impl.OmegaOperator;
import com.tregouet.occam.data.representations.evaluation.facts.IFact;
import com.tregouet.occam.data.structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.structures.lambda_terms.impl.LambdaAbstrApp;
import com.tregouet.occam.data.structures.languages.words.construct.IConstruct;

public class Fact implements IFact {

	private final List<IAbstractionApplication> operatorList;

	public Fact(List<IAbstractionApplication> operatorList) {
		this.operatorList = operatorList;
	}

	@Override
	public ILambdaExpression asLambda() {
		if (operatorList.isEmpty() || !operatorList.get(0).equals(OmegaOperator.INSTANCE))
			return null;
		IConstruct initialThis = OmegaOperator.INSTANCE.getArguments().get(0).getValue();
		if (operatorList.size() == 1)
			return initialThis;
		else {
			ILambdaExpression exp = new LambdaAbstrApp(initialThis, operatorList.get(1));
			for (int i = 2 ; i < operatorList.size() ; i++) {
				exp = exp.abstractAndApply(operatorList.get(i), false);
			}
			return exp;
		}
	}

	@Override
	public List<IAbstractionApplication> asList() {
		return new ArrayList<>(operatorList);
	}

	@Override
	public IFact copy() {
		return new Fact(asList());
	}

	@Override
	public int size() {
		return operatorList.size();
	}

}
