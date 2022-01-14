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

import com.tregouet.occam.alg.denotation_sets_gen.IDenotationSetsTreeSupplier;
import com.tregouet.occam.alg.scoring.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.scoring.ScoringStrategy;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotationSets;
import com.tregouet.occam.data.denotations.IContextObject;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.denotations.impl.DenotationSets;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.io.input.impl.GenericFileReader;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.StructureInspector;

@SuppressWarnings("unused")
public class DenotationSetsTest {

	private static Path shapes2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	private static List<IContextObject> shapes2Obj;
	private static IDenotationSets denotationSets;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shapes2Obj = GenericFileReader.getContextObjects(shapes2);
		CalculatorsAbstractFactory.INSTANCE.setUpStrategy(ScoringStrategy.SCORING_STRATEGY_1);
	}

	@Test
	public void whenDenotationSetLatticeReturnedThenReallyIsALattice() {
		DirectedAcyclicGraph<IDenotationSet, IIsA> lattice = denotationSets.getLatticeOfDenotationSets();
		assertTrue(StructureInspector.isAnUpperSemilattice(lattice) 
				&& StructureInspector.isALowerSemilattice(lattice)
				&& !lattice.vertexSet().isEmpty());
	}
	
	@Before
	public void setUp() throws Exception {
		denotationSets = new DenotationSets(shapes2Obj);
	}	

	@Test
	public void whenDenotationSetsReturnedThenContains1Absurdity1Truism1Commitment() {
		int nbOfTruism = 0;
		int nbOfCommitments = 0;
		for (IDenotationSet cat : denotationSets.getTopologicalSorting()) {
			if (cat.type() == IDenotationSet.TRUISM)
				nbOfTruism++;
			else if (cat.type() == IDenotationSet.ONTOLOGICAL_COMMITMENT)
				nbOfCommitments++;
		}
		assertTrue(nbOfTruism == 1 && nbOfCommitments == 1);
	}
	

	@Test
	public void whenDenotationSetsReturnedThenEachHasADistinctIntent() {
		Set<Set<IDenotation>> setsOfDenotations = new HashSet<>();
		for (IDenotationSet cat : denotationSets.getTopologicalSorting())
			setsOfDenotations.add(cat.getDenotations());
		assertTrue(setsOfDenotations.size() == denotationSets.getTopologicalSorting().size());
	}	
	
	@Test
	public void whenDenotationSetsReturnedThenTruismAndCommitmentHaveSameExtent() {
		Set<Set<IContextObject>> extents = new HashSet<>();
		extents.add(denotationSets.getTruism().getExtent());
		extents.add(denotationSets.getOntologicalCommitment().getExtent());
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
		IDenotationSet absurdity = denotationSets.getAbsurdity();
		IDenotationSet dS0 = denotationSets.getDenotationSetWithExtent(extent0);
		IDenotationSet dS1 = denotationSets.getDenotationSetWithExtent(extent1);
		IDenotationSet dS2 = denotationSets.getDenotationSetWithExtent(extent2);
		IDenotationSet dS3 = denotationSets.getDenotationSetWithExtent(extent3);
		IDenotationSet dS02 = denotationSets.getDenotationSetWithExtent(extent02);
		IDenotationSet dS01 = denotationSets.getDenotationSetWithExtent(extent01);
		IDenotationSet dS123 = denotationSets.getDenotationSetWithExtent(extent123);
		IDenotationSet truism = denotationSets.getTruism();
		IDenotationSet commitment = denotationSets.getOntologicalCommitment();
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
		IDenotationSetsTreeSupplier denotationSetsTreeSupplier = null;
		try {
			denotationSetsTreeSupplier = denotationSets.getDenotationSetsTreeSupplier();
		}
		catch (Exception e) {
			assertTrue(false);
		}
		assertNotNull(denotationSetsTreeSupplier);
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
		IDenotationSet dS0 = denotationSets.getDenotationSetWithExtent(extent0);
		IDenotationSet dS1 = denotationSets.getDenotationSetWithExtent(extent1);
		IDenotationSet dS2 = denotationSets.getDenotationSetWithExtent(extent2);
		IDenotationSet dS3 = denotationSets.getDenotationSetWithExtent(extent3);
		IDenotationSet dS02 = denotationSets.getDenotationSetWithExtent(extent02);
		IDenotationSet dS01 = denotationSets.getDenotationSetWithExtent(extent01);
		IDenotationSet dS123 = denotationSets.getDenotationSetWithExtent(extent123);
		IDenotationSet truism = denotationSets.getTruism();
		IDenotationSet commitment = denotationSets.getOntologicalCommitment();
		assertTrue(
				denotationSets.isADirectSubordinateOf(dS0, dS01)
				&& denotationSets.isADirectSubordinateOf(dS0, dS02)
				&& denotationSets.isADirectSubordinateOf(dS1, dS01)
				&& denotationSets.isADirectSubordinateOf(dS1, dS123)
				&& denotationSets.isADirectSubordinateOf(dS2, dS02)
				&& denotationSets.isADirectSubordinateOf(dS2, dS123)
				&& denotationSets.isADirectSubordinateOf(dS3, dS123)
				&& denotationSets.isADirectSubordinateOf(dS02, truism)
				&& denotationSets.isADirectSubordinateOf(dS01, truism)
				&& denotationSets.isADirectSubordinateOf(dS123, truism)
				&& denotationSets.isADirectSubordinateOf(truism, commitment)
				&& !denotationSets.isADirectSubordinateOf(dS0, truism)
				&& !denotationSets.isADirectSubordinateOf(dS02, dS0));
	}
	
	@Test
	public void whenIsAMethodCalledThenConsistentReturn() {
		List<IDenotationSet> catList = denotationSets.getTopologicalSorting();
		for (int i = 0 ; i < catList.size() ; i++) {
			IDenotationSet catI = catList.get(i);
			for (int j = 0 ; j < catList.size() ; j++) {
				IDenotationSet catJ = catList.get(j);
				if (denotationSets.isA(catI, catJ)) {
					if (!catJ.getExtent().containsAll(catI.getExtent())
							&& catI.type() != IDenotationSet.TRUISM
							&& catJ.type() != IDenotationSet.ONTOLOGICAL_COMMITMENT)
							assertTrue(false);
				}
			}
		}
		assertTrue(true);
	}
	
	@Test
	public void whenLeastCommonSuperordinateRequiredThenExpectedReturned() {
		boolean unexpected = false;
		Set<Set<IDenotationSet>> objPowerSet = buildCatsPowerSet(denotationSets.getTopologicalSorting()); 
		for (Set<IDenotationSet> subset : objPowerSet) {
			if (!subset.isEmpty()) {
				IDenotationSet lcs = denotationSets.getLeastCommonSuperordinate(subset);
				for (IDenotationSet current : denotationSets.getTopologicalSorting()) {
					if (denotationSets.areA(new ArrayList<>(subset), current)) {
						if (!denotationSets.isA(lcs, current) && !lcs.equals(current))
							unexpected = true;
					}
				}
			}
		}
		assertFalse(unexpected);
	}
	
	@Test
	public void whenObjectDenotationSetsRequestedThenReturned() throws Exception {
		Set<IDenotationSet> objectDenotationSets = new HashSet<>(denotationSets.getObjectDenotationSets());
		Set<Set<IConstruct>> expectedSetsOfConstructs = new HashSet<>();
		Set<Set<IConstruct>> objSetsOfDenotatingConstructs = new HashSet<>();
		for (IContextObject obj : shapes2Obj)
			expectedSetsOfConstructs.add(new HashSet<>(obj.getConstructs()));
		for (IDenotationSet objDenotationSet : objectDenotationSets) {
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
			denotationSets.getOntologicalUpperSemilattice().validate();
		}
		catch (DataFormatException e) {
			isAnUpperSemilattice = false;
		}
		assertTrue(isAnUpperSemilattice);
	}
	
	@Test
	public void whenTreeSuppliedThenReallyIsATree() throws IOException {
		IDenotationSetsTreeSupplier denotationSetsTreeSupplier = denotationSets.getDenotationSetsTreeSupplier();
		int nbOfChecks = 0;
		while (denotationSetsTreeSupplier.hasNext()) {
			Tree<IDenotationSet, IIsA> nextTree = denotationSetsTreeSupplier.next();
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
		returnedIntentString = denotationSets.getTruism().getDenotations()
				.stream()
				.map(c -> c.toListOfStringsWithPlaceholders())
				.collect(Collectors.toSet());
		unexpectedReturnedIntentString = new HashSet<>(returnedIntentString);
		unexpectedReturnedIntentString.removeAll(expectedIntentString);
		assertTrue(expectedIntentString.size() == returnedIntentString.size() 
				&& unexpectedReturnedIntentString.isEmpty());
	}
	
	private Set<Set<IDenotationSet>> buildCatsPowerSet(List<IDenotationSet> objects) {
	    Set<Set<IDenotationSet>> powerSet = new HashSet<Set<IDenotationSet>>();
	    for (int i = 0; i < (1 << objects.size()); i++) {
	    	Set<IDenotationSet> subset = new HashSet<IDenotationSet>();
	        for (int j = 0; j < objects.size(); j++) {
	            if(((1 << j) & i) > 0)
	            	subset.add(objects.get(j));
	        }
	        powerSet.add(subset);
	    }
	    return powerSet;
	}
	
	private Set<IDenotationSet> removeNonMaximalElements(Set<IDenotationSet> denotSets){
		List<IDenotationSet> dSList = new ArrayList<>(denotSets);
		for (int i = 0 ; i < dSList.size() - 1 ; i++) {
			IDenotationSet iDS = dSList.get(i);
			if (denotSets.contains(iDS)) {
				for (int j = i+1 ; j < dSList.size() ; j++) {
					IDenotationSet jDS = dSList.get(j);
					if (denotSets.contains(jDS)) {
						if (denotationSets.isA(iDS, jDS))
							denotSets.remove(iDS);
						else if (denotationSets.isA(jDS, iDS))
							denotSets.remove(jDS);
					}
				}
			}
		}
		return denotSets;
	}	
	
	private Set<IDenotationSet> removeNonMinimalElements(Set<IDenotationSet> denotSets){
		List<IDenotationSet> dSList = new ArrayList<>(denotSets);
		for (int i = 0 ; i < dSList.size() - 1 ; i++) {
			IDenotationSet iDS = dSList.get(i);
			if (denotSets.contains(iDS)) {
				for (int j = i+1 ; j < dSList.size() ; j++) {
					IDenotationSet jDS = dSList.get(j);
					if (denotSets.contains(jDS)) {
						if (denotationSets.isA(iDS, jDS))
							denotSets.remove(jDS);
						else if (denotationSets.isA(jDS, iDS))
							denotSets.remove(iDS);
					}
				}
			}
		}
		return denotSets;
	}

}
