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

import com.tregouet.occam.alg.concepts_gen.IConceptTreeSupplier;
import com.tregouet.occam.alg.scoring.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring.ScoringStrategy;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IContextObject;
import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.data.concepts.impl.Concepts;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.StructureInspector;

@SuppressWarnings("unused")
public class DenotationSetsTest {

	private static Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;
	private static IConcepts concepts;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(shapes2);
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy.SCORING_STRATEGY_1);
	}

	@Test
	public void whenDenotationSetLatticeReturnedThenReallyIsALattice() {
		DirectedAcyclicGraph<IConcept, IIsA> lattice = concepts.getLatticeOfConcepts();
		assertTrue(StructureInspector.isAnUpperSemilattice(lattice) 
				&& StructureInspector.isALowerSemilattice(lattice)
				&& !lattice.vertexSet().isEmpty());
	}
	
	@Before
	public void setUp() throws Exception {
		concepts = new Concepts(shapes2Obj);
	}	

	@Test
	public void whenDenotationSetsReturnedThenContains1Absurdity1Truism1Commitment() {
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
	public void whenDenotationSetsReturnedThenEachHasADistinctIntent() {
		Set<Set<IDenotation>> setsOfDenotations = new HashSet<>();
		for (IConcept cat : concepts.getTopologicalSorting())
			setsOfDenotations.add(cat.getDenotations());
		assertTrue(setsOfDenotations.size() == concepts.getTopologicalSorting().size());
	}	
	
	@Test
	public void whenDenotationSetsReturnedThenTruismAndCommitmentHaveSameExtent() {
		Set<Set<IContextObject>> extents = new HashSet<>();
		extents.add(concepts.getTruism().getExtent());
		extents.add(concepts.getOntologicalCommitment().getExtent());
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
		IConcept absurdity = concepts.getAbsurdity();
		IConcept dS0 = concepts.getConceptWithExtent(extent0);
		IConcept dS1 = concepts.getConceptWithExtent(extent1);
		IConcept dS2 = concepts.getConceptWithExtent(extent2);
		IConcept dS3 = concepts.getConceptWithExtent(extent3);
		IConcept dS02 = concepts.getConceptWithExtent(extent02);
		IConcept dS01 = concepts.getConceptWithExtent(extent01);
		IConcept dS123 = concepts.getConceptWithExtent(extent123);
		IConcept truism = concepts.getTruism();
		IConcept commitment = concepts.getOntologicalCommitment();
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
		IConceptTreeSupplier conceptTreeSupplier = null;
		try {
			conceptTreeSupplier = concepts.getConceptTreeSupplier();
		}
		catch (Exception e) {
			assertTrue(false);
		}
		assertNotNull(conceptTreeSupplier);
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
		IConcept dS0 = concepts.getConceptWithExtent(extent0);
		IConcept dS1 = concepts.getConceptWithExtent(extent1);
		IConcept dS2 = concepts.getConceptWithExtent(extent2);
		IConcept dS3 = concepts.getConceptWithExtent(extent3);
		IConcept dS02 = concepts.getConceptWithExtent(extent02);
		IConcept dS01 = concepts.getConceptWithExtent(extent01);
		IConcept dS123 = concepts.getConceptWithExtent(extent123);
		IConcept truism = concepts.getTruism();
		IConcept commitment = concepts.getOntologicalCommitment();
		assertTrue(
				concepts.isADirectSubordinateOf(dS0, dS01)
				&& concepts.isADirectSubordinateOf(dS0, dS02)
				&& concepts.isADirectSubordinateOf(dS1, dS01)
				&& concepts.isADirectSubordinateOf(dS1, dS123)
				&& concepts.isADirectSubordinateOf(dS2, dS02)
				&& concepts.isADirectSubordinateOf(dS2, dS123)
				&& concepts.isADirectSubordinateOf(dS3, dS123)
				&& concepts.isADirectSubordinateOf(dS02, truism)
				&& concepts.isADirectSubordinateOf(dS01, truism)
				&& concepts.isADirectSubordinateOf(dS123, truism)
				&& concepts.isADirectSubordinateOf(truism, commitment)
				&& !concepts.isADirectSubordinateOf(dS0, truism)
				&& !concepts.isADirectSubordinateOf(dS02, dS0));
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
	public void whenObjectDenotationSetsRequestedThenReturned() throws Exception {
		Set<IConcept> objectDenotationSets = new HashSet<>(concepts.getObjectConcepts());
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
			concepts.getOntologicalUpperSemilattice().validate();
		}
		catch (DataFormatException e) {
			isAnUpperSemilattice = false;
		}
		assertTrue(isAnUpperSemilattice);
	}
	
	@Test
	public void whenTreeSuppliedThenReallyIsATree() throws IOException {
		IConceptTreeSupplier conceptTreeSupplier = concepts.getConceptTreeSupplier();
		int nbOfChecks = 0;
		while (conceptTreeSupplier.hasNext()) {
			Tree<IConcept, IIsA> nextTree = conceptTreeSupplier.next();
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
		returnedIntentString = concepts.getTruism().getDenotations()
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
						if (concepts.isA(iDS, jDS))
							denotSets.remove(iDS);
						else if (concepts.isA(jDS, iDS))
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
						if (concepts.isA(iDS, jDS))
							denotSets.remove(jDS);
						else if (concepts.isA(jDS, iDS))
							denotSets.remove(iDS);
					}
				}
			}
		}
		return denotSets;
	}

}
