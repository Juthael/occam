package com.tregouet.occam.data.languages.words.lambda;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IProduction;

public interface ILambdaExpression {
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();
	
	boolean abstractAndApplyAccordingTo(IProduction production);
	
	@Override
	String toString();
	
	boolean isAnApplication();

}
