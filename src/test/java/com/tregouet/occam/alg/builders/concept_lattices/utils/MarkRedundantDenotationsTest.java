package com.tregouet.occam.alg.builders.concept_lattices.utils;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class MarkRedundantDenotationsTest {

	private static final Path SHAPES2 = Paths.get(".", "src", "test", "java", "files", "shapes2.txt");
	@SuppressWarnings("unused")
	private static final String nL = System.lineSeparator();
	private List<IContextObject> context;
	private IConceptLattice lattice;

	@Before
	public void setUp() throws Exception {
		context = GenericFileReader.getContextObjects(SHAPES2);
		lattice = BuildersAbstractFactory.INSTANCE.getConceptLatticeBuilder().apply(context);
	}

	@Test
	public void whenDenotationIsMarkedAsRedundantThenIsUpperBoundOfAnotherInTheSameConcept() {
		boolean ifRedundantThenUpperBound = true;
		for (IConcept concept : lattice.getLatticeOfConcepts()) {
			/*
			System.out.println("Concept n." + Integer.toString(concept.iD()) + nL);
			*/
			for (IDenotation denotation : concept.getDenotations()) {
				if (denotation.isRedundant()) {
					/*
					System.out.println("The denotation [" + denotation.toString() + "] is redundant...");
					*/
					boolean upperBound = false;
					for (IDenotation compared : concept.getDenotations()) {
						Integer comparison = denotation.compareTo(compared);
						if (comparison != null && comparison > 0) {
							upperBound = true;
							/*
							System.out.println("... because it accepts [" + compared.toString() + "] as "
									+ "a lower bound in the instantiation order. " + nL);
							*/
						}
					}
					if (!upperBound) {
						/*
						System.out.println("... but accepts no other denotation as lower bound in the "
								+ "instantiation order." + nL);
						*/
						ifRedundantThenUpperBound = false;
					}
				}
			}
		}
		assertTrue(ifRedundantThenUpperBound);
	}

	@Test
	public void whenDenotationIsUpperBoundOfAnotherInInstantiationOrderThenIsMarkedAsRedundant() {
		boolean ifUpperBoundThenRedundant = true;
		for (IConcept concept : lattice.getLatticeOfConcepts()) {
			/*
			System.out.println("Concept N." + Integer.toString(concept.iD()) + nL);
			*/
			List<IDenotation> denotations = new ArrayList<>(concept.getDenotations());
			for (int i = 0 ; i < denotations.size() ; i++) {
				IDenotation iDenot = denotations.get(i);
				for (int j = 0 ; j < denotations.size() ; j++) {
					if (i != j) {
						IDenotation jDenot = denotations.get(j);
						Integer comparison = iDenot.compareTo(jDenot);
						if (comparison != null && comparison > 0) {
							/*
							System.out.println("The denotation " + nL + "   [" + iDenot.toString() + "]");
							System.out.println("is redundant since it can be transformed into the denotation");
							System.out.println("    [" + jDenot.toString() + "]");
							System.out.println("by instantiation of its variable(s)." + nL);
							*/
							if (!iDenot.isRedundant())
								ifUpperBoundThenRedundant = false;
						}
					}
				}
			}
		}
		assertTrue(ifUpperBoundThenRedundant);
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Occam.initialize();
		OverallAbstractFactory.INSTANCE.apply(Occam.STRATEGY);
	}

}
