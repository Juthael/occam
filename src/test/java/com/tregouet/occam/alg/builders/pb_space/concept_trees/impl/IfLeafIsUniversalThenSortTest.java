package com.tregouet.occam.alg.builders.pb_space.concept_trees.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.impl.IfLeafIsUniversalThenSort;
import com.tregouet.occam.data.representations.concepts.ConceptType;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;

public class IfLeafIsUniversalThenSortTest {

	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	public static int count = 0;
	@SuppressWarnings("unused")
	private static final String nL = System.lineSeparator();
	private List<IContextObject> context;
	private IConceptLattice conceptLattice;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		conceptLattice = GeneratorsAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		/*
		VisualizersAbstractFactory.INSTANCE.getConceptGraphViz()
			.apply(conceptLattice.getOntologicalUpperSemilattice(), "IfLeafIsUniversal_lattice");
		*/
	}
	
	@Test
	public void whenTreeExpansionRequestedThenTheSameTreeCanBeBuiltManyTimes() {
		boolean asExpected = false;
		Set<InvertedTree<IConcept, IIsA>> expandedTrees = new HashSet<>();
		Set<InvertedTree<IConcept, IIsA>> expandedTreesFromLastIteration;
		expandedTreesFromLastIteration = IfLeafIsUniversalThenSort.INSTANCE.apply(conceptLattice, null);
		do {
			expandedTrees.addAll(expandedTreesFromLastIteration);
			Set<InvertedTree<IConcept, IIsA>> expandable = new HashSet<>(expandedTreesFromLastIteration);
			expandedTreesFromLastIteration.clear();
			for (InvertedTree<IConcept, IIsA> tree : expandable) {
				for (InvertedTree<IConcept, IIsA> returned : IfLeafIsUniversalThenSort.INSTANCE.apply(conceptLattice, tree)) {
					boolean newTree = expandedTreesFromLastIteration.add(returned);
					if (!newTree)
						asExpected = true;
				} 
			}
		}
		while (!expandedTreesFromLastIteration.isEmpty());
		/*
		VisualizersAbstractFactory.INSTANCE.getConceptGraphViz()
			.apply(conceptLattice.getOntologicalUpperSemilattice(), "IfLeafIsUniversal_lattice");
		int count = 0;
		for (InvertedTree<IConcept, IIsA> expTree : expandedTrees) {
			VisualizersAbstractFactory.INSTANCE.getConceptGraphViz()
				.apply(expTree, "IfLeafIsUniversal_tree" + Integer.toString(count++));
		}
		*/
		assertTrue(asExpected);
	}	
	
	@Test
	public void whenTreeExpansionRequestedThenProceeded() {
		Set<InvertedTree<IConcept, IIsA>> expandedTrees = new HashSet<>();
		Set<InvertedTree<IConcept, IIsA>> expandedTreesFromLastIteration;
		expandedTreesFromLastIteration = IfLeafIsUniversalThenSort.INSTANCE.apply(conceptLattice, null);
		do {
			expandedTrees.addAll(expandedTreesFromLastIteration);
			Set<InvertedTree<IConcept, IIsA>> expandable = new HashSet<>(expandedTreesFromLastIteration);
			expandedTreesFromLastIteration.clear();
			for (InvertedTree<IConcept, IIsA> tree : expandable) {
				expandedTreesFromLastIteration.addAll(IfLeafIsUniversalThenSort.INSTANCE.apply(conceptLattice, tree)); 
			}
		}
		while (!expandedTreesFromLastIteration.isEmpty());
		/*
		VisualizersAbstractFactory.INSTANCE.getConceptGraphViz()
			.apply(conceptLattice.getOntologicalUpperSemilattice(), "IfLeafIsUniversal_lattice");
		int count = 0;
		for (InvertedTree<IConcept, IIsA> expTree : expandedTrees) {
			VisualizersAbstractFactory.INSTANCE.getConceptGraphViz()
				.apply(expTree, "IfLeafIsUniversal_tree" + Integer.toString(count++));
		}
		*/
		assertTrue(!expandedTrees.isEmpty());
	}
	
	@Test
	public void whenTreeHasALeafWhichIsNotParticularThenCanBeExpandedOtherwiseCannot() {
		boolean asExpected = true;
		Set<InvertedTree<IConcept, IIsA>> expandedTreesFromLastIteration;
		expandedTreesFromLastIteration = IfLeafIsUniversalThenSort.INSTANCE.apply(conceptLattice, null);
		int nbOfChecks = 0;
		do {
			Set<InvertedTree<IConcept, IIsA>> expandable = new HashSet<>(expandedTreesFromLastIteration);
			expandedTreesFromLastIteration.clear();
			for (InvertedTree<IConcept, IIsA> iTree : expandable) {
				boolean expectedExpansion = false;
				for (IConcept leaf : iTree.getLeaves()) {
					if (leaf.type() != ConceptType.PARTICULAR)
						expectedExpansion = true;
				}
				Set<InvertedTree<IConcept, IIsA>> iExpandedTrees = 
						IfLeafIsUniversalThenSort.INSTANCE.apply(conceptLattice, iTree);
				if (expectedExpansion == iExpandedTrees.isEmpty())
					asExpected = false;
				expandedTreesFromLastIteration.addAll(iExpandedTrees);
				nbOfChecks++;
			}
		}
		while (!expandedTreesFromLastIteration.isEmpty());
		assertTrue(asExpected && nbOfChecks > 0);
	}

}
