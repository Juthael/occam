package com.tregouet.occam.alg.builders.pb_space.representations.partitions.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.impl.IfLeafIsUniversalThenSort;
import com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.ClassificationProductionSetBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.impl.BuildGraphFirst;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.builders.pb_space.utils.MapConceptIDs2ExtentIDs;
import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.classifications.impl.Classification;
import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.states.productions.IClassificationProductions;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;

@SuppressWarnings("unused")
public class BuildGraphFirstTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private static final String NL = System.lineSeparator();
	private List<IContextObject> context;
	private Set<Integer> extentIDs = new HashSet<>();
	private IConceptLattice conceptLattice;	
	private Set<IContextualizedProduction> productions;
	private Set<InvertedTree<IConcept, IIsA>> trees;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		for (IContextObject obj : context)
			extentIDs.add(obj.iD());		
		conceptLattice = BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		productions = BuildersAbstractFactory.INSTANCE.getProdBuilderFromConceptLattice().apply(conceptLattice);
		growTrees();
	}

	@Test
	public void whenPartitionsRequestedThenReturned() {
		boolean asExpected = true;
		int nbOfChecks = 0;
		ClassificationProductionSetBuilder classProdBldr;
		for (InvertedTree<IConcept, IIsA> tree : trees) {
			Map<Integer, List<Integer>> conceptID2ExtentIDs = MapConceptIDs2ExtentIDs.in(tree);
			Map<Integer, Integer> speciesID2GenusID = mapSpeciesID2GenusID(tree);
			IClassification classification = new Classification(tree, conceptID2ExtentIDs, speciesID2GenusID, extentIDs);		
			classProdBldr = BuildersAbstractFactory.INSTANCE.getClassificationProductionSetBuilder();
			IClassificationProductions classProds = classProdBldr.apply(classification, productions);
			IDescription description = BuildersAbstractFactory.INSTANCE.getDescriptionBuilder().apply(classification, classProds);
			BuildGraphFirst partitionBuilder = new BuildGraphFirst();
			Set<IPartition> partitions = partitionBuilder.apply(description, classification);
			if (partitions == null || partitions.isEmpty())
				asExpected = false;
			/*
			VisualizersAbstractFactory.INSTANCE.getDescriptionViz().apply(
							description, "BuildGraphFirst_D" + Integer.toString(nbOfChecks));
			System.out.println("DESCRIPTION n." + Integer.toString(nbOfChecks) + " : " 
					+ GeneratorsAbstractFactory.INSTANCE.getStringPatternBuilder().apply(description.asGraph()));
			System.out.println("Partitions : ");
			System.out.println("**********" + NL);
			*/
			nbOfChecks++;
		}
		/*
		System.out.print(nbOfChecks + " checks proceeded");
		*/
		assertTrue(nbOfChecks > 0 && asExpected);
	}
	
	private void growTrees() {
		trees = BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, null);
		boolean newTreesBuilt = true;
		Set<InvertedTree<IConcept, IIsA>> previouslyFoundTrees = new HashSet<>();
		previouslyFoundTrees.addAll(trees);
		while (newTreesBuilt) {
			Set<InvertedTree<IConcept, IIsA>> foundTrees = new HashSet<>();
			for (InvertedTree<IConcept, IIsA> tree : previouslyFoundTrees)
				foundTrees.addAll(BuildersAbstractFactory.INSTANCE.getConceptTreeGrower().apply(conceptLattice, tree));
			newTreesBuilt = !(foundTrees.isEmpty());
			trees.addAll(foundTrees);
			previouslyFoundTrees = foundTrees;
		}
	}
	
	private static Map<Integer, Integer> mapSpeciesID2GenusID(InvertedTree<IConcept, IIsA> conceptTree) {
		Map<Integer, Integer> speciesID2GenusID = new HashMap<>();
		for (IIsA edge : conceptTree.edgeSet())
			speciesID2GenusID.put(conceptTree.getEdgeSource(edge).iD(), conceptTree.getEdgeTarget(edge).iD());
		return speciesID2GenusID;
	}	

}
