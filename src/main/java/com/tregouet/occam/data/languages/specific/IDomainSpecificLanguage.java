package com.tregouet.occam.data.languages.specific;

public interface IDomainSpecificLanguage {
	
	String getDomainSpecificGrammarOfOperators();
	
	String getOperatorsDescription();
	
	@Override
	String toString();

}
