package com.tregouet.occam.transition_function;

import java.util.List;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.compiler.ICompiler;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.operators.IConjunctiveOperator;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.tree_finder.data.ClassificationTree;

public interface ITransitionFunction extends Comparable<ITransitionFunction> {
	
	@Override
	boolean equals(Object o);
	
	ClassificationTree<ICategory, DefaultEdge> getCategoryTree();
	
	String getCategoryTreeAsDOTFile();
	
	ICompiler getCompiler();
	
	IDSLanguageDisplayer getDomainSpecificLanguage();
	
	List<IState> getStates();
	
	String getTransitionFunctionAsDOTFile();
	
	String getTFWithConjunctiveOperatorsAsDOTFile();
	
	List<IOperator> getTransitions();
	
	List<IConjunctiveOperator> getConjunctiveTransitions();
	
	@Override
	int hashCode();
	
	ISimilarityCalculator getSimilarityCalculator();
	
	double getCoherenceScore();
	
	IInfoMeter getInfometer();

}
