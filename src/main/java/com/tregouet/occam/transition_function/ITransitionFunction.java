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
import com.tregouet.occam.data.transitions.ITransition;
import com.tregouet.occam.finite_automaton.IFiniteAutomaton;
import com.tregouet.tree_finder.data.Tree;

public interface ITransitionFunction extends Comparable<ITransitionFunction> {
	
	@Override
	boolean equals(Object o);
	
	double[][] getAsymmetricalSimilarityMatrix();
	
	Tree<IConcept, IsA> getCategoryTree();
	
	String getCategoryTreeAsDOTFile();
	
	double getCoherenceScore();
	
	IFiniteAutomaton getCompiler();
	
	Map<Integer, Double> getConceptualCoherenceMap();
	
	List<IConjunctiveTransition> getConjunctiveTransitions();
	
	IDSLanguageDisplayer getDomainSpecificLanguage();
	
	SimpleDirectedGraph<IState, IConjunctiveTransition> getFiniteAutomatonGraph();
	
	DirectedMultigraph<IState, ITransition> getFiniteAutomatonMultigraph();
	
	ISimilarityCalculator getSimilarityCalculator();
	
	double[][] getSimilarityMatrix();
	
	List<IState> getStates();
	
	String getTransitionFunctionAsDOTFile(TransitionFunctionGraphType graphType);
	
	List<ITransition> getTransitions();
	
	double[] getTypicalityArray();
	
	@Override
	int hashCode();
	
	boolean validate(Predicate<ITransitionFunction> validator);

}
