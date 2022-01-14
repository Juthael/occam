package com.tregouet.occam.data.languages.specific;

public interface IDomainSpecificLanguageDisplayer {
	
	String getDomainSpecificGrammarOfOperators();
	
	String getOperatorsDescription();
	
	@Override
	String toString();

}
