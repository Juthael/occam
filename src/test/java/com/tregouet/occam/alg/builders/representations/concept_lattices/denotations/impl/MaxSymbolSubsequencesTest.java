package com.tregouet.occam.alg.builders.representations.concept_lattices.denotations.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.alphabets.impl.Terminal;
import com.tregouet.occam.data.logical_structures.languages.alphabets.impl.Variable;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.impl.Particular;

public class MaxSymbolSubsequencesTest {

	private IContextObject obj1;
	private IContextObject obj2;
	private IContextObject obj3;
	private Set<IContextObject> context = new HashSet<>();
	private Set<IConstruct> returned;
	private Set<IConstruct> expected;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OverallAbstractFactory.INSTANCE.apply(Occam.strategy);
	}

	@Before
	public void setUp() throws Exception {
		
		obj1 = initializeObj1();
		obj2 = initializeObj2();
		obj3 = initializeObj3();
		context.add(obj1);
		context.add(obj2);
		context.add(obj3);
		/*
		System.out.println(obj1 + System.lineSeparator());
		System.out.println(obj2 + System.lineSeparator());
		System.out.println(obj3 + System.lineSeparator());
		*/
	}

	@Test
	public void whenAbstractConstructsRequiredThenExpectedReturned() {
		expected = initializeExpected();
		returned = new MaxSymbolSubsequences().apply(context);
		//var name is random, so they have to be unnamed before equality check;
		Set<List<String>> expectedWithVarPlaceHolders = new HashSet<>();
		Set<List<String>> returnedWithVarPlaceHolders = new HashSet<>();
		for (IConstruct construct : expected)
			expectedWithVarPlaceHolders.add(construct.toListOfStringsWithPlaceholders());
		for (IConstruct construct : returned)
			returnedWithVarPlaceHolders.add(construct.toListOfStringsWithPlaceholders());
		/*
		System.out.println("expected : " + expectedWithVarPlaceHolders.toString());
		System.out.println("returned : " + returnedWithVarPlaceHolders.toString());
		*/
		assertEquals(expectedWithVarPlaceHolders, returnedWithVarPlaceHolders);
	}
	
	private static IContextObject initializeObj1() {
		List<List<String>> constructsAsLists = new ArrayList<>();
		constructsAsLists.add(new ArrayList<>(Arrays.asList(new String[] {"alpha", "beta", "gamma"})));
		constructsAsLists.add(new ArrayList<>(Arrays.asList(new String[] {"alpha", "delta", "epsilon"})));
		return new Particular(constructsAsLists);
	}
	
	private static IContextObject initializeObj2() {
		List<List<String>> constructsAsLists = new ArrayList<>();
		constructsAsLists.add(new ArrayList<>(Arrays.asList(new String[] {"alpha", "beta", "epsilon"})));
		constructsAsLists.add(new ArrayList<>(Arrays.asList(new String[] {"alpha", "delta", "zeta"})));
		return new Particular(constructsAsLists);
	}
	
	private static IContextObject initializeObj3() {
		List<List<String>> constructsAsLists = new ArrayList<>();
		constructsAsLists.add(new ArrayList<>(Arrays.asList(new String[] {"alpha", "beta", "gamma"})));
		constructsAsLists.add(new ArrayList<>(Arrays.asList(new String[] {"alpha", "eta", "epsilon"})));
		return new Particular(constructsAsLists);
	}
	
	private static Set<IConstruct> initializeExpected() {
		Set<IConstruct> expected = new HashSet<>();
		expected.add(stringArray2Construct(new String[] {"alpha", "var"}));
		expected.add(stringArray2Construct(new String[] {"alpha", "beta", "var"}));
		expected.add(stringArray2Construct(new String[] {"alpha", "var", "epsilon"}));
		return expected;
	}
	
	private static IConstruct stringArray2Construct(String[] array) {
		List<ISymbol> symbolList = new ArrayList<>();
		for (String s : array) {
			if (s.equals("var"))
				symbolList.add(new Variable(true));
			else symbolList.add(new Terminal(s));
		}
		return new Construct(symbolList);
	}

}