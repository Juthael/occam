package com.tregouet.occam.data.denotations.impl;

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

import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilder;
import com.tregouet.occam.alg.scoring_dep.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring_dep.ScoringStrategy_dep;
import com.tregouet.occam.data.languages.words.construct.IConstruct;
import com.tregouet.occam.data.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IDenotation;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.concepts.impl.ConceptLattice;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.StructureInspector;

@SuppressWarnings("unused")
public class DenotationSetsTest {

	private static Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;
	private static IConceptLattice conceptLattice;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(shapes2);
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy_dep.SCORING_STRATEGY_1);
	}

	@Test
	public void whenDenotationSetLatticeReturnedThenReallyIsALattice() {
		DirectedAcyclicGraph<IConcept, IIsA> lattice = conceptLattice.getLatticeOfConcepts();
		assertTrue(StructureInspector.isAnUpperSemilattice(lattice) 
				&& StructureInspector.isALowerSemilattice(lattice)
				&& !lattice.vertexSet().isEmpty());
	}
	
	@Before
	public void setUp() throws Exception {
		conceptLattice = new ConceptLattice(shapes2Obj);
	}	

	@Test
	public void whenDenotationSetsReturnedThenContains1Absurdity1Truism1Commitment() {
		int nbOfTruism = 0;
		int nbOfCommitments = 0;
		for (IConcept cat : conceptLattice.getTopologicalSorting()) {
			if (cat.type() == IConcept.TRUISM)
				nbOfTruism++;
			else if (cat.type() == IConcept.ONTOLOGICAL_COMMITMENT)
				nbOfCommitments++;
		}
		assertTrue(nbOfTruism == 1 && nbOfCommitments == 1);
	}
	

	@Test
	public void whenDenotationSetsReturnedThenEachHasADistinctIntent() {
		Set<Set<IDenotation>> setsOfDenotations = new HashSet<>();
		for (IConcept cat : conceptLattice.getTopologicalSorting())
			setsOfDenotations.add(cat.getDenotations());
		assertTrue(setsOfDenotations.size() == conceptLattice.getTopologicalSorting().size());
	}	
	
	@Test
	public void whenDenotationSetsReturnedThenTruismAndCommitmentHaveSameExtent() {
		Set<Set<IContextObject>> extents = new HashSet<>();
		extents.add(conceptLattice.getTruism().getExtent());
		extents.add(conceptLattice.getOntologicalCommitment().getExtent());
		assertTrue(extents.size() == 1);
	}	
	
	@Test
	public void whenDenotationSetsRankRequestedThenAsExpected() {
		Set<IContextObject> extent0 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(0)}));
		Set<IContextObject> extent1 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(1)}));
		Set<IContextObject> extent2 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(2)}));
		Set<IContextObject> extent3 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(3)}));
		Set<IContextObject> extent02 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(0), shapes2Obj.get(2)}));
		Set<IContextObject> extent01 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(0), shapes2Obj.get(1)}));
		Set<IContextObject> extent123 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(1), shapes2Obj.get(2), shapes2Obj.get(3)}));
		IConcept absurdity = conceptLattice.getAbsurdity();
		IConcept dS0 = conceptLattice.getConceptWithExtent(extent0);
		IConcept dS1 = conceptLattice.getConceptWithExtent(extent1);
		IConcept dS2 = conceptLattice.getConceptWithExtent(extent2);
		IConcept dS3 = conceptLattice.getConceptWithExtent(extent3);
		IConcept dS02 = conceptLattice.getConceptWithExtent(extent02);
		IConcept dS01 = conceptLattice.getConceptWithExtent(extent01);
		IConcept dS123 = conceptLattice.getConceptWithExtent(extent123);
		IConcept truism = conceptLattice.getTruism();
		IConcept commitment = conceptLattice.getOntologicalCommitment();
		assertTrue(absurdity.rank() == 0
				&& dS0.rank() == 1
				&& dS1.rank() == 1
				&& dS2.rank() == 1
				&& dS3.rank() == 1
				&& dS02.rank() == 2
				&& dS01.rank() == 2
				&& dS123.rank() == 2
				&& truism.rank() == 3
				&& commitment.rank() == 4);
	}	
	
	@Test
	public void whenDenotationSetTreeSupplierRequestedThenReturned() {
		ConceptTreeBuilder conceptTreeBuilder = null;
		try {
			conceptTreeBuilder = conceptLattice.getConceptTreeSupplier();
		}
		catch (Exception e) {
			assertTrue(false);
		}
		assertNotNull(conceptTreeBuilder);
	}
	
	@Test
	public void whenIsADirectSubordinateOfMethodCalledThenConsistentReturn() {
		Set<IContextObject> extent0 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(0)}));
		Set<IContextObject> extent1 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(1)}));
		Set<IContextObject> extent2 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(2)}));
		Set<IContextObject> extent3 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(3)}));
		Set<IContextObject> extent02 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(0), shapes2Obj.get(2)}));
		Set<IContextObject> extent01 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(0), shapes2Obj.get(1)}));
		Set<IContextObject> extent123 = new HashSet<>(Arrays.asList(new IContextObject[] {shapes2Obj.get(1), shapes2Obj.get(2), shapes2Obj.get(3)}));
		IConcept dS0 = conceptLattice.getConceptWithExtent(extent0);
		IConcept dS1 = conceptLattice.getConceptWithExtent(extent1);
		IConcept dS2 = conceptLattice.getConceptWithExtent(extent2);
		IConcept dS3 = conceptLattice.getConceptWithExtent(extent3);
		IConcept dS02 = conceptLattice.getConceptWithExtent(extent02);
		IConcept dS01 = conceptLattice.getConceptWithExtent(extent01);
		IConcept dS123 = conceptLattice.getConceptWithExtent(extent123);
		IConcept truism = conceptLattice.getTruism();
		IConcept commitment = conceptLattice.getOntologicalCommitment();
		assertTrue(
				conceptLattice.isADirectSubordinateOf(dS0, dS01)
				&& conceptLattice.isADirectSubordinateOf(dS0, dS02)
				&& conceptLattice.isADirectSubordinateOf(dS1, dS01)
				&& conceptLattice.isADirectSubordinateOf(dS1, dS123)
				&& conceptLattice.isADirectSubordinateOf(dS2, dS02)
				&& conceptLattice.isADirectSubordinateOf(dS2, dS123)
				&& conceptLattice.isADirectSubordinateOf(dS3, dS123)
				&& conceptLattice.isADirectSubordinateOf(dS02, truism)
				&& conceptLattice.isADirectSubordinateOf(dS01, truism)
				&& conceptLattice.isADirectSubordinateOf(dS123, truism)
				&& conceptLattice.isADirectSubordinateOf(truism, commitment)
				&& !conceptLattice.isADirectSubordinateOf(dS0, truism)
				&& !conceptLattice.isADirectSubordinateOf(dS02, dS0));
	}
	
	@Test
	public void whenIsAMethodCalledThenConsistentReturn() {
		List<IConcept> catList = conceptLattice.getTopologicalSorting();
		for (int i = 0 ; i < catList.size() ; i++) {
			IConcept catI = catList.get(i);
			for (int j = 0 ; j < catList.size() ; j++) {
				IConcept catJ = catList.get(j);
				if (conceptLattice.isA(catI, catJ)) {
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
		Set<Set<IConcept>> objPowerSet = buildCatsPowerSet(conceptLattice.getTopologicalSorting()); 
		for (Set<IConcept> subset : objPowerSet) {
			if (!subset.isEmpty()) {
				IConcept lcs = conceptLattice.getLeastCommonSuperordinate(subset);
				for (IConcept current : conceptLattice.getTopologicalSorting()) {
					if (conceptLattice.areA(new ArrayList<>(subset), current)) {
						if (!conceptLattice.isA(lcs, current) && !lcs.equals(current))
							unexpected = true;
					}
				}
			}
		}
		assertFalse(unexpected);
	}
	
	@Test
	public void whenObjectDenotationSetsRequestedThenReturned() throws Exception {
		Set<IConcept> objectDenotationSets = new HashSet<>(conceptLattice.getObjectConcepts());
		Set<Set<IConstruct>> expectedSetsOfConstructs = new HashSet<>();
		Set<Set<IConstruct>> objSetsOfDenotatingConstructs = new HashSet<>();
		for (IContextObject obj : shapes2Obj)
			expectedSetsOfConstructs.add(new HashSet<>(obj.getConstructs()));
		for (IConcept objDenotationSet : objectDenotationSets) {
			Set<IConstruct> objDenotatingConstructs = objDenotationSet.getDenotations()
					.stream()
					.map(a -> new Construct(a))
					.collect(Collectors.toSet());
			objSetsOfDenotatingConstructs.add(objDenotatingConstructs);
		}
		assertTrue((objectDenotationSets.size() == shapes2Obj.size()) 
				&& objSetsOfDenotatingConstructs.retainAll(expectedSetsOfConstructs) == false); 
	}
	
	@Test
	public void whenOntologicalUSLReturnedThenReallyIsAnUpperSemilattice() {
		boolean isAnUpperSemilattice = true;
		try {
			conceptLattice.getOntologicalUpperSemilattice().validate();
		}
		catch (DataFormatException e) {
			isAnUpperSemilattice = false;
		}
		assertTrue(isAnUpperSemilattice);
	}
	
	@Test
	public void whenTreeSuppliedThenReallyIsATree() throws IOException {
		ConceptTreeBuilder conceptTreeBuilder = conceptLattice.getConceptTreeSupplier();
		int nbOfChecks = 0;
		while (conceptTreeBuilder.hasNext()) {
			Tree<IConcept, IIsA> nextTree = conceptTreeBuilder.next();
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
		returnedIntentString = conceptLattice.getTruism().getDenotations()
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
	
	private Set<IConcept> removeNonMaximalElements(Set<IConcept> denotSets){
		List<IConcept> dSList = new ArrayList<>(denotSets);
		for (int i = 0 ; i < dSList.size() - 1 ; i++) {
			IConcept iDS = dSList.get(i);
			if (denotSets.contains(iDS)) {
				for (int j = i+1 ; j < dSList.size() ; j++) {
					IConcept jDS = dSList.get(j);
					if (denotSets.contains(jDS)) {
						if (conceptLattice.isA(iDS, jDS))
							denotSets.remove(iDS);
						else if (conceptLattice.isA(jDS, iDS))
							denotSets.remove(jDS);
					}
				}
			}
		}
		return denotSets;
	}	
	
	private Set<IConcept> removeNonMinimalElements(Set<IConcept> denotSets){
		List<IConcept> dSList = new ArrayList<>(denotSets);
		for (int i = 0 ; i < dSList.size() - 1 ; i++) {
			IConcept iDS = dSList.get(i);
			if (denotSets.contains(iDS)) {
				for (int j = i+1 ; j < dSList.size() ; j++) {
					IConcept jDS = dSList.get(j);
					if (denotSets.contains(jDS)) {
						if (conceptLattice.isA(iDS, jDS))
							denotSets.remove(jDS);
						else if (conceptLattice.isA(jDS, iDS))
							denotSets.remove(iDS);
					}
				}
			}
		}
		return denotSets;
	}

}
