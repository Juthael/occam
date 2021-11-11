package com.tregouet.occam.transition_function;

import java.util.List;
import java.util.function.Predicate;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;

import com.tregouet.occam.cost_calculation.property_weighing.IPropertyWeigher;
import com.tregouet.occam.cost_calculation.similarity_calculation.ISimilarityCalculator;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.impl.IsA;
import com.tregouet.occam.data.operators.IConjunctiveOperator;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.finite_automaton.IFiniteAutomaton;
import com.tregouet.tree_finder.data.Tree;

public interface ITransitionFunction extends Comparable<ITransitionFunction> {
	
	@Override
	boolean equals(Object o);
	
	Tree<ICategory, IsA> getCategoryTree();
	
	String getCategoryTreeAsDOTFile();
	
	double getCoherenceScore();
	
	IFiniteAutomaton getCompiler();
	
	List<IConjunctiveOperator> getConjunctiveTransitions();
	
	IDSLanguageDisplayer getDomainSpecificLanguage();
	
	IPropertyWeigher getInfometer();
	
	ISimilarityCalculator getSimilarityCalculator();
	
	List<IState> getStates();
	
	SimpleDirectedGraph<IState, IConjunctiveOperator> getFiniteAutomatonGraph();
	
	DirectedMultigraph<IState, IOperator> getFiniteAutomatonMultigraph();
	
	String getTransitionFunctionAsDOTFile(TransitionFunctionGraphType graphType);
	
	List<IOperator> getTransitions();
	
	boolean validate(Predicate<ITransitionFunction> validator);
	
	@Override
	int hashCode();

}
