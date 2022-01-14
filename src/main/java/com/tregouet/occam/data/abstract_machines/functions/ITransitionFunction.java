package com.tregouet.occam.data.abstract_machines.functions;

import java.util.List;
import java.util.function.Predicate;

import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;

import com.tregouet.occam.alg.scoring.costs.ICosted;
import com.tregouet.occam.alg.scoring.scores.IScored;
import com.tregouet.occam.alg.scoring.scores.similarity.ISimilarityScorer;
import com.tregouet.occam.data.abstract_machines.IFiniteAutomaton;
import com.tregouet.occam.data.abstract_machines.functions.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transitions.ICostedTransition;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.languages.specific.IDomainSpecificLanguage;
import com.tregouet.tree_finder.data.Tree;

public interface ITransitionFunction extends ICosted, IScored {
	
	@Override
	boolean equals(Object o);
	
	IState getAssociatedStateOf(IDenotationSet denotationSet);
	
	IFiniteAutomaton getCompiler();
	
	List<IConjunctiveTransition> getConjunctiveTransitions();
	
	IDomainSpecificLanguage getDomainSpecificLanguage();
	
	SimpleDirectedGraph<IState, IConjunctiveTransition> getFiniteAutomatonGraph();
	
	DirectedMultigraph<IState, ITransition> getFiniteAutomatonMultigraph();
	
	Tree<IState, IGenusDifferentiaDefinition> getPorphyrianTree();
	
	ISimilarityScorer getSimilarityCalculator();
	
	List<IState> getStates();
	
	List<ICostedTransition> getTransitions();
	
	Tree<IDenotationSet, IIsA> getTreeOfDenotationSets();
	
	Tree<IDenotation, IProduction> getTreeOfDenotations();
	
	Tree<IDenotation, IProduction> getTreeOfDenotationsWithNoBlankProduction();
	
	@Override
	int hashCode();
	
	void setSimilarityScorer(ISimilarityScorer similarityScorer);
	
	boolean validate(Predicate<ITransitionFunction> validator);
	
}
