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

import com.tregouet.occam.alg.builders.preconcepts.trees.IPreconceptTreeBuilder;
import com.tregouet.occam.alg.scoring_dep.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring_dep.ScoringStrategy_dep;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.preconcepts.IPreconceptLattice;
import com.tregouet.occam.data.preconcepts.impl.PreconceptLattice;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.StructureInspector;

@SuppressWarnings("unused")
public class DenotationSetsTest {

	private static Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;
	private static IPreconceptLattice preconceptLattice;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(shapes2);
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy_dep.SCORING_STRATEGY_1);
	}

	@Test
	public void whenDenotationSetLatticeReturnedThenReallyIsALattice() {
		DirectedAcyclicGraph<IPreconcept, IIsA> lattice = preconceptLattice.getLatticeOfConcepts();
		assertTrue(StructureInspector.isAnUpperSemilattice(lattice) 
				&& StructureInspector.isALowerSemilattice(lattice)
				&& !lattice.vertexSet().isEmpty());
	}
	
	@Before
	public void setUp() throws Exception {
		preconceptLattice = new PreconceptLattice(shapes2Obj);
	}	

	@Test
	public void whenDenotationSetsReturnedThenContains1Absurdity1Truism1Commitment() {
		int nbOfTruism = 0;
		int nbOfCommitments = 0;
		for (IPreconcept cat : preconceptLattice.getTopologicalSorting()) {
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
		for (IPreconcept cat : preconceptLattice.getTopologicalSorting())
			setsOfDenotations.add(cat.getDenotations());
		assertTrue(setsOfDenotations.size() == preconceptLattice.getTopologicalSorting().size());
	}	
	
	@Test
	public void whenDenotationSetsReturnedThenTruismAndCommitmentHaveSameExtent() {
		Set<Set<IContextObject>> extents = new HashSet<>();
		extents.add(preconceptLattice.getTruism().getExtent());
		extents.add(preconceptLattice.getOntologicalCommitment().getExtent());
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
		IPreconcept absurdity = preconceptLattice.getAbsurdity();
		IPreconcept dS0 = preconceptLattice.getConceptWithExtent(extent0);
		IPreconcept dS1 = preconceptLattice.getConceptWithExtent(extent1);
		IPreconcept dS2 = preconceptLattice.getConceptWithExtent(extent2);
		IPreconcept dS3 = preconceptLattice.getConceptWithExtent(extent3);
		IPreconcept dS02 = preconceptLattice.getConceptWithExtent(extent02);
		IPreconcept dS01 = preconceptLattice.getConceptWithExtent(extent01);
		IPreconcept dS123 = preconceptLattice.getConceptWithExtent(extent123);
		IPreconcept truism = preconceptLattice.getTruism();
		IPreconcept commitment = preconceptLattice.getOntologicalCommitment();
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
		IPreconceptTreeBuilder preconceptTreeBuilder = null;
		try {
			preconceptTreeBuilder = preconceptLattice.getConceptTreeSupplier();
		}
		catch (Exception e) {
			assertTrue(false);
		}
		assertNotNull(preconceptTreeBuilder);
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
		IPreconcept dS0 = preconceptLattice.getConceptWithExtent(extent0);
		IPreconcept dS1 = preconceptLattice.getConceptWithExtent(extent1);
		IPreconcept dS2 = preconceptLattice.getConceptWithExtent(extent2);
		IPreconcept dS3 = preconceptLattice.getConceptWithExtent(extent3);
		IPreconcept dS02 = preconceptLattice.getConceptWithExtent(extent02);
		IPreconcept dS01 = preconceptLattice.getConceptWithExtent(extent01);
		IPreconcept dS123 = preconceptLattice.getConceptWithExtent(extent123);
		IPreconcept truism = preconceptLattice.getTruism();
		IPreconcept commitment = preconceptLattice.getOntologicalCommitment();
		assertTrue(
				preconceptLattice.isADirectSubordinateOf(dS0, dS01)
				&& preconceptLattice.isADirectSubordinateOf(dS0, dS02)
				&& preconceptLattice.isADirectSubordinateOf(dS1, dS01)
				&& preconceptLattice.isADirectSubordinateOf(dS1, dS123)
				&& preconceptLattice.isADirectSubordinateOf(dS2, dS02)
				&& preconceptLattice.isADirectSubordinateOf(dS2, dS123)
				&& preconceptLattice.isADirectSubordinateOf(dS3, dS123)
				&& preconceptLattice.isADirectSubordinateOf(dS02, truism)
				&& preconceptLattice.isADirectSubordinateOf(dS01, truism)
				&& preconceptLattice.isADirectSubordinateOf(dS123, truism)
				&& preconceptLattice.isADirectSubordinateOf(truism, commitment)
				&& !preconceptLattice.isADirectSubordinateOf(dS0, truism)
				&& !preconceptLattice.isADirectSubordinateOf(dS02, dS0));
	}
	
	@Test
	public void whenIsAMethodCalledThenConsistentReturn() {
		List<IPreconcept> catList = preconceptLattice.getTopologicalSorting();
		for (int i = 0 ; i < catList.size() ; i++) {
			IPreconcept catI = catList.get(i);
			for (int j = 0 ; j < catList.size() ; j++) {
				IPreconcept catJ = catList.get(j);
				if (preconceptLattice.isA(catI, catJ)) {
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
		Set<Set<IPreconcept>> objPowerSet = buildCatsPowerSet(preconceptLattice.getTopologicalSorting()); 
		for (Set<IPreconcept> subset : objPowerSet) {
			if (!subset.isEmpty()) {
				IPreconcept lcs = preconceptLattice.getLeastCommonSuperordinate(subset);
				for (IPreconcept current : preconceptLattice.getTopologicalSorting()) {
					if (preconceptLattice.areA(new ArrayList<>(subset), current)) {
						if (!preconceptLattice.isA(lcs, current) && !lcs.equals(current))
							unexpected = true;
					}
				}
			}
		}
		assertFalse(unexpected);
	}
	
	@Test
	public void whenObjectDenotationSetsRequestedThenReturned() throws Exception {
		Set<IPreconcept> objectDenotationSets = new HashSet<>(preconceptLattice.getObjectConcepts());
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
			preconceptLattice.getOntologicalUpperSemilattice().validate();
		}
		catch (DataFormatException e) {
			isAnUpperSemilattice = false;
		}
		assertTrue(isAnUpperSemilattice);
	}
	
	@Test
	public void whenTreeSuppliedThenReallyIsATree() throws IOException {
		IPreconceptTreeBuilder preconceptTreeBuilder = preconceptLattice.getConceptTreeSupplier();
		int nbOfChecks = 0;
		while (preconceptTreeBuilder.hasNext()) {
			Tree<IPreconcept, IIsA> nextTree = preconceptTreeBuilder.next();
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
		returnedIntentString = preconceptLattice.getTruism().getDenotations()
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
						if (preconceptLattice.isA(iDS, jDS))
							denotSets.remove(iDS);
						else if (preconceptLattice.isA(jDS, iDS))
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
						if (preconceptLattice.isA(iDS, jDS))
							denotSets.remove(jDS);
						else if (preconceptLattice.isA(jDS, iDS))
							denotSets.remove(iDS);
					}
				}
			}
		}
		return denotSets;
	}

}
