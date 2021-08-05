package com.tregouet.occam.transition_function;

import java.util.List;

import com.tregouet.occam.compiler.ICompiler;
import com.tregouet.occam.data.operators.IOperator;

public interface ITransitionFunction extends Comparable<ITransitionFunction> {
	
	@Override
	boolean equals(Object o);
	
	String getCategoryStructureAsDOTFile();
	
	ICompiler getCompiler();
	
	double getCost();
	
	IDSLanguageDisplayer getDomainSpecificLanguage();
	
	List<IState> getStates();
	
	String getTransitionFunctionAsDOTFile();
	
	List<IOperator> getTransitions();
	
	@Override
	int hashCode();

}
