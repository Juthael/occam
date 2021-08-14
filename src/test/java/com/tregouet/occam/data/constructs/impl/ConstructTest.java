package com.tregouet.occam.data.constructs.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.ITerminal;
import com.tregouet.subseq_finder.ISymbolSeq;

public class ConstructTest {

	private String[] concreteProg1 = new String[] {"a", "b", "c", "d"};
	private String[] concreteProg2 = new String[] {"a", "b", "c", "d"};
	private String[] concreteProgPartial = new String[] {"a", "b", "c"};
	private String[] concreteProgWrongOrder = new String[] {"a", "b", "d", "c"};
	private String[] abstractProg = new String[] {"a", "b", "_", "d", "_"};
	private String[] metConstraintProg = new String[] {"b", "c"};
	private String[] notMetConstraintProg1 = new String[] {"a", "b", "c", "d", "e"};
	private String[] notMetConstraintProg2 = new String[] {"b", "e"};
	private String[] notMetConstraintProg3 = new String[] {"c", "b"};
	
	@Test
	public void onlyWhenContainsVariablesThenAbstract() {
		IConstruct construct = new Construct(concreteProg1);
		IConstruct abstractConstruct = new Construct(abstractProg);
		assertTrue(!construct.isAbstract() && abstractConstruct.isAbstract());
	}
	
	@Test
	public void onlyWhenOtherConstructWithSameListOfSymbolsThenEquals() {
		IConstruct construct = new Construct(concreteProg1);
		IConstruct equalConstruct = new Construct(concreteProg2);
		IConstruct partialConstruct = new Construct(concreteProgPartial);
		IConstruct wronglyOrderedConstruct = new Construct(concreteProgWrongOrder);
		IConstruct abstractConstruct = new Construct(abstractProg);
		assertTrue(construct.equals(equalConstruct)
					&& !construct.equals(partialConstruct)
					&& !construct.equals(wronglyOrderedConstruct)
					&& !construct.equals(abstractConstruct));		
	}
	
	@Test
	public void whenListOfTerminalsRequestedThenReturned() {
		IConstruct abstractConstruct = new Construct(abstractProg);
		List<ITerminal> expectedList = new ArrayList<>();
		expectedList.add(new Terminal("a"));
		expectedList.add(new Terminal("b"));
		expectedList.add(new Terminal("d"));
		assertTrue(abstractConstruct.getListOfTerminals().equals(expectedList));
	}
	
	@Test
	public void whenSpecifiedConstraintIsMetThenIsASubsequenceOfConstraintsListOfSymbols() {
		IConstruct construct = new Construct(concreteProg1);
		IConstruct metConstraint = new Construct(metConstraintProg);
		IConstruct notMetConstraint1 = new Construct(notMetConstraintProg1);
		IConstruct notMetConstraint2 = new Construct(notMetConstraintProg2);
		IConstruct notMetConstraint3 = new Construct(notMetConstraintProg3);
		assertTrue(construct.meets(metConstraint)
				&& !construct.meets(notMetConstraint1)
				&& !construct.meets(notMetConstraint2)
				&& !construct.meets(notMetConstraint3));
	}
	
	@Test
	public void whenVariableNamingRequestedThenDone() {
		boolean beforeNamingThenPlaceholder;
		boolean afterNamingThenEachHasAName;
		IConstruct abstractConstruct = new Construct(abstractProg);
		Set<String> variablesNamesBeforeNaming = abstractConstruct.getListOfSymbols()
				.stream()
				.filter(s -> s instanceof AVariable)
				.map(s -> s.toString())
				.collect(Collectors.toSet());
		abstractConstruct.nameVariables();
		Set<String> variablesNamesAfterNaming = abstractConstruct.getListOfSymbols()
				.stream()
				.filter(s -> s instanceof AVariable)
				.map(s -> s.toString())
				.collect(Collectors.toSet());
		beforeNamingThenPlaceholder = ((variablesNamesBeforeNaming.size() == 1)
				&& variablesNamesBeforeNaming.iterator().next().equals(ISymbolSeq.PLACEHOLDER));
		afterNamingThenEachHasAName = (variablesNamesAfterNaming.size() == 2);
		assertTrue(beforeNamingThenPlaceholder && afterNamingThenEachHasAName);
	}

}
