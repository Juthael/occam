package com.tregouet.occam.alg.builders.representations.productions.impl;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.representations.productions.impl.IfIsAThenBuildProductions;
import com.tregouet.occam.alg.displayers.graph_visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;
import com.tregouet.occam.io.input.impl.GenericFileReader;

@SuppressWarnings("unused")
public class IfIsAThenBuildProductionsTest {
	
	private static final Path SHAPES6 = Paths.get(".", "src", "test", "java", "files", "shapes6.txt");
	@SuppressWarnings("unused")
	private static final String nL = System.lineSeparator();
	private List<IContextObject> context;
	private IConceptLattice conceptLattice;	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}
	
	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES6);
		conceptLattice = GeneratorsAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
	}	

	@Test
	public void whenSubordinationRelationExistsBtw2ConceptsThenProductionsFromOneToOtherCanBeFound() 
			throws Exception {
		boolean asExpected = true;
		Set<IContextualizedProduction> productions = IfIsAThenBuildProductions.INSTANCE.apply(conceptLattice);
		DirectedAcyclicGraph<IConcept, IIsA> upperSemiLattice = conceptLattice.getOntologicalUpperSemilattice();
		/*
		String latticeGraphPath = 
				VisualizersAbstractFactory.INSTANCE.getConceptGraphViz().apply(
						upperSemiLattice, "IfIsAThenBuildProductionTest_lattice");
		System.out.println(nL + nL + "The lattice graph is available at " + latticeGraphPath + nL);
		*/
		for (IConcept concept : upperSemiLattice) {
			Set<IConcept> subordinates = upperSemiLattice.getAncestors(concept);
			for (IConcept subordinate : subordinates) {
				Set<IContextualizedProduction> linkingProductions = productions.stream()
						.filter(p -> (p.getSpeciesID() == subordinate.iD() && p.getGenusID() == concept.iD()))
						.collect(Collectors.toSet());
				/*
				System.out.println(report(concept, subordinate, linkingProductions));
				*/
				if (linkingProductions.isEmpty())
					asExpected = false;
			}
		}
		assertTrue(asExpected);
	}
	
	@SuppressWarnings("unused")
	private String report(IConcept concept, IConcept subordinate, Set<IContextualizedProduction> productions) {
		StringBuilder sB = new StringBuilder();
		sB.append("***Productions from concept " + Integer.toString(concept.iD()) + " to concept " 
				+ Integer.toString(subordinate.iD()) + "***" + nL + nL)
			.append("**Concept " + Integer.toString(concept.iD()) + " denotations : " + nL)
			.append(concept.getDenotations().toString() + nL + nL)
			.append("**Concept " + Integer.toString(subordinate.iD()) + " denotations : " + nL)
			.append(subordinate.getDenotations().toString() + nL + nL);
		if (productions.isEmpty())
			sB.append("ERROR : no production has been found." + nL + nL);
		else {
			sB.append("**Productions found : " + nL);
			for (IContextualizedProduction production : productions) {
				sB.append(production.toString() + nL);
			}
			sB.append(nL);
		}
		return sB.toString();
	}

}
