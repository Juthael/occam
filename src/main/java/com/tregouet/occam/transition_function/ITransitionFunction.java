package com.tregouet.occam.transition_function;

import java.util.List;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.compiler.ICompiler;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.operators.IConjunctiveOperator;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.tree_finder.data.Tree;

public interface ITransitionFunction extends Comparable<ITransitionFunction> {
	
	@Override
	boolean equals(Object o);
	
	Tree<ICategory, DefaultEdge> getCategoryTree();
	
	String getCategoryTreeAsDOTFile();
	
	double getCoherenceScore();
	
	ICompiler getCompiler();
	
	List<IConjunctiveOperator> getConjunctiveTransitions();
	
	IDSLanguageDisplayer getDomainSpecificLanguage();
	
	IInfoMeter getInfometer();
	
	ISimilarityCalculator getSimilarityCalculator();
	
	List<IState> getStates();
	
	String getTFWithConjunctiveOperatorsAsDOTFile();
	
	String getTransitionFunctionAsDOTFile();
	
	List<IOperator> getTransitions();
	
	@Override
	int hashCode();

}
