package com.tregouet.occam.data.concepts.utils;

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

import com.tregouet.occam.alg.calculators.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.calculators.ScoringStrategy;
import com.tregouet.occam.alg.conceptual_structure_gen.IClassificationSupplier;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.concepts.utils.TreeOfConceptsToStringConvertor;
import com.tregouet.occam.data.languages.generic.IContextObject;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.utils.Visualizer;
import com.tregouet.tree_finder.data.Tree;

@SuppressWarnings("unused")
public class TreeOfConceptsToStringConvertorTest {

	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;	
	private IConcepts concepts;
	private IClassificationSupplier classificationSupplier;
	private Map<IConcept, String> objCatToName = new HashMap<>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(SHAPES2);
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy.CONCEPTUAL_COHERENCE);
	}

	@Before
	public void setUp() throws Exception {
		concepts = new Concepts(shapes2Obj);
		char name = 'A';
		for (IConcept objectCategory : concepts.getSingletonConcept()) {
			objCatToName.put(objectCategory, new String(Character.toString(name++)));
		}
		classificationSupplier = concepts.getClassificationSupplier();
	}

	@Test
	public void whenCatTreeDescriptionReturnedThenContainsEveryLeaf() throws IOException {
		boolean containsEveryLeaf = true;
		int countCheck = 0;
		Set<String> leaves = new HashSet<>(objCatToName.values());
		/*
		System.out.println(getObjDefinitions());
		Categories cats = (Categories) categories;
		Visualizer.visualizeCategoryGraph(cats.getCategoryLattice(), "CatTreeToStringConvertorTest");
		int treeIdx = 0;
		*/
		while (classificationSupplier.hasNext()) {
			Tree<IConcept, IIsA> currTree = classificationSupplier.next().getClassificationTree();
			/*
			Visualizer.visualizeCategoryGraph(currTree, "2110151257_tree" + Integer.toString(treeIdx++));
			*/
			String currTreeDesc = new TreeOfConceptsToStringConvertor(currTree, objCatToName).toString();
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
		for (IConcept objectCat : objCatToName.keySet()) {
			sB.append("**Object " + objCatToName.get(objectCat) + " :" + newLine);
			sB.append(objectCat.toString());
			sB.append(newLine + newLine);
		}
		return sB.toString();
	}

}
