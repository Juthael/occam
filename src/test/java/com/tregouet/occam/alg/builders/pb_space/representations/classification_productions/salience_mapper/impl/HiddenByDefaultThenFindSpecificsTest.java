package com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.salience_mapper.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.OverallStrategy;
import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.ProductionSetBuilder;
import com.tregouet.occam.alg.builders.pb_space.utils.MapConceptIDs2ExtentIDs;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.classifications.impl.Classification;
import com.tregouet.occam.data.problem_space.states.productions.IClassificationProductions;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.productions.Salience;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;

public class HiddenByDefaultThenFindSpecificsTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	public static int count = 0;
	@SuppressWarnings("unused")
	private static final String nL = System.lineSeparator();
	private List<IContextObject> context;
	private Set<Integer> particularIDs = new HashSet<>();
	private IConceptLattice conceptLattice;	
	private Set<IContextualizedProduction> productions;
	private Set<InvertedTree<IConcept, IIsA>> conceptTrees;
	private Map<IClassificationProductions, IClassification> classProd2Classification =	new HashMap<>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		for (IContextObject obj : context)
			particularIDs.add(obj.iD());		
		conceptLattice = BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		if (Occam.strategy == OverallStrategy.OVERALL_STRATEGY_1 || Occam.strategy == OverallStrategy.OVERALL_STRATEGY_2)
			productions = BuildersAbstractFactory.INSTANCE.getProdBuilderFromConceptLattice().apply(conceptLattice);
		/*
		VisualizersAbstractFactory.INSTANCE.getConceptGraphViz()
			.apply(conceptLattice.getOntologicalUpperSemilattice(), "FirstBuildTransitionFunctionTest_lattice");
		*/
		conceptTrees = growTrees();
		ProductionSetBuilder bldr;
		for (InvertedTree<IConcept, IIsA> tree : conceptTrees) {
			Map<Integer, List<Integer>> conceptID2ExtentIDs = MapConceptIDs2ExtentIDs.in(tree);
			Map<Integer, Integer> speciesID2GenusID = mapSpeciesID2GenusID(tree);
			IClassification classification = new Classification(tree, conceptID2ExtentIDs, speciesID2GenusID, particularIDs);
			bldr = BuildersAbstractFactory.INSTANCE.getClassificationProductionSetBuilder();
			IClassificationProductions classProds = bldr.setUp(productions).apply(classification);
			classProd2Classification.put(classProds, classification);
		}		
	}

	@Test
	public void ifTransitionIsHiddenProductionThenSpeciesIsParticular() {
		boolean asExpected = true;
		int nbOfChecks = 0;
		for (IClassificationProductions classProd : classProd2Classification.keySet()) {
			Set<IContextualizedProduction> salientProds = classProd.getSalientProductions();
			Set<IContextualizedProduction> allProds = classProd.getProductions();
			Set<IContextualizedProduction> hiddenProds = new HashSet<>(Sets.difference(allProds, salientProds));
			for (IContextualizedProduction production : hiddenProds) {
				if (!production.isEpsilon() && !production.isBlank()) {
					Integer speciesID = production.getSubordinateID();
					if (!particularIDs.contains(speciesID))
						asExpected = false;
					nbOfChecks ++;
				}
			}
		}
		assertTrue(nbOfChecks > 0 && asExpected);
	}
	
	@Test
	public void ifTransitionIsRuleThenRulesTowardsConcurrentOutputsCanBeFound() {
		boolean asExpected = true;
		int nbOfChecks = 0;
		for (IClassificationProductions classProds : classProd2Classification.keySet()) {
			IClassification classification = classProd2Classification.get(classProds);
			for (IContextualizedProduction prod : classProds.getSalientProductions()) {
				if (classProds.salienceOf(prod) == Salience.TRANSITION_RULE) {
					nbOfChecks++;
					int genusID = classification.getGenusID(prod.getSubordinateID());
					IConcept genus = classification.getConceptWithSpecifiedID(genusID);
					List<Integer> speciesID = new ArrayList<>();
					for (IIsA edge : classification.asGraph().edgeSet()) {
						if (classification.asGraph().getEdgeTarget(edge).equals(genus)) {
							speciesID.add(classification.asGraph().getEdgeSource(edge).iD());
						}
					}
					List<Set<IContextualizedProduction>> rules = 
							findProductionsWithSpecifiedInputStateAndStackSymbol(classProds, speciesID, prod.getVariable());
					if (rules.size() != speciesID.size())
						asExpected = false;
				}
			}
		}
		assertTrue(nbOfChecks > 0 && asExpected);
	}	
	
	@Test
	public void ifTransitionIsCommonFeatureThenOutputStateIsNotParticular() {
		boolean asExpected = true;
		int nbOfChecks = 0;
		for (IClassificationProductions classProds : classProd2Classification.keySet()) {
			for (IContextualizedProduction prod : classProds.getProductions()) {
				if (classProds.salienceOf(prod) == Salience.COMMON_FEATURE) {
					nbOfChecks++;
					if (particularIDs.contains(prod.getSubordinateID()))
						asExpected = false;
				}
			}
		}
		assertTrue(nbOfChecks > 0 && asExpected);
	}		
	
	private Set<InvertedTree<IConcept, IIsA>> growTrees() {
		Set<InvertedTree<IConcept, IIsA>> expandedTrees = new HashSet<>();
		Set<InvertedTree<IConcept, IIsA>> expandedTreesFromLastIteration;
		expandedTreesFromLastIteration = BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, null);
		do {
			expandedTrees.addAll(expandedTreesFromLastIteration);
			Set<InvertedTree<IConcept, IIsA>> expandable = new HashSet<>(expandedTreesFromLastIteration);
			expandedTreesFromLastIteration.clear();
			for (InvertedTree<IConcept, IIsA> tree : expandable) {
				expandedTreesFromLastIteration.addAll(
						BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, tree)); 
			}
		}
		while (!expandedTreesFromLastIteration.isEmpty());
		return expandedTrees;
	}	
	
	private static Map<Integer, Integer> mapSpeciesID2GenusID(InvertedTree<IConcept, IIsA> conceptTree) {
		Map<Integer, Integer> speciesID2GenusID = new HashMap<>();
		for (IIsA edge : conceptTree.edgeSet())
			speciesID2GenusID.put(conceptTree.getEdgeSource(edge).iD(), conceptTree.getEdgeTarget(edge).iD());
		return speciesID2GenusID;
	}
	
	private List<Set<IContextualizedProduction>> findProductionsWithSpecifiedInputStateAndStackSymbol(
			IClassificationProductions classProds, List<Integer> speciesIDs, AVariable variable) {
		List<Set<IContextualizedProduction>> assumedRules = new ArrayList<>();
		for (int i = 0 ; i < speciesIDs.size() ; i++)
			assumedRules.add(new HashSet<>());
		for (IContextualizedProduction production : classProds.getSalientProductions()) {
			if (production.getVariable().equals(variable)) {
				int speciesIdx = speciesIDs.indexOf(production.getSubordinateID());
				if (speciesIdx != -1) {
					assumedRules.get(speciesIdx).add(production);
				}
			}
		}
		return assumedRules;
	}	

}
