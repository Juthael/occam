package com.tregouet.occam.alg.builders.representations.string_pattern.impl;

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
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.InvertedTree;

public class RecursiveFramingTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	private List<IContextObject> context;
	private IConceptLattice conceptLattice;	
	private Set<IContextualizedProduction> productions;
	private Set<InvertedTree<IConcept, IIsA>> trees;
	private Set<IRepresentationTransitionFunction> transFunctions = new HashSet<>();
	private Set<IDescription> descriptions = new HashSet<>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		conceptLattice = GeneratorsAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
		productions = GeneratorsAbstractFactory.INSTANCE.getProdBuilderFromConceptLattice().apply(conceptLattice);
		trees = GeneratorsAbstractFactory.INSTANCE.getConceptTreeBuilder().apply(conceptLattice);
		RepresentationTransFuncBuilder transFuncBldr;
		for (InvertedTree<IConcept, IIsA> tree : trees) {
			transFuncBldr = GeneratorsAbstractFactory.INSTANCE.getRepresentationTransFuncBuilder();
			transFunctions.add(transFuncBldr.apply(tree, productions));
		}
		for (IRepresentationTransitionFunction transFunc : transFunctions) {
			descriptions.add(GeneratorsAbstractFactory.INSTANCE.getDescriptionBuilder().apply(transFunc));
		}
	}

	@Test
	public void whenStringPatternRequestedThenEachDescriptionOfContextGeneratesDistinctPattern() {
		boolean asExpected = true;
		RecursiveFraming patternBldr;
		Set<String> patterns = new HashSet<>();
		int idx = 0;
		for (IDescription description : descriptions) {
			patternBldr = new RecursiveFraming();
			String pattern = patternBldr.apply(description.asGraph());
			//HERE
			System.out.println("Pattern n." + Integer.toString(idx) + " : " + pattern);
			//HERE
			if (!patterns.add(pattern))
				asExpected = false;
			idx++;
		}
		assertTrue(asExpected);
	}

}
