package com.tregouet.occam.alg.builders.pb_space.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
import com.tregouet.occam.alg.builders.pb_space.representations.lattice_productions.from_denotations.utils.MapVariablesToValues;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IComplementaryConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.tree_finder.data.InvertedTree;

public interface AlphaConverter extends Function<InvertedTree<IConcept, IIsA>, InvertedTree<IConcept, IIsA>> {
	
	public static InvertedTree<IConcept, IIsA> convert(InvertedTree<IConcept, IIsA> conceptTree){
		List<Integer> topoOrderIDs = new ArrayList<>();
		List<Set<Integer>> topoOrderedStrictLowerSets = new ArrayList<>();
		List<List<List<ISymbol>>> topoOrderedConstructSets = new ArrayList<>();
		List<IComplementaryConcept> topoOrderedNullOrComplementaryConcept = new ArrayList<>();
		//populate lists
		for (IConcept concept : conceptTree.getTopologicalOrder()) {
			Set<Integer> ancestorIDs = new HashSet<>();
			for (IConcept ancestor : conceptTree.getAncestors(concept))
				ancestorIDs.add(ancestor.iD());
			List<List<ISymbol>> constructs = new ArrayList<>();
			if (!concept.isComplementary()) {
				for (IDenotation denotation : concept.getDenotations())
					constructs.add(denotation.asList());
			}
			else {
				IComplementaryConcept compConcept = (IComplementaryConcept) concept;
				if (compConcept.)
			}
			topoOrderIDs.add(concept.iD());
			topoOrderedStrictLowerSets.add(ancestorIDs);
			topoOrderedConstructSets.add(constructs);
			if (concept.isComplementary())
				topoOrderedNullOrComplementaryConcept.add((IComplementaryConcept) concept);
			else topoOrderedNullOrComplementaryConcept.add(null);
		}
		//alpha-convert
		for (int i = topoOrderIDs.size() - 1 ; i >= 0 ; i--) {
			for (List<ISymbol> iConstruct : topoOrderedConstructSets.get(i)) {
				for (Integer j : topoOrderedStrictLowerSets.get(i)) {
					for (List<ISymbol> jConstruct : topoOrderedConstructSets.get(topoOrderIDs.indexOf(j))) {
						alphaConvertIfNeeded(iConstruct, jConstruct);
					}
				}
			}
		}
		//re-build graph
		//*rebuild concepts
		Map<Integer, IConcept> iD2UpdatedConcept = new HashMap<>();
		for (int i = 0 ; i < topoOrderIDs.size() ; i++) {
			
		}
		Set<IConcept> updatedConcepts = new HashSet<>();
		for (int i = 0 ; i < topoOrderIDs.size() ; i++) {
			if (topoOrderedNullOrComplementaryConcept.get(i) == null) {
				Set<IConstruct> denotatingConstructs = new HashSet<>();
				for (List<ISymbol> symbolList : topoOrderedConstructSets.get(i)) {
					denotatingConstructs.add(null)
				}
			}
		}
	}
	
	private static void alphaConvertIfNeeded(List<ISymbol> iConstruct, List<ISymbol> jConstruct) {
		Map<AVariable, List<ISymbol>> var2Value = MapVariablesToValues.of(jConstruct, iConstruct);
		if (var2Value != null) {
			for (AVariable var : var2Value.keySet()) {
				List<ISymbol> value = var2Value.get(var);
				if (value.size() == 1) {
					ISymbol valSymbol = value.get(0);
					if (valSymbol instanceof AVariable && !var.equals(valSymbol))
						jConstruct.set(jConstruct.indexOf(valSymbol), var);
				}
			}
		}
	}

}
