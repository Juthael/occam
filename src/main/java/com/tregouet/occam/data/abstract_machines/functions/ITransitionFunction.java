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
import com.tregouet.occam.data.abstract_machines.transition_rules.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transition_rules.ICostedTransition;
import com.tregouet.occam.data.abstract_machines.transition_rules.ITransitionRule;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.languages.specific.IDomainSpecificLanguageDisplayer;
import com.tregouet.occam.data.languages.specific.IProduction;
import com.tregouet.tree_finder.data.Tree;

public interface ITransitionFunction extends ICosted, IScored {
	
	@Override
	boolean equals(Object o);
	
	IState getAssociatedStateOf(IDenotationSet denotationSet);
	
	IFiniteAutomaton getCompiler();
	
	List<IConjunctiveTransition> getConjunctiveTransitions();
	
	IDomainSpecificLanguageDisplayer getDomainSpecificLanguage();
	
	SimpleDirectedGraph<IState, IConjunctiveTransition> getFiniteAutomatonGraph();
	
	DirectedMultigraph<IState, ITransitionRule> getFiniteAutomatonMultigraph();
	
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
