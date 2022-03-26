package com.tregouet.occam.data.denotations.utils;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
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

import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilder;
import com.tregouet.occam.alg.scoring_dep.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring_dep.ScoringStrategy_dep;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.concepts.impl.ConceptLattice;
import com.tregouet.occam.data.representations.concepts.utils.TreeOfConceptsToStringConvertor;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.Tree;

@SuppressWarnings("unused")
public class TreeOfDenotationsToStringConvertorTest {

	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private IConceptLattice conceptLattice;
	private ConceptTreeBuilder conceptTreeBuilder;
	private Map<IConcept, String> objDenotationSetToName = new HashMap<>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(SHAPES2);
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy_dep.SCORING_STRATEGY_1);
	}

	@Before
	public void setUp() throws Exception {
		conceptLattice = new ConceptLattice(shapes2Obj);
		char name = 'A';
		for (IConcept objectCategory : conceptLattice.getObjectConcepts()) {
			objDenotationSetToName.put(objectCategory, new String(Character.toString(name++)));
		}
		conceptTreeBuilder = conceptLattice.getConceptTreeSupplier();
	}

	@Test
	public void whenDenotSetTreeDescriptionReturnedThenContainsEveryLeaf() throws IOException {
		boolean containsEveryLeaf = true;
		int countCheck = 0;
		Set<String> leaves = new HashSet<>(objDenotationSetToName.values());
		/*
		System.out.println(getObjDefinitions());
		Categories cats = (Categories) categories;
		Visualizer.visualizeCategoryGraph(cats.getCategoryLattice(), "CatTreeToStringConvertorTest");
		int treeIdx = 0;
		*/
		while (conceptTreeBuilder.hasNext()) {
			Tree<IConcept, IIsA> currTree = conceptTreeBuilder.next();
			/*
			Visualizer.visualizeCategoryGraph(currTree, "2110151257_tree" + Integer.toString(treeIdx++));
			*/
			String currTreeDesc = new TreeOfConceptsToStringConvertor(currTree, objDenotationSetToName).toString();
			/*
			System.out.println(currTreeDesc);
			*/
			for (String leafString : leaves) {
				if (!currTreeDesc.contains(leafString))
					containsEveryLeaf = false;
			}
			countCheck++;
		}
		assertTrue(containsEveryLeaf && countCheck > 0);
	}
	
	private String getObjDefinitions() {
		StringBuilder sB = new StringBuilder();
		String newLine = System.lineSeparator();
		sB.append("*** DEFINITION OF OBJECTS ***" + newLine + newLine);
		for (IConcept objectCat : objDenotationSetToName.keySet()) {
			sB.append("**Object " + objDenotationSetToName.get(objectCat) + " :" + newLine);
			sB.append(objectCat.toString());
			sB.append(newLine + newLine);
		}
		return sB.toString();
	}

}
