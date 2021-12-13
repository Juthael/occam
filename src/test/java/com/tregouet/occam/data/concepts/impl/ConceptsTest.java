package com.tregouet.occam.data.concepts.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.alg.conceptual_structure_gen.IClassificationSupplier;
import com.tregouet.occam.alg.score_calc.CalculatorFactory;
import com.tregouet.occam.alg.score_calc.OverallScoringStrategy;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.IContextObject;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.occam.io.output.utils.Visualizer;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.StructureInspector;

@SuppressWarnings("unused")
public class ConceptsTest {

	private static Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;
	private static IConcepts concepts;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(shapes2);
		CalculatorFactory.INSTANCE.setUpStrategy(OverallScoringStrategy.CONCEPTUAL_COHERENCE);
	}

	@Before
	public void setUp() throws Exception {
		concepts = new Concepts(shapes2Obj);
	}
	
	@Test
	public void whenCategoriesReturnedThenContains1Absurdity1Truism1Commitment() {
		int nbOfTruism = 0;
		int nbOfCommitments = 0;
		for (IConcept cat : concepts.getTopologicalSorting()) {
			if (cat.type() == IConcept.TRUISM)
				nbOfTruism++;
			else if (cat.type() == IConcept.ONTOLOGICAL_COMMITMENT)
				nbOfCommitments++;
		}
		assertTrue(nbOfTruism == 1 && nbOfCommitments == 1);
	}	

	@Test
	public void whenCategoriesReturnedThenEachHasADistinctIntent() {
		Set<Set<IIntentAttribute>> intents = new HashSet<>();
		for (IConcept cat : concepts.getTopologicalSorting())
			intents.add(cat.getIntent());
		assertTrue(intents.size() == concepts.getTopologicalSorting().size());
	}
	

	@Test
	public void whenCategoriesReturnedThenTruismAndCommitmentHaveSameExtent() {
		Set<Set<IContextObject>> extents = new HashSet<>();
		extents.add(concepts.getTruism().getExtent());
		extents.add(concepts.getOntologicalCommitment().getExtent());
		assertTrue(extents.size() == 1);
	}	
	
	@Test
	public void whenCategoryRankRequestedThenAsExpected() {
		Set<IContextObject> extent0 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(0)}));
		Set<IContextObject> extent1 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(1)}));
		Set<IContextObject> extent2 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(2)}));
		Set<IContextObject> extent3 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(3)}));
		Set<IContextObject> extent02 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(0), shapes2Obj.get(2)}));
		Set<IContextObject> extent01 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(0), shapes2Obj.get(1)}));
		Set<IContextObject> extent123 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(1), shapes2Obj.get(2), shapes2Obj.get(3)}));
		IConcept absurdity = concepts.getAbsurdity();
		IConcept cat0 = concepts.getConceptWithExtent(extent0);
		IConcept cat1 = concepts.getConceptWithExtent(extent1);
		IConcept cat2 = concepts.getConceptWithExtent(extent2);
		IConcept cat3 = concepts.getConceptWithExtent(extent3);
		IConcept cat02 = concepts.getConceptWithExtent(extent02);
		IConcept cat01 = concepts.getConceptWithExtent(extent01);
		IConcept cat123 = concepts.getConceptWithExtent(extent123);
		IConcept truism = concepts.getTruism();
		IConcept commitment = concepts.getOntologicalCommitment();
		assertTrue(absurdity.rank() == 0
				&& cat0.rank() == 1
				&& cat1.rank() == 1
				&& cat2.rank() == 1
				&& cat3.rank() == 1
				&& cat02.rank() == 2
				&& cat01.rank() == 2
				&& cat123.rank() == 2
				&& truism.rank() == 3
				&& commitment.rank() == 4);
	}	
	
	@Test
	public void whenCatTreeSupplierRequestedThenReturned() {
		IClassificationSupplier classificationSupplier = null;
		try {
			classificationSupplier = concepts.getClassificationSupplier();
		}
		catch (Exception e) {
			assertTrue(false);
		}
		assertNotNull(classificationSupplier);
	}	
	
	@Test
	public void whenOntologicalUSLReturnedThenReallyIsAnUpperSemilattice() {
		boolean isAnUpperSemilattice = true;
		try {
			concepts.getOntologicalUpperSemilattice().validate();
		}
		catch (DataFormatException e) {
			isAnUpperSemilattice = false;
		}
		assertTrue(isAnUpperSemilattice);
	}
	
	@Test
	public void categoryLatticeReturnedThenReallyIsALattice() {
		DirectedAcyclicGraph<IConcept, IsA> lattice = concepts.getConceptLattice();
		assertTrue(StructureInspector.isAnUpperSemilattice(lattice) 
				&& StructureInspector.isALowerSemilattice(lattice)
				&& !lattice.vertexSet().isEmpty());
	}
	
	@Test
	public void whenIsADirectSubCategoryOfMethodCalledThenConsistentReturn() {
		Set<IContextObject> extent0 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(0)}));
		Set<IContextObject> extent1 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(1)}));
		Set<IContextObject> extent2 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(2)}));
		Set<IContextObject> extent3 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(3)}));
		Set<IContextObject> extent02 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(0), shapes2Obj.get(2)}));
		Set<IContextObject> extent01 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(0), shapes2Obj.get(1)}));
		Set<IContextObject> extent123 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(1), shapes2Obj.get(2), shapes2Obj.get(3)}));
		IConcept cat0 = concepts.getConceptWithExtent(extent0);
		IConcept cat1 = concepts.getConceptWithExtent(extent1);
		IConcept cat2 = concepts.getConceptWithExtent(extent2);
		IConcept cat3 = concepts.getConceptWithExtent(extent3);
		IConcept cat02 = concepts.getConceptWithExtent(extent02);
		IConcept cat01 = concepts.getConceptWithExtent(extent01);
		IConcept cat123 = concepts.getConceptWithExtent(extent123);
		IConcept truism = concepts.getTruism();
		IConcept commitment = concepts.getOntologicalCommitment();
		assertTrue(
				concepts.isADirectSubordinateOf(cat0, cat01)
				&& concepts.isADirectSubordinateOf(cat0, cat02)
				&& concepts.isADirectSubordinateOf(cat1, cat01)
				&& concepts.isADirectSubordinateOf(cat1, cat123)
				&& concepts.isADirectSubordinateOf(cat2, cat02)
				&& concepts.isADirectSubordinateOf(cat2, cat123)
				&& concepts.isADirectSubordinateOf(cat3, cat123)
				&& concepts.isADirectSubordinateOf(cat02, truism)
				&& concepts.isADirectSubordinateOf(cat01, truism)
				&& concepts.isADirectSubordinateOf(cat123, truism)
				&& concepts.isADirectSubordinateOf(truism, commitment)
				&& !concepts.isADirectSubordinateOf(cat0, truism)
				&& !concepts.isADirectSubordinateOf(cat02, cat0));
	}
	
	@Test
	public void whenIsAMethodCalledThenConsistentReturn() {
		List<IConcept> catList = concepts.getTopologicalSorting();
		for (int i = 0 ; i < catList.size() ; i++) {
			IConcept catI = catList.get(i);
			for (int j = 0 ; j < catList.size() ; j++) {
				IConcept catJ = catList.get(j);
				if (concepts.isA(catI, catJ)) {
					if (!catJ.getExtent().containsAll(catI.getExtent())
							&& catI.type() != IConcept.TRUISM
							&& catJ.type() != IConcept.ONTOLOGICAL_COMMITMENT)
							assertTrue(false);
				}
			}
		}
		assertTrue(true);
	}
	
	@Test
	public void whenLeastCommonSuperordinateRequiredThenExpectedReturned() {
		boolean unexpected = false;
		Set<Set<IConcept>> objPowerSet = buildCatsPowerSet(concepts.getTopologicalSorting()); 
		for (Set<IConcept> subset : objPowerSet) {
			if (!subset.isEmpty()) {
				IConcept lcs = concepts.getLeastCommonSuperordinate(subset);
				for (IConcept current : concepts.getTopologicalSorting()) {
					if (concepts.areA(new ArrayList<>(subset), current)) {
						if (!concepts.isA(lcs, current) && !lcs.equals(current))
							unexpected = true;
					}
				}
			}
		}
		assertFalse(unexpected);
	}
	
	@Test
	public void whenObjectCategoriesRequestedThenReturned() throws Exception {
		Set<IConcept> objectCats = new HashSet<>(concepts.getSingletonConcept());
		Set<Set<IConstruct>> expectedSetsOfConstructs = new HashSet<>();
		Set<Set<IConstruct>> objCatSetsOfConstructs = new HashSet<>();
		for (IContextObject obj : shapes2Obj)
			expectedSetsOfConstructs.add(new HashSet<>(obj.getConstructs()));
		for (IConcept objCat : objectCats) {
			Set<IConstruct> catConstructs = objCat.getIntent()
					.stream()
					.map(a -> new Construct(a))
					.collect(Collectors.toSet());
			objCatSetsOfConstructs.add(catConstructs);
		}
		assertTrue((objectCats.size() == shapes2Obj.size()) 
				&& objCatSetsOfConstructs.retainAll(expectedSetsOfConstructs) == false); 
	}
	
	@Test
	public void whenTruismRequestedThenReturnedWithExpectedIntent() {
		Set<List<String>> expectedIntentString = new HashSet<>();
		Set<List<String>> returnedIntentString;
		Set<List<String>> unexpectedReturnedIntentString;
		expectedIntentString.add(new ArrayList<>(Arrays.asList(new String[] {"figure", "forme", "_"})));
		expectedIntentString.add(new ArrayList<>(Arrays.asList(new String[] {"figure", "trait", "épaisseur", "_"})));
		expectedIntentString.add(new ArrayList<>(Arrays.asList(new String[] {"figure", "trait", "couleur", "_"})));
		expectedIntentString.add(new ArrayList<>(Arrays.asList(new String[] {"figure", "fond", "_"})));
		expectedIntentString.add(new ArrayList<>(Arrays.asList(new String[] {"figure", "fond", "_", "couleur", "_"})));
		expectedIntentString.add(new ArrayList<>(Arrays.asList(new String[] {"figure", "_", "couleur", "bleu"})));
		returnedIntentString = concepts.getTruism().getIntent()
				.stream()
				.map(c -> c.toListOfStringsWithPlaceholders())
				.collect(Collectors.toSet());
		unexpectedReturnedIntentString = new HashSet<>(returnedIntentString);
		unexpectedReturnedIntentString.removeAll(expectedIntentString);
		assertTrue(expectedIntentString.size() == returnedIntentString.size() 
				&& unexpectedReturnedIntentString.isEmpty());
	}
	
	private Set<Set<IConcept>> buildCatsPowerSet(List<IConcept> objects) {
	    Set<Set<IConcept>> powerSet = new HashSet<Set<IConcept>>();
	    for (int i = 0; i < (1 << objects.size()); i++) {
	    	Set<IConcept> subset = new HashSet<IConcept>();
	        for (int j = 0; j < objects.size(); j++) {
	            if(((1 << j) & i) > 0)
	            	subset.add(objects.get(j));
	        }
	        powerSet.add(subset);
	    }
	    return powerSet;
	}
	
	private Set<IConcept> removeNonMaximalElements(Set<IConcept> cats){
		List<IConcept> catList = new ArrayList<>(cats);
		for (int i = 0 ; i < catList.size() - 1 ; i++) {
			IConcept catI = catList.get(i);
			if (cats.contains(catI)) {
				for (int j = i+1 ; j < catList.size() ; j++) {
					IConcept catJ = catList.get(j);
					if (cats.contains(catJ)) {
						if (concepts.isA(catI, catJ))
							cats.remove(catI);
						else if (concepts.isA(catJ, catI))
							cats.remove(catJ);
					}
				}
			}
		}
		return cats;
	}
	
	private Set<IConcept> removeNonMinimalElements(Set<IConcept> cats){
		List<IConcept> catList = new ArrayList<>(cats);
		for (int i = 0 ; i < catList.size() - 1 ; i++) {
			IConcept catI = catList.get(i);
			if (cats.contains(catI)) {
				for (int j = i+1 ; j < catList.size() ; j++) {
					IConcept catJ = catList.get(j);
					if (cats.contains(catJ)) {
						if (concepts.isA(catI, catJ))
							cats.remove(catJ);
						else if (concepts.isA(catJ, catI))
							cats.remove(catI);
					}
				}
			}
		}
		return cats;
	}	
	
	@Test
	public void whenTreeSuppliedThenReallyIsATree() throws IOException {
		IClassificationSupplier classificationSupplier = concepts.getClassificationSupplier();
		int nbOfChecks = 0;
		while (classificationSupplier.hasNext()) {
			Tree<IConcept, IsA> nextTree = classificationSupplier.next().getClassificationTree();
			/*
			Visualizer.visualizeCategoryGraph(nextTree, "2109231614_classification" + Integer.toString(nbOfChecks));
			*/
			try {
				nextTree.validate();
				nbOfChecks++;
			}
			catch (DataFormatException e) {
				fail();
			}
		}
		assertTrue(nbOfChecks > 0);
	}

}
