package com.tregouet.occam.transition_function;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;

import com.tregouet.occam.cost_calculation.similarity_calculation.ISimilarityCalculator;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.transitions.IOperator;
import com.tregouet.occam.data.transitions.ITransition;
import com.tregouet.occam.finite_automaton.IFiniteAutomaton;
import com.tregouet.tree_finder.data.Tree;

public interface ITransitionFunction extends Comparable<ITransitionFunction> {
	
	@Override
	boolean equals(Object o);
	
	Tree<IConcept, IsA> getCategoryTree();
	
	String getCategoryTreeAsDOTFile();
	
	double getCoherenceScore();
	
	IFiniteAutomaton getCompiler();
	
	List<IConjunctiveTransition> getConjunctiveTransitions();
	
	IDSLanguageDisplayer getDomainSpecificLanguage();
	
	ISimilarityCalculator getSimilarityCalculator();
	
	List<IState> getStates();
	
	SimpleDirectedGraph<IState, IConjunctiveTransition> getFiniteAutomatonGraph();
	
	DirectedMultigraph<IState, ITransition> getFiniteAutomatonMultigraph();
	
	String getTransitionFunctionAsDOTFile(TransitionFunctionGraphType graphType);
	
	List<ITransition> getTransitions();
	
	boolean validate(Predicate<ITransitionFunction> validator);
	
	double[][] getSimilarityMatrix();
	
	double[][] getAsymmetricalSimilarityMatrix();
	
	Map<Integer, Double> getConceptualCoherenceMap();
	
	double[] getTypicalityArray();
	
	@Override
	int hashCode();

}
