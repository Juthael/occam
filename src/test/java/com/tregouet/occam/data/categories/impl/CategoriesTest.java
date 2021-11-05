package com.tregouet.occam.data.categories.impl;

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

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.categories.IClassificationTreeSupplier;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.constructs.impl.Construct;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.ITreeFinder;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.error.InvalidInputException;
import com.tregouet.tree_finder.utils.StructureInspector;

@SuppressWarnings("unused")
public class CategoriesTest {

	private static Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;
	private static ICategories categories;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(shapes2);
		/*
		Categories catImpl = (Categories) categories;
		Visualizer.visualize(catImpl.getDiagram(), "2107291658");
		*/
	}

	@Before
	public void setUp() throws Exception {
		categories = new Categories(shapes2Obj);
	}
	
	@Test
	public void whenCategoriesReturnedThenContains1Absurdity1Truism1Commitment() {
		int nbOfTruism = 0;
		int nbOfCommitments = 0;
		for (ICategory cat : categories.getTopologicalSorting()) {
			if (cat.type() == ICategory.TRUISM)
				nbOfTruism++;
			else if (cat.type() == ICategory.ONTOLOGICAL_COMMITMENT)
				nbOfCommitments++;
		}
		assertTrue(nbOfTruism == 1 && nbOfCommitments == 1);
	}	

	@Test
	public void whenCategoriesReturnedThenEachHasADistinctIntent() {
		Set<Set<IIntentAttribute>> intents = new HashSet<>();
		for (ICategory cat : categories.getTopologicalSorting())
			intents.add(cat.getIntent());
		assertTrue(intents.size() == categories.getTopologicalSorting().size());
	}
	

	@Test
	public void whenCategoriesReturnedThenTruismAndCommitmentHaveSameExtent() {
		Set<Set<IContextObject>> extents = new HashSet<>();
		extents.add(categories.getTruism().getExtent());
		extents.add(categories.getOntologicalCommitment().getExtent());
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
		ICategory absurdity = categories.getAbsurdity();
		ICategory cat0 = categories.getCatWithExtent(extent0);
		ICategory cat1 = categories.getCatWithExtent(extent1);
		ICategory cat2 = categories.getCatWithExtent(extent2);
		ICategory cat3 = categories.getCatWithExtent(extent3);
		ICategory cat02 = categories.getCatWithExtent(extent02);
		ICategory cat01 = categories.getCatWithExtent(extent01);
		ICategory cat123 = categories.getCatWithExtent(extent123);
		ICategory truism = categories.getTruism();
		ICategory commitment = categories.getOntologicalCommitment();
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
		ITreeFinder<ICategory, DefaultEdge> classificationTreeSupplier = null;
		try {
			classificationTreeSupplier = categories.getCatTreeSupplier();
		}
		catch (Exception e) {
			assertTrue(false);
		}
		assertNotNull(classificationTreeSupplier);
	}	
	
	@Test
	public void whenCatTreeSupplierWithConstrainedExtentStructureRequestedThenReturned() {
		fail("Not yet implemented");
	}	
	
	@Test
	public void whenOntologicalUSLReturnedThenReallyIsAnUpperSemilattice() {
		boolean isAnUpperSemilattice = true;
		try {
			categories.getOntologicalUpperSemilattice().validate();
		}
		catch (InvalidInputException e) {
			isAnUpperSemilattice = false;
		}
		assertTrue(isAnUpperSemilattice);
	}
	
	@Test
	public void categoryLatticeReturnedThenReallyIsALattice() {
		DirectedAcyclicGraph<ICategory, DefaultEdge> lattice = categories.getCategoryLattice();
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
		ICategory cat0 = categories.getCatWithExtent(extent0);
		ICategory cat1 = categories.getCatWithExtent(extent1);
		ICategory cat2 = categories.getCatWithExtent(extent2);
		ICategory cat3 = categories.getCatWithExtent(extent3);
		ICategory cat02 = categories.getCatWithExtent(extent02);
		ICategory cat01 = categories.getCatWithExtent(extent01);
		ICategory cat123 = categories.getCatWithExtent(extent123);
		ICategory truism = categories.getTruism();
		ICategory commitment = categories.getOntologicalCommitment();
		assertTrue(
				categories.isADirectSubordinateOf(cat0, cat01)
				&& categories.isADirectSubordinateOf(cat0, cat02)
				&& categories.isADirectSubordinateOf(cat1, cat01)
				&& categories.isADirectSubordinateOf(cat1, cat123)
				&& categories.isADirectSubordinateOf(cat2, cat02)
				&& categories.isADirectSubordinateOf(cat2, cat123)
				&& categories.isADirectSubordinateOf(cat3, cat123)
				&& categories.isADirectSubordinateOf(cat02, truism)
				&& categories.isADirectSubordinateOf(cat01, truism)
				&& categories.isADirectSubordinateOf(cat123, truism)
				&& categories.isADirectSubordinateOf(truism, commitment)
				&& !categories.isADirectSubordinateOf(cat0, truism)
				&& !categories.isADirectSubordinateOf(cat02, cat0));
	}
	
	@Test
	public void whenIsAMethodCalledThenConsistentReturn() {
		List<ICategory> catList = categories.getTopologicalSorting();
		for (int i = 0 ; i < catList.size() ; i++) {
			ICategory catI = catList.get(i);
			for (int j = 0 ; j < catList.size() ; j++) {
				ICategory catJ = catList.get(j);
				if (categories.isA(catI, catJ)) {
					if (!catJ.getExtent().containsAll(catI.getExtent())
							&& catI.type() != ICategory.TRUISM
							&& catJ.type() != ICategory.ONTOLOGICAL_COMMITMENT)
							assertTrue(false);
				}
			}
		}
		assertTrue(true);
	}
	
	@Test
	public void whenLeastCommonSuperordinateRequiredThenExpectedReturned() {
		boolean unexpected = false;
		Set<Set<ICategory>> objPowerSet = buildCatsPowerSet(categories.getTopologicalSorting()); 
		for (Set<ICategory> subset : objPowerSet) {
			if (!subset.isEmpty()) {
				ICategory lcs = categories.getLeastCommonSuperordinate(subset);
				for (ICategory current : categories.getTopologicalSorting()) {
					if (categories.areA(new ArrayList<>(subset), current)) {
						if (!categories.isA(lcs, current) && !lcs.equals(current))
							unexpected = true;
					}
				}
			}
		}
		assertFalse(unexpected);
	}
	
	@Test
	public void whenObjectCategoriesRequestedThenReturned() throws Exception {
		Set<ICategory> objectCats = new HashSet<>(categories.getObjectCategories());
		Set<Set<IConstruct>> expectedSetsOfConstructs = new HashSet<>();
		Set<Set<IConstruct>> objCatSetsOfConstructs = new HashSet<>();
		for (IContextObject obj : shapes2Obj)
			expectedSetsOfConstructs.add(new HashSet<>(obj.getConstructs()));
		for (ICategory objCat : objectCats) {
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
		returnedIntentString = categories.getTruism().getIntent()
				.stream()
				.map(c -> c.toListOfStringsWithPlaceholders())
				.collect(Collectors.toSet());
		unexpectedReturnedIntentString = new HashSet<>(returnedIntentString);
		unexpectedReturnedIntentString.removeAll(expectedIntentString);
		assertTrue(expectedIntentString.size() == returnedIntentString.size() 
				&& unexpectedReturnedIntentString.isEmpty());
	}
	
	private Set<Set<ICategory>> buildCatsPowerSet(List<ICategory> objects) {
	    Set<Set<ICategory>> powerSet = new HashSet<Set<ICategory>>();
	    for (int i = 0; i < (1 << objects.size()); i++) {
	    	Set<ICategory> subset = new HashSet<ICategory>();
	        for (int j = 0; j < objects.size(); j++) {
	            if(((1 << j) & i) > 0)
	            	subset.add(objects.get(j));
	        }
	        powerSet.add(subset);
	    }
	    return powerSet;
	}
	
	private Set<ICategory> removeNonMaximalElements(Set<ICategory> cats){
		List<ICategory> catList = new ArrayList<>(cats);
		for (int i = 0 ; i < catList.size() - 1 ; i++) {
			ICategory catI = catList.get(i);
			if (cats.contains(catI)) {
				for (int j = i+1 ; j < catList.size() ; j++) {
					ICategory catJ = catList.get(j);
					if (cats.contains(catJ)) {
						if (categories.isA(catI, catJ))
							cats.remove(catI);
						else if (categories.isA(catJ, catI))
							cats.remove(catJ);
					}
				}
			}
		}
		return cats;
	}
	
	private Set<ICategory> removeNonMinimalElements(Set<ICategory> cats){
		List<ICategory> catList = new ArrayList<>(cats);
		for (int i = 0 ; i < catList.size() - 1 ; i++) {
			ICategory catI = catList.get(i);
			if (cats.contains(catI)) {
				for (int j = i+1 ; j < catList.size() ; j++) {
					ICategory catJ = catList.get(j);
					if (cats.contains(catJ)) {
						if (categories.isA(catI, catJ))
							cats.remove(catJ);
						else if (categories.isA(catJ, catI))
							cats.remove(catI);
					}
				}
			}
		}
		return cats;
	}	
	
	@Test
	public void whenTreeSuppliedThenReallyIsATree() throws IOException, InvalidInputException {
		ITreeFinder<ICategory, DefaultEdge> treeSupplier = categories.getCatTreeSupplier();
		int nbOfChecks = 0;
		while (treeSupplier.hasNext()) {
			Tree<ICategory, DefaultEdge> nextTree = treeSupplier.next();
			/*
			Visualizer.visualizeCategoryGraph(nextTree, "2109231614_classification" + Integer.toString(nbOfChecks));
			*/
			try {
				nextTree.validate();
				nbOfChecks++;
			}
			catch (InvalidInputException e) {
				fail();
			}
		}
		assertTrue(nbOfChecks > 0);
	}

}
