package com.tregouet.occam.alg.builders.pb_space.representations.production_sets.salience_setter.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.ProductionSetBuilder;
import com.tregouet.occam.alg.builders.pb_space.utils.MapConceptIDs2ExtentIDs;
import com.tregouet.occam.data.representations.classifications.IClassification;
import com.tregouet.occam.data.representations.classifications.concepts.ConceptType;
import com.tregouet.occam.data.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.data.representations.classifications.concepts.IIsA;
import com.tregouet.occam.data.representations.classifications.impl.Classification;
import com.tregouet.occam.data.representations.productions.IContextualizedProduction;
import com.tregouet.occam.data.representations.productions.Salience;
import com.tregouet.occam.data.structures.languages.alphabets.AVariable;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;

public class HiddenByDefaultThenFindSpecificsTest {

	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	public static int count = 0;
	@SuppressWarnings("unused")
	private static final String nL = System.lineSeparator();
	private List<IContextObject> context;
	private IConceptLattice conceptLattice;
	private Set<InvertedTree<IConcept, IIsA>> conceptTrees;
	private Map<Set<IContextualizedProduction>, IClassification> classProd2Classification =	new HashMap<>();

	@Test
	public void ifTransitionIsCommonFeatureThenOutputStateIsNotParticular() {
		boolean asExpected = true;
		int nbOfChecks = 0;
		for (Set<IContextualizedProduction> classProds : classProd2Classification.keySet()) {
			for (IContextualizedProduction prod : classProds) {
				if (prod.getSalience() == Salience.COMMON_FEATURE) {
					nbOfChecks++;
					if (conceptLattice.getParticularID2Particular().containsKey(prod.getSubordinateID()))
						asExpected = false;
				}
			}
		}
		assertTrue(nbOfChecks > 0 && asExpected);
	}

	@Test
	public void ifTransitionIsHiddenProductionThenSpeciesIsParticular() {
		boolean asExpected = true;
		int nbOfChecks = 0;
		for (Set<IContextualizedProduction> classProd : classProd2Classification.keySet()) {
			Set<IContextualizedProduction> hiddenProds = classProd.stream()
					.filter(p -> p.getSalience() == Salience.HIDDEN)
					.collect(Collectors.toSet());
			for (IContextualizedProduction production : hiddenProds) {
				Integer speciesID = production.getSubordinateID();
				if (!conceptLattice.getParticularID2Particular().containsKey(speciesID))
					asExpected = false;
				nbOfChecks ++;
			}
		}
		assertTrue(nbOfChecks > 0 && asExpected);
	}

	@Test
	public void ifTransitionIsRuleThenRulesTowardsConcurrentOutputsCanBeFound() {
		boolean asExpected = true;
		int nbOfChecks = 0;
		for (Set<IContextualizedProduction> classProds : classProd2Classification.keySet()) {
			IClassification classification = classProd2Classification.get(classProds);
			for (IContextualizedProduction prod : classProds) {
				if (prod.getSalience() == Salience.TRANSITION_RULE) {
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
							findRulesWithSpecifiedInputStateAndStackSymbol(classProds, speciesID, prod.getVariable());
					if (rules.size() != speciesID.size())
						asExpected = false;
				}
			}
		}
		assertTrue(nbOfChecks > 0 && asExpected);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		conceptLattice = BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		/*
		VisualizersAbstractFactory.INSTANCE.getConceptGraphViz()
			.apply(conceptLattice.getOntologicalUpperSemilattice(), "FirstBuildTransitionFunctionTest_lattice");
		*/
		conceptTrees = growTrees();
		ProductionSetBuilder bldr;
		for (InvertedTree<IConcept, IIsA> tree : conceptTrees) {
			Map<Integer, List<Integer>> conceptID2ExtentIDs = MapConceptIDs2ExtentIDs.in(tree);
			Map<Integer, Integer> speciesID2GenusID = mapSpeciesID2GenusID(tree);
			boolean fullyDeveloped = isFullyDeveloped(tree);
			IClassification classification =
					new Classification(tree, conceptID2ExtentIDs, speciesID2GenusID,
							conceptLattice.getParticularID2Particular(), fullyDeveloped);
			bldr = BuildersAbstractFactory.INSTANCE.getProductionSetBuilder();
			Set<IContextualizedProduction> classProds = bldr.apply(classification);
			classProd2Classification.put(classProds, classification);
		}
	}

	private List<Set<IContextualizedProduction>> findRulesWithSpecifiedInputStateAndStackSymbol(
			Set<IContextualizedProduction> classProds, List<Integer> speciesIDs, AVariable variable) {
		List<Set<IContextualizedProduction>> assumedRules = new ArrayList<>();
		for (int i = 0 ; i < speciesIDs.size() ; i++)
			assumedRules.add(new HashSet<>());
		for (IContextualizedProduction production : classProds) {
			if (production.getSalience() == Salience.TRANSITION_RULE && production.getVariable().equals(variable)) {
				int speciesIdx = speciesIDs.indexOf(production.getSubordinateID());
				if (speciesIdx != -1) {
					assumedRules.get(speciesIdx).add(production);
				}
			}
		}
		return assumedRules;
	}

	private Set<InvertedTree<IConcept, IIsA>> growTrees() {
		Set<InvertedTree<IConcept, IIsA>> expandedTrees = new HashSet<>();
		Set<InvertedTree<IConcept, IIsA>> expandedTreesFromLastIteration;
		expandedTreesFromLastIteration =
				new HashSet<>(BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, null).keySet());
		do {
			expandedTrees.addAll(expandedTreesFromLastIteration);
			Set<InvertedTree<IConcept, IIsA>> expandable = new HashSet<>(expandedTreesFromLastIteration);
			expandedTreesFromLastIteration.clear();
			for (InvertedTree<IConcept, IIsA> tree : expandable) {
				expandedTreesFromLastIteration.addAll(new HashSet<>(
						BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, tree).keySet()));
			}
		}
		while (!expandedTreesFromLastIteration.isEmpty());
		return expandedTrees;
	}

	@BeforeClass
	public static void setUpBeforeClass() {
		Occam.initialize();
		OverallAbstractFactory.INSTANCE.apply(Occam.STRATEGY);
	}

	private static boolean isFullyDeveloped(InvertedTree<IConcept, IIsA> conceptTree) {
		for (IConcept concept : conceptTree.getLeaves()) {
			if (concept.type() != ConceptType.PARTICULAR)
				return false;
		}
		return true;
	}

	private static Map<Integer, Integer> mapSpeciesID2GenusID(InvertedTree<IConcept, IIsA> conceptTree) {
		Map<Integer, Integer> speciesID2GenusID = new HashMap<>();
		for (IIsA edge : conceptTree.edgeSet())
			speciesID2GenusID.put(conceptTree.getEdgeSource(edge).iD(), conceptTree.getEdgeTarget(edge).iD());
		return speciesID2GenusID;
	}

}
