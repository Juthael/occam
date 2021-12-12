package com.tregouet.occam.data.abstract_machines.functions;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;

import com.tregouet.occam.alg.score_calc.similarity_calc.ISimilarityCalculator;
import com.tregouet.occam.data.abstract_machines.IFiniteAutomaton;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;
import com.tregouet.occam.data.concepts.IClassification;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.languages.specific.IDomainSpecificLanguage;
import com.tregouet.tree_finder.data.Tree;

public interface ITransitionFunction extends Comparable<ITransitionFunction> {
	
	@Override
	boolean equals(Object o);
	
	double[][] getAsymmetricalSimilarityMatrix();
	
	Tree<IConcept, IsA> getCategoryTree();
	
	String getCategoryTreeAsDOTFile();
	
	IClassification getClassification();
	
	double getCoherenceScore();
	
	IFiniteAutomaton getCompiler();
	
	Map<Integer, Double> getConceptualCoherenceMap();
	
	List<IConjunctiveTransition> getConjunctiveTransitions();
	
	IDomainSpecificLanguage getDomainSpecificLanguage();
	
	SimpleDirectedGraph<IState, IConjunctiveTransition> getFiniteAutomatonGraph();
	
	DirectedMultigraph<IState, ITransition> getFiniteAutomatonMultigraph();
	
	ISimilarityCalculator getSimilarityCalculator();
	
	double[][] getSimilarityMatrix();
	
	List<IState> getStates();
	
	String getTransitionFunctionAsDOTFile(TransitionFunctionGraphType graphType);
	
	double getTransitionFunctionCost();
	
	List<ITransition> getTransitions();
	
	double[] getTypicalityArray();
	
	@Override
	int hashCode();
	
	boolean validate(Predicate<ITransitionFunction> validator);

}
