package com.tregouet.occam.transition_function;

public interface IDSLanguageDisplayer {
	
	String getDomainSpecificGrammarOfOperators();
	
	String getOperatorsDescription();
	
	@Override
	String toString();

}
