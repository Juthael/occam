package com.tregouet.occam.data.abstract_machines.functions;

import java.util.List;
import java.util.function.Predicate;

import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;

import com.tregouet.occam.alg.calculators.costs.ICosted;
import com.tregouet.occam.alg.calculators.scores.IScored;
import com.tregouet.occam.alg.calculators.scores.similarity.ISimilarityScorer;
import com.tregouet.occam.data.abstract_machines.IFiniteAutomaton;
import com.tregouet.occam.data.abstract_machines.functions.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.data.languages.specific.IDomainSpecificLanguage;
import com.tregouet.tree_finder.data.Tree;

public interface ITransitionFunction extends ICosted, IScored {
	
	@Override
	boolean equals(Object o);
	
	IFiniteAutomaton getCompiler();
	
	List<IConjunctiveTransition> getConjunctiveTransitions();
	
	IDomainSpecificLanguage getDomainSpecificLanguage();
	
	SimpleDirectedGraph<IState, IConjunctiveTransition> getFiniteAutomatonGraph();
	
	DirectedMultigraph<IState, ITransition> getFiniteAutomatonMultigraph();
	
	ISimilarityScorer getSimilarityCalculator();
	
	List<IState> getStates();
	
	String getTransitionFunctionAsDOTFile(TransitionFunctionGraphType graphType);
	
	List<ITransition> getTransitions();
	
	Tree<IConcept, IIsA> getTreeOfConcepts();
	
	String getTreeOfConceptsAsDOTFile();
	
	@Override
	int hashCode();
	
	boolean validate(Predicate<ITransitionFunction> validator);
	
	Tree<IState, IGenusDifferentiaDefinition> getPorphyrianTree();
	
	IState getAssociatedStateOf(IConcept concept);
	
}
