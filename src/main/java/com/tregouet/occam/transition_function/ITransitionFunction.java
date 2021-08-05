package com.tregouet.occam.transition_function;

import java.util.List;

import com.tregouet.occam.compiler.ICompiler;
import com.tregouet.occam.data.operators.IOperator;

public interface ITransitionFunction extends Comparable<ITransitionFunction> {
	
	double getCost();
	
	String getCategoryStructureAsDOTFile();
	
	String getTransitionFunctionAsDOTFile();
	
	List<IState> getStates();
	
	List<IOperator> getTransitions();
	
	IDSLanguageDisplayer getDomainSpecificLanguage();
	
	ICompiler getCompiler();
	
	int hashCode();
	
	boolean equals(Object o);

}
