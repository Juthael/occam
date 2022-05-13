package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.transition_saliences.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.transition_saliences.TransitionSalienceSetter;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.Salience;
import com.tregouet.occam.data.representations.transitions.TransitionType;
import com.tregouet.occam.data.representations.transitions.productions.IProduction;

public class HiddenByDefaultThenFindSpecifics implements TransitionSalienceSetter {

	private List<Integer> inputStateIDs;
	private List<Set<Integer>> outputStateIDs;
	private List<Set<IConceptTransition>> applications;

	public HiddenByDefaultThenFindSpecifics() {
	}

	private static boolean everySubConceptGivesThisVariableADistinctValue(List<Set<IProduction>> values) {
		Set<Set<IProduction>> uniqueValues = new HashSet<>(values);
		return uniqueValues.size() == values.size();
	}

	private static boolean everySubConceptInstantiatesThisVariable(List<Set<IProduction>> values) {
		for (Set<IProduction> value : values) {
			if (value.isEmpty())
				return false;
		}
		return true;
	}

	@Override
	public void setSaliencesOf(Set<IConceptTransition> transitions, Set<Integer> particularIDs) {
		init();
		// set salience default value as HIDDEN, group applications by input concept,
		// find input/output relation
		for (IConceptTransition transition : transitions) {
			// default value, may be changed later
			transition.setSalience(Salience.HIDDEN);
			Integer inputStateID = transition.getInputConfiguration().getInputStateID();
			if (inputStateIDs.contains(inputStateID)) {
				int inputStateIdx = inputStateIDs.indexOf(inputStateID);
				outputStateIDs.get(inputStateIdx).add(transition.getOutputInternConfiguration().getOutputStateID());
				if (transition.type() == TransitionType.APPLICATION)
					applications.get(inputStateIdx).add(transition);
			} else {
				inputStateIDs.add(inputStateID);
				outputStateIDs.add(new HashSet<>(
						Arrays.asList(new Integer[] { transition.getOutputInternConfiguration().getOutputStateID() })));
				if (transition.type() == TransitionType.APPLICATION)
					applications.add(new HashSet<>(Arrays.asList(new IConceptTransition[] { transition })));
				else
					applications.add(new HashSet<>());
			}
		}
		// set common features
		for (Set<IConceptTransition> conceptApplications : applications) {
			for (IConceptTransition application : conceptApplications) {
				if (!particularIDs.contains(application.getOutputInternConfiguration().getOutputStateID())) {
					if (application.getInputConfiguration().getInputSymbol().isRedundant())
						application.setSalience(Salience.REDUNDANT);
					else
						application.setSalience(Salience.COMMON_FEATURE);
				}
			}
		}
		// set partition rules
		for (int i = 0; i < inputStateIDs.size(); i++) {
			setPartitionRulesSalience(i);
		}
	}

	private void init() {
		inputStateIDs = new ArrayList<>();
		outputStateIDs = new ArrayList<>();
		applications = new ArrayList<>();
	}

	private void setPartitionRuleSalienceOf(Map.Entry<AVariable, Set<IConceptTransition>> varToApplications,
			int inputConceptIdx) {
		List<Integer> outputIDs = new ArrayList<>(outputStateIDs.get(inputConceptIdx));
		List<Set<IProduction>> values = new ArrayList<>(outputIDs.size());
		for (int i = 0; i < outputIDs.size(); i++) {
			values.add(new HashSet<>());
		}
		for (IConceptTransition application : varToApplications.getValue()) {
			values.get(outputIDs.indexOf(application.getOutputInternConfiguration().getOutputStateID()))
					.add(application.getInputConfiguration().getInputSymbol().getUncontextualizedProduction());
		}
		if (everySubConceptInstantiatesThisVariable(values) && everySubConceptGivesThisVariableADistinctValue(values)) {
			for (IConceptTransition transitionRule : varToApplications.getValue())
				transitionRule.setSalience(Salience.TRANSITION_RULE);
		}
	}

	private void setPartitionRulesSalience(int inputConceptIdx) {
		Map<AVariable, Set<IConceptTransition>> varToApplications = new HashMap<>();
		for (IConceptTransition application : applications.get(inputConceptIdx)) {
			AVariable instantiatedVar = application.getInputConfiguration().getStackSymbol();
			if (varToApplications.containsKey(instantiatedVar))
				varToApplications.get(instantiatedVar).add(application);
			else
				varToApplications.put(instantiatedVar,
						new HashSet<>(Arrays.asList(new IConceptTransition[] { application })));
		}
		for (Map.Entry<AVariable, Set<IConceptTransition>> entry : varToApplications.entrySet())
			setPartitionRuleSalienceOf(entry, inputConceptIdx);
	}

}