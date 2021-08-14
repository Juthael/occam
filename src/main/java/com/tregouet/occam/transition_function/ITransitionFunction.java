package com.tregouet.occam.transition_function;

import java.util.List;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.compiler.ICompiler;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.tree_finder.data.InTree;

public interface ITransitionFunction extends Comparable<ITransitionFunction> {
	
	@Override
	boolean equals(Object o);
	
	InTree<ICategory, DefaultEdge> getCategoryTree();
	
	String getCategoryTreeAsDOTFile();
	
	ICompiler getCompiler();
	
	double getCost();
	
	IDSLanguageDisplayer getDomainSpecificLanguage();
	
	List<IState> getStates();
	
	String getTransitionFunctionAsDOTFile();
	
	List<IOperator> getTransitions();
	
	@Override
	int hashCode();

}
