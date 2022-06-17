package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.logical_structures.lambda_terms.impl.Bindings;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.impl.Everything;
import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.problem_space.states.productions.IBasicProduction;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;
import com.tregouet.occam.data.problem_space.states.transitions.impl.ClosureTransition;
import com.tregouet.occam.data.problem_space.states.transitions.impl.ConceptProductiveTransition;
import com.tregouet.occam.data.problem_space.states.transitions.impl.ConceptTransitionIC;
import com.tregouet.occam.data.problem_space.states.transitions.impl.ConceptTransitionOIC;
import com.tregouet.occam.data.problem_space.states.transitions.impl.InheritanceTransition;
import com.tregouet.occam.data.problem_space.states.transitions.impl.InitialTransition;
import com.tregouet.occam.data.problem_space.states.transitions.impl.RepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.states.transitions.impl.SpontaneousTransition;
import com.tregouet.occam.data.problem_space.states.transitions.impl.stack_default.EpsilonBinding;
import com.tregouet.tree_finder.data.InvertedTree;

public abstract class AbstractTransFuncBuilder implements RepresentationTransFuncBuilder {

	public AbstractTransFuncBuilder() {
	}

	@Override
	public IRepresentationTransitionFunction apply(IClassification classification,
			IDescription description) {
		//populate
		Set<IComputation> computations = new HashSet<>();
		for (ADifferentiae diff : description.asGraph().edgeSet()) {
			for (IProperty prop : diff.getProperties())
				computations.addAll(prop.getComputations());
		}
		// declare TF constructor parameters
		IConceptTransition initial;
		Set<IConceptTransition> productiveTrans = new HashSet<>();
		Set<IConceptTransition> closures;
		Set<IConceptTransition> inheritances = new HashSet<>();
		Set<IConceptTransition> spontaneous;
		// build
		initial = buildInitialTransition(classification);
		Set<IComputation> relevantComputations = selectRelevantComputations(computations);
		Set<IConceptTransition> prodTransAndUnclosedInheritances = buildProductiveTransAndUnclosedInheritances(classification,
				relevantComputations);
		for (IConceptTransition transition : prodTransAndUnclosedInheritances) {
			if (transition.type() == TransitionType.PRODUCTIVE_TRANS)
				productiveTrans.add(transition);
			else
				inheritances.add(transition);
		}
		closures = buildClosures(productiveTrans);
		inheritances.addAll(buildClosedInheritances(classification));
		spontaneous = buildSpontaneousTransitions(classification);
		// gather, filter, return
		Set<IConceptTransition> transitions = new HashSet<>();
		transitions.add(initial);
		transitions.addAll(productiveTrans);
		transitions.addAll(closures);
		transitions.addAll(inheritances);
		transitions.addAll(spontaneous);
		filterForComplianceWithAdditionalConstraints(transitions);
		// return
		return new RepresentationTransitionFunction(transitions);
	}

	abstract protected void filterForComplianceWithAdditionalConstraints(Set<IConceptTransition> transitions);

	abstract protected Set<IComputation> selectRelevantComputations(Set<IComputation> computations);

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

	private static Set<IConceptTransition> buildClosures(Set<IConceptTransition> transitions) {
		Set<IConceptTransition> closures = new HashSet<>();
		for (IConceptTransition transition : transitions) {
			if (outputIsFunction(transition)) {
				closures.add(
						new ClosureTransition(
								transition.getInputConfiguration(),
								transition.getOutputInternConfiguration().getOutputStateID()));
			}
		}
		return closures;
	}

	private static IConceptTransition buildInitialTransition(IClassification classification) {
		Everything everything = (Everything) classification.asGraph().getRoot();
		return new InitialTransition(everything);
	}

	private static Set<IConceptTransition> buildProductiveTransAndUnclosedInheritances(IClassification classification,
			Set<IComputation> computations) {
		Set<IConceptTransition> transitions = new HashSet<>();
		for (IComputation computation : computations) {
			if (!computation.isEpsilon()) {
				int speciesID = computation.getOutput().getConceptID();
				int sourceID = computation.getInput().getConceptID();
				int genusID = classification.getGenusID(speciesID);
				if (sourceID != genusID) {
					transitions.addAll(
							maintainStackSymbolOnTop(computation.getOperator().getBindings(), sourceID, genusID, classification));
				}
				if (computation.returnsInput()) {
					//blank : substitution of a variables by themselves. Instead, ε-transition with same bindings remaining on top
					transitions.add(new InheritanceTransition(genusID, speciesID, computation.getOperator().getBindings()));
				}
				else {
					IAbstractionApplication inputSymbol = computation.getOperator();
					IBindings stackSymbol = inputSymbol.getBindings();
					IConceptTransitionIC inputConfig =
							new ConceptTransitionIC(genusID, inputSymbol, stackSymbol);
					IConceptTransitionOIC outputConfig;
					List<AVariable> newBoundVariables = computation.getOutput().getVariables();
					if (newBoundVariables.isEmpty())
						outputConfig = new ConceptTransitionOIC(speciesID,
								new ArrayList<>(Arrays.asList(new IBindings[] {EpsilonBinding.INSTANCE})));
					else outputConfig = new ConceptTransitionOIC(speciesID,
								new ArrayList<>(Arrays.asList(new IBindings[] {new Bindings(newBoundVariables)})));
					transitions.add(new ConceptProductiveTransition(inputConfig, outputConfig));
				}
			}
		}
		return transitions;
	}
	
	private static Set<IConceptTransition> maintainStackSymbolOnTop(IBindings stackSymbol, int sourceID, int genusID, 
			IClassification classification){
		Set<IConceptTransition> bindingsMaintainers = new HashSet<>();
		int subordinate = genusID;
		int superordinate = classification.getGenusID(subordinate);
		while (subordinate != sourceID) {
			bindingsMaintainers.add(new InheritanceTransition(superordinate, subordinate, stackSymbol));
			subordinate = superordinate;
			if (subordinate != classification.asGraph().getRoot().iD())
				superordinate = classification.getGenusID(subordinate);
		}
		return bindingsMaintainers;
	}

	private static boolean outputIsFunction(IConceptTransition transition) {
		for (IBasicProduction prod : transition.getInputConfiguration().getInputSymbol().getArguments()) {
			if (prod.getValue().isAbstract())
				return true;
		}
		return false;
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

}
