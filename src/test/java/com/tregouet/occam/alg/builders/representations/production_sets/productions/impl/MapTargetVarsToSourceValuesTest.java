package com.tregouet.occam.alg.builders.representations.production_sets.productions.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.Occam;
import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.builders.representations.production_sets.productions.impl.MapTargetVarsToSourceValues;
import com.tregouet.occam.data.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.representations.classifications.concepts.denotations.impl.Denotation;
import com.tregouet.occam.data.representations.productions.IContextualizedProduction;
import com.tregouet.occam.data.structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.structures.languages.alphabets.impl.Terminal;
import com.tregouet.occam.data.structures.languages.alphabets.impl.Variable;
import com.tregouet.occam.data.structures.languages.words.construct.impl.Construct;

public class MapTargetVarsToSourceValuesTest {

	private IDenotation alphaBetaGamma;
	private IDenotation alphaDeltaEpsilonZeta;
	private IDenotation alphaXGamma;
	private IDenotation alphaXEpsilonX;

	@Before
	public void setUp() throws Exception {
		alphaBetaGamma = array2Denotation(new String[] {"alpha", "beta", "gamma"}, 0);
		alphaDeltaEpsilonZeta = array2Denotation(new String[] {"alpha", "delta", "epsilon", "zeta"}, 0);
		alphaXGamma = array2Denotation(new String[] {"alpha", "var", "gamma"}, 1);
		alphaXEpsilonX = array2Denotation(new String[] {"alpha", "var", "epsilon", "var"}, 1);
	}

	@Test
	public void whenProductionsRequestedThenExpectedNumberReturned() {
		boolean asExpected = true;
		if (howManyProductionsFor(alphaBetaGamma, alphaDeltaEpsilonZeta) != 0)
			asExpected = false;
		if (howManyProductionsFor(alphaXGamma, alphaBetaGamma) != 0)
			asExpected = false;
		if (howManyProductionsFor(alphaBetaGamma, alphaXGamma) != 1)
			asExpected = false;
		if (howManyProductionsFor(alphaXEpsilonX, alphaDeltaEpsilonZeta) != 0)
			asExpected = false;
		if (howManyProductionsFor(alphaDeltaEpsilonZeta, alphaXEpsilonX) != 2)
			asExpected = false;
		if (howManyProductionsFor(alphaXEpsilonX, alphaBetaGamma) != 0)
			asExpected = false;
		if (howManyProductionsFor(alphaDeltaEpsilonZeta, alphaDeltaEpsilonZeta) != 1)
			asExpected = false;
		assertTrue(asExpected);
	}

	private IDenotation array2Denotation(String[] array, int conceptID) {
		List<ISymbol> symbols = new ArrayList<>();
		for (String string : array) {
			if (string.equals("var"))
				symbols.add(new Variable(false));
			else symbols.add(new Terminal(string));
		}
		return new Denotation(new Construct(symbols), conceptID);
	}

	private int howManyProductionsFor(IDenotation source, IDenotation target) {
		MapTargetVarsToSourceValues prodBuilder = new MapTargetVarsToSourceValues();
		Set<IContextualizedProduction> productions = prodBuilder.apply(source, target);
		/*
		System.out.println(report(source, target, productions));
		*/
		return productions.size();
	}

	@SuppressWarnings("unused")
	private String report(IDenotation source, IDenotation target, Set<IContextualizedProduction> result) {
		String nL = System.lineSeparator();
		if (result.isEmpty())
			return "No set of productions can generate [" + source.toString() + "]" + nL +
					"out of [" + target.toString() + "]." + nL;
		else {
			StringBuilder sB = new StringBuilder();
			sB.append("[" + source.toString() + "] can be generated out of [" + target.toString() + "]"
				+ nL + "by application of the following productions : " + nL);
			Iterator<IContextualizedProduction> prodIte = result.iterator();
			while (prodIte.hasNext()) {
				sB.append("   " + prodIte.next().toString());
				if (prodIte.hasNext())
					sB.append(nL);
			}
			sB.append(nL);
			return sB.toString();
		}
	}

	@BeforeClass
	public static void setUpBeforeClass() {
		Occam.initialize();
		OverallAbstractFactory.INSTANCE.apply(Occam.STRATEGY);
	}

}
