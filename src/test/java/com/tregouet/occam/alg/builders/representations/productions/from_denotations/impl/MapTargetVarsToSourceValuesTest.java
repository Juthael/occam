package com.tregouet.occam.alg.builders.representations.productions.from_denotations.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.alphabets.impl.Terminal;
import com.tregouet.occam.data.logical_structures.languages.alphabets.impl.Variable;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.representations.concepts.denotations.IDenotation;

public class MapTargetVarsToSourceValuesTest {
	
	private IDenotation alphaBetaGamma;
	private IDenotation alphaDeltaEpsilonZeta;
	private IDenotation alphaXGamma;
	private IDenotation alphaXEpsilonX;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	private IConstruct array2Construct(String[] array) {
		List<ISymbol> symbols = new ArrayList<>();
		for (String string : array) {
			if (string.equals("var"))
				symbols.add(new Variable(false));
			else symbols.add(new Terminal(string));
		}
		return new Construct(symbols);
	}

}
