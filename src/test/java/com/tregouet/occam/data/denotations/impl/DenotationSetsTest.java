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

import com.tregouet.occam.alg.generators.preconcepts.IPreconceptTreeSupplier;
import com.tregouet.occam.alg.scoring_dep.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring_dep.ScoringStrategy_dep;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.preconcepts.IPreconcepts;
import com.tregouet.occam.data.preconcepts.impl.Preconcepts;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.StructureInspector;

@SuppressWarnings("unused")
public class DenotationSetsTest {

	private static Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;
	private static IPreconcepts preconcepts;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(shapes2);
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy_dep.SCORING_STRATEGY_1);
	}

	@Test
	public void whenDenotationSetLatticeReturnedThenReallyIsALattice() {
		DirectedAcyclicGraph<IPreconcept, IIsA> lattice = preconcepts.getLatticeOfConcepts();
		assertTrue(StructureInspector.isAnUpperSemilattice(lattice) 
				&& StructureInspector.isALowerSemilattice(lattice)
				&& !lattice.vertexSet().isEmpty());
	}
	
	@Before
	public void setUp() throws Exception {
		preconcepts = new Preconcepts(shapes2Obj);
	}	

	@Test
	public void whenDenotationSetsReturnedThenContains1Absurdity1Truism1Commitment() {
		int nbOfTruism = 0;
		int nbOfCommitments = 0;
		for (IPreconcept cat : preconcepts.getTopologicalSorting()) {
			if (cat.type() == IPreconcept.TRUISM)
				nbOfTruism++;
			else if (cat.type() == IPreconcept.ONTOLOGICAL_COMMITMENT)
				nbOfCommitments++;
		}
		assertTrue(nbOfTruism == 1 && nbOfCommitments == 1);
	}
	

	@Test
	public void whenDenotationSetsReturnedThenEachHasADistinctIntent() {
		Set<Set<IDenotation>> setsOfDenotations = new HashSet<>();
		for (IPreconcept cat : preconcepts.getTopologicalSorting())
			setsOfDenotations.add(cat.getDenotations());
		assertTrue(setsOfDenotations.size() == preconcepts.getTopologicalSorting().size());
	}	
	
	@Test
	public void whenDenotationSetsReturnedThenTruismAndCommitmentHaveSameExtent() {
		Set<Set<IContextObject>> extents = new HashSet<>();
		extents.add(preconcepts.getTruism().getExtent());
		extents.add(preconcepts.getOntologicalCommitment().getExtent());
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
		IPreconcept absurdity = preconcepts.getAbsurdity();
		IPreconcept dS0 = preconcepts.getConceptWithExtent(extent0);
		IPreconcept dS1 = preconcepts.getConceptWithExtent(extent1);
		IPreconcept dS2 = preconcepts.getConceptWithExtent(extent2);
		IPreconcept dS3 = preconcepts.getConceptWithExtent(extent3);
		IPreconcept dS02 = preconcepts.getConceptWithExtent(extent02);
		IPreconcept dS01 = preconcepts.getConceptWithExtent(extent01);
		IPreconcept dS123 = preconcepts.getConceptWithExtent(extent123);
		IPreconcept truism = preconcepts.getTruism();
		IPreconcept commitment = preconcepts.getOntologicalCommitment();
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
		IPreconceptTreeSupplier preconceptTreeSupplier = null;
		try {
			preconceptTreeSupplier = preconcepts.getConceptTreeSupplier();
		}
		catch (Exception e) {
			assertTrue(false);
		}
		assertNotNull(preconceptTreeSupplier);
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
		IPreconcept dS0 = preconcepts.getConceptWithExtent(extent0);
		IPreconcept dS1 = preconcepts.getConceptWithExtent(extent1);
		IPreconcept dS2 = preconcepts.getConceptWithExtent(extent2);
		IPreconcept dS3 = preconcepts.getConceptWithExtent(extent3);
		IPreconcept dS02 = preconcepts.getConceptWithExtent(extent02);
		IPreconcept dS01 = preconcepts.getConceptWithExtent(extent01);
		IPreconcept dS123 = preconcepts.getConceptWithExtent(extent123);
		IPreconcept truism = preconcepts.getTruism();
		IPreconcept commitment = preconcepts.getOntologicalCommitment();
		assertTrue(
				preconcepts.isADirectSubordinateOf(dS0, dS01)
				&& preconcepts.isADirectSubordinateOf(dS0, dS02)
				&& preconcepts.isADirectSubordinateOf(dS1, dS01)
				&& preconcepts.isADirectSubordinateOf(dS1, dS123)
				&& preconcepts.isADirectSubordinateOf(dS2, dS02)
				&& preconcepts.isADirectSubordinateOf(dS2, dS123)
				&& preconcepts.isADirectSubordinateOf(dS3, dS123)
				&& preconcepts.isADirectSubordinateOf(dS02, truism)
				&& preconcepts.isADirectSubordinateOf(dS01, truism)
				&& preconcepts.isADirectSubordinateOf(dS123, truism)
				&& preconcepts.isADirectSubordinateOf(truism, commitment)
				&& !preconcepts.isADirectSubordinateOf(dS0, truism)
				&& !preconcepts.isADirectSubordinateOf(dS02, dS0));
	}
	
	@Test
	public void whenIsAMethodCalledThenConsistentReturn() {
		List<IPreconcept> catList = preconcepts.getTopologicalSorting();
		for (int i = 0 ; i < catList.size() ; i++) {
			IPreconcept catI = catList.get(i);
			for (int j = 0 ; j < catList.size() ; j++) {
				IPreconcept catJ = catList.get(j);
				if (preconcepts.isA(catI, catJ)) {
					if (!catJ.getExtent().containsAll(catI.getExtent())
							&& catI.type() != IPreconcept.TRUISM
							&& catJ.type() != IPreconcept.ONTOLOGICAL_COMMITMENT)
							assertTrue(false);
				}
			}
		}
		assertTrue(true);
	}
	
	@Test
	public void whenLeastCommonSuperordinateRequiredThenExpectedReturned() {
		boolean unexpected = false;
		Set<Set<IPreconcept>> objPowerSet = buildCatsPowerSet(preconcepts.getTopologicalSorting()); 
		for (Set<IPreconcept> subset : objPowerSet) {
			if (!subset.isEmpty()) {
				IPreconcept lcs = preconcepts.getLeastCommonSuperordinate(subset);
				for (IPreconcept current : preconcepts.getTopologicalSorting()) {
					if (preconcepts.areA(new ArrayList<>(subset), current)) {
						if (!preconcepts.isA(lcs, current) && !lcs.equals(current))
							unexpected = true;
					}
				}
			}
		}
		assertFalse(unexpected);
	}
	
	@Test
	public void whenObjectDenotationSetsRequestedThenReturned() throws Exception {
		Set<IPreconcept> objectDenotationSets = new HashSet<>(preconcepts.getObjectConcepts());
		Set<Set<IConstruct>> expectedSetsOfConstructs = new HashSet<>();
		Set<Set<IConstruct>> objSetsOfDenotatingConstructs = new HashSet<>();
		for (IContextObject obj : shapes2Obj)
			expectedSetsOfConstructs.add(new HashSet<>(obj.getConstructs()));
		for (IPreconcept objDenotationSet : objectDenotationSets) {
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
			preconcepts.getOntologicalUpperSemilattice().validate();
		}
		catch (DataFormatException e) {
			isAnUpperSemilattice = false;
		}
		assertTrue(isAnUpperSemilattice);
	}
	
	@Test
	public void whenTreeSuppliedThenReallyIsATree() throws IOException {
		IPreconceptTreeSupplier preconceptTreeSupplier = preconcepts.getConceptTreeSupplier();
		int nbOfChecks = 0;
		while (preconceptTreeSupplier.hasNext()) {
			Tree<IPreconcept, IIsA> nextTree = preconceptTreeSupplier.next();
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
		returnedIntentString = preconcepts.getTruism().getDenotations()
				.stream()
				.map(c -> c.toListOfStringsWithPlaceholders())
				.collect(Collectors.toSet());
		unexpectedReturnedIntentString = new HashSet<>(returnedIntentString);
		unexpectedReturnedIntentString.removeAll(expectedIntentString);
		assertTrue(expectedIntentString.size() == returnedIntentString.size() 
				&& unexpectedReturnedIntentString.isEmpty());
	}
	
	private Set<Set<IPreconcept>> buildCatsPowerSet(List<IPreconcept> objects) {
	    Set<Set<IPreconcept>> powerSet = new HashSet<Set<IPreconcept>>();
	    for (int i = 0; i < (1 << objects.size()); i++) {
	    	Set<IPreconcept> subset = new HashSet<IPreconcept>();
	        for (int j = 0; j < objects.size(); j++) {
	            if(((1 << j) & i) > 0)
	            	subset.add(objects.get(j));
	        }
	        powerSet.add(subset);
	    }
	    return powerSet;
	}
	
	private Set<IPreconcept> removeNonMaximalElements(Set<IPreconcept> denotSets){
		List<IPreconcept> dSList = new ArrayList<>(denotSets);
		for (int i = 0 ; i < dSList.size() - 1 ; i++) {
			IPreconcept iDS = dSList.get(i);
			if (denotSets.contains(iDS)) {
				for (int j = i+1 ; j < dSList.size() ; j++) {
					IPreconcept jDS = dSList.get(j);
					if (denotSets.contains(jDS)) {
						if (preconcepts.isA(iDS, jDS))
							denotSets.remove(iDS);
						else if (preconcepts.isA(jDS, iDS))
							denotSets.remove(jDS);
					}
				}
			}
		}
		return denotSets;
	}	
	
	private Set<IPreconcept> removeNonMinimalElements(Set<IPreconcept> denotSets){
		List<IPreconcept> dSList = new ArrayList<>(denotSets);
		for (int i = 0 ; i < dSList.size() - 1 ; i++) {
			IPreconcept iDS = dSList.get(i);
			if (denotSets.contains(iDS)) {
				for (int j = i+1 ; j < dSList.size() ; j++) {
					IPreconcept jDS = dSList.get(j);
					if (denotSets.contains(jDS)) {
						if (preconcepts.isA(iDS, jDS))
							denotSets.remove(jDS);
						else if (preconcepts.isA(jDS, iDS))
							denotSets.remove(iDS);
					}
				}
			}
		}
		return denotSets;
	}

}
