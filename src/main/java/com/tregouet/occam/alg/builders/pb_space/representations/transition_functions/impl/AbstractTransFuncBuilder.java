package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.impl.Everything;
import com.tregouet.occam.data.problem_space.states.productions.IClassificationProductions;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;
import com.tregouet.occam.data.problem_space.states.transitions.dimensions.EpsilonDimension;
import com.tregouet.occam.data.problem_space.states.transitions.impl.Application;
import com.tregouet.occam.data.problem_space.states.transitions.impl.ClosureTransition;
import com.tregouet.occam.data.problem_space.states.transitions.impl.ConceptTransitionIC;
import com.tregouet.occam.data.problem_space.states.transitions.impl.ConceptTransitionOIC;
import com.tregouet.occam.data.problem_space.states.transitions.impl.InheritanceTransition;
import com.tregouet.occam.data.problem_space.states.transitions.impl.InitialTransition;
import com.tregouet.occam.data.problem_space.states.transitions.impl.RepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.states.transitions.impl.SpontaneousTransition;
import com.tregouet.tree_finder.data.InvertedTree;

public abstract class AbstractTransFuncBuilder implements RepresentationTransFuncBuilder {

	public AbstractTransFuncBuilder() {
	}

	private static Set<IConceptTransition> buildApplicationsAndUnclosedInheritances(IClassification classification,
			Set<IContextualizedProduction> relevantApplications) {
		Set<IConceptTransition> transitions = new HashSet<>();
		for (IContextualizedProduction production : relevantApplications) {
			if (!production.isEpsilon()) {
				int speciesID = production.getSubordinateID();
				int genusID = classification.getGenusID(speciesID);
				if (production.isBlank()) {
					//blank : substitution of a variable by itself. Instead, ε-transition with same variable remaining on top
					transitions.add(new InheritanceTransition(genusID, speciesID, production.getSource(), 
							production.getTarget(), production.getVariable()));
				}
				else {
					AVariable poppedStackSymbol = production.getVariable();
					IConceptTransitionIC inputConfig = 
							new ConceptTransitionIC(genusID, production.getUncontextualizedProduction(), poppedStackSymbol);
					List<AVariable> newBoundVariables = production.getValue().getVariables();
					if (newBoundVariables.isEmpty()) {
						IConceptTransitionOIC outputConfig = new ConceptTransitionOIC(speciesID,
								new ArrayList<>(Arrays.asList(new AVariable[] {EpsilonDimension.INSTANCE})));
						transitions.add(new Application(inputConfig, outputConfig));
					}
					else for (AVariable pushedStackSymbol : newBoundVariables) {
						IConceptTransitionOIC outputConfig = new ConceptTransitionOIC(speciesID,
								new ArrayList<>(Arrays.asList(new AVariable[] {pushedStackSymbol})));
						transitions.add(new Application(inputConfig, outputConfig));
					}
				}
			}
		}
		return transitions;
	}

	private static Set<IConceptTransition> buildClosures(Set<IConceptTransition> applications) {
		Set<IConceptTransition> closures = new HashSet<>();
		for (IConceptTransition application : applications) {
			IConceptTransitionIC inputConfig = application.getInputConfiguration();
			if (inputConfig.getInputSymbol().getValue().getVariables().size() > 0)
				closures.add(new ClosureTransition(inputConfig,
					application.getOutputInternConfiguration().getOutputStateID()));
		}
		return closures;
	}

	private static IConceptTransition buildInitialTransition(IClassification classification) {
		Everything everything = (Everything) classification.asGraph().getRoot();
		return new InitialTransition(everything);
	}

	private static Set<IConceptTransition> buildSpontaneousTransitions(IClassification classification) {
		Set<IConceptTransition> spontaneousTransitions = new HashSet<>();
		InvertedTree<IConcept, IIsA> conceptTree = classification.asGraph();
		Set<IIsA> edges = new HashSet<>(conceptTree.edgeSet());
		for (IIsA edge : edges) {
			spontaneousTransitions.add(
					new SpontaneousTransition(conceptTree.getEdgeTarget(edge).iD(), conceptTree.getEdgeSource(edge).iD()));
		}
		return spontaneousTransitions;
	}

	private static Set<IConceptTransition> buildClosedInheritances(IClassification classification) {
		Set<IConceptTransition> closedInheritances = new HashSet<>();
		InvertedTree<IConcept, IIsA> conceptTree = classification.asGraph();
		Set<IIsA> edges = new HashSet<>(conceptTree.edgeSet());
		for (IIsA edge : edges) {
			closedInheritances.add(
					new InheritanceTransition(conceptTree.getEdgeTarget(edge).iD(), conceptTree.getEdgeSource(edge).iD()));
		}
		return closedInheritances;
	}
	
	abstract protected Set<IContextualizedProduction> selectRelevantProductions(IClassificationProductions classificationProductions);

	abstract protected void filterForComplianceToAdditionalConstraints(Set<IConceptTransition> transitions);

	@Override
	public IRepresentationTransitionFunction apply(IClassification classification,
			IClassificationProductions classificationProductions) {
		// declare TF constructor parameters
		IConceptTransition initial;
		Set<IConceptTransition> applications = new HashSet<>();
		Set<IConceptTransition> closures;
		Set<IConceptTransition> inheritances = new HashSet<>();
		Set<IConceptTransition> spontaneous;
		// build
		initial = buildInitialTransition(classification);
		Set<IContextualizedProduction> relevantApplications = selectRelevantProductions(classificationProductions);
		Set<IConceptTransition> appAndUnclosedInheritances = buildApplicationsAndUnclosedInheritances(classification,
				relevantApplications);
		for (IConceptTransition transition : appAndUnclosedInheritances) {
			if (transition.type() == TransitionType.APPLICATION)
				applications.add(transition);
			else
				inheritances.add(transition);
		}
		closures = buildClosures(applications);
		inheritances.addAll(buildClosedInheritances(classification));
		spontaneous = buildSpontaneousTransitions(classification);
		// gather, filter, return
		Set<IConceptTransition> transitions = new HashSet<>();
		transitions.add(initial);
		transitions.addAll(applications);
		transitions.addAll(closures);
		transitions.addAll(inheritances);
		transitions.addAll(spontaneous);
		filterForComplianceToAdditionalConstraints(transitions);
		// return
		return new RepresentationTransitionFunction(transitions);
	}

}
