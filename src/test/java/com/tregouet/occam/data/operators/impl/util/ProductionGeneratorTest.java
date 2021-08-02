package com.tregouet.occam.data.operators.impl.util;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.ISymbol;
import com.tregouet.occam.data.constructs.impl.Terminal;
import com.tregouet.occam.data.constructs.impl.Variable;

public class ProductionGeneratorTest {

	private ISymbol a = new Terminal("A");
	private ISymbol b = new Terminal("B");
	private ISymbol c = new Terminal("C");
	private ISymbol d = new Terminal("D");
	private ISymbol i = new Terminal("I");
	private ISymbol j = new Terminal("J");
	private AVariable x = new Variable(false);
	private AVariable y = new Variable(false);
	private AVariable z = new Variable(false);
	private List<ISymbol> acd = Arrays.asList(new ISymbol[] {a, c, d});
	private List<ISymbol> abcd = Arrays.asList(new ISymbol[] {a, b, c, d});
	private List<ISymbol> abijcd = Arrays.asList(new ISymbol[] {a, b, i, j, c, d});
	private List<ISymbol> abcijcd = Arrays.asList(new ISymbol[] {a, b, c, i, j, c, d});
	private List<ISymbol> abicjd = Arrays.asList(new ISymbol[] {a, b, i, c, j, d});
	private List<ISymbol> abX = Arrays.asList(new ISymbol[] {a, b, x});
	private List<ISymbol> abXcd = Arrays.asList(new ISymbol[] {a, b, x, c, d});
	private List<ISymbol> abXcYd = Arrays.asList(new ISymbol[] {a, b, x, c, y, d});
	private List<ISymbol> abiZcd = Arrays.asList(new ISymbol[] {a, b, i, z, c, d});
	private List<ISymbol> aXcYcZ = Arrays.asList(new ISymbol[] {a, x, c, y, c, z});
	private Map<AVariable, List<ISymbol>> abXcd2acd;
	private Map<AVariable, List<ISymbol>> abXcd2acdEXPECTED = null;
	private Map<AVariable, List<ISymbol>> abijcd2abXcd;
	private Map<AVariable, List<ISymbol>> abijcd2abXcdEXPECTED = new HashMap<>();
	private Map<AVariable, List<ISymbol>> abcd2abXcd;
	private Map<AVariable, List<ISymbol>> abcd2abXcdEXPECTED = new HashMap<>();
	private Map<AVariable, List<ISymbol>> abcd2abx;
	private Map<AVariable, List<ISymbol>> abcd2abxEXPECTED = new HashMap<>();
	private Map<AVariable, List<ISymbol>> abcijcd2abXcd;
	private Map<AVariable, List<ISymbol>> abcijcd2abXcdEXPECTED = new HashMap<>();
	private Map<AVariable, List<ISymbol>> abicjd2abXcYd;
	private Map<AVariable, List<ISymbol>> abicjd2abXcYdEXPECTED = new HashMap<>();
	private Map<AVariable, List<ISymbol>> abiZcd2abXcd;
	private Map<AVariable, List<ISymbol>> abiZcd2abXcdEXPECTED = new HashMap<>();
	private Map<AVariable, List<ISymbol>> abcd2X;
	private Map<AVariable, List<ISymbol>> abcd2XEXPECTED = new HashMap<>();
	private Map<AVariable, List<ISymbol>> abcijcd2aXcYcZ;
	private Map<AVariable, List<ISymbol>> abcijcd2aXcYcZEXPECTED = new HashMap<>();


	@Before
	public void setUp() throws Exception {
		abijcd2abXcdEXPECTED.put(x, Arrays.asList(new ISymbol[] {i, j}));
		abcd2abXcdEXPECTED.put(x, Arrays.asList(new ISymbol[0]));
		abcd2abxEXPECTED.put(x, Arrays.asList(new ISymbol[] {c, d}));
		abcijcd2abXcdEXPECTED.put(x, Arrays.asList(new ISymbol[] {c, i, j}));
		abicjd2abXcYdEXPECTED.put(x, Arrays.asList(new ISymbol[] {i}));
		abicjd2abXcYdEXPECTED.put(y, Arrays.asList(new ISymbol[] {j}));
		abiZcd2abXcdEXPECTED.put(x, Arrays.asList(new ISymbol[] {i, z}));
		abcd2XEXPECTED.put(x, Arrays.asList(new ISymbol[] {a, b, c, d}));
		abcijcd2aXcYcZEXPECTED.put(x, Arrays.asList(new ISymbol[] {b}));
		abcijcd2aXcYcZEXPECTED.put(y, Arrays.asList(new ISymbol[] {i, j}));
		abcijcd2aXcYcZEXPECTED.put(z, Arrays.asList(new ISymbol[] {d}));
	}

	@Test
	public void test() {
		abXcd2acd = ProductionGenerator.mapVariablesToValues(abXcd, acd);
		abijcd2abXcd = ProductionGenerator.mapVariablesToValues(abijcd, abXcd);
		abcd2abXcd = ProductionGenerator.mapVariablesToValues(abcd, abXcd);
		abcd2abx = ProductionGenerator.mapVariablesToValues(abcd, abX);
		abcijcd2abXcd = ProductionGenerator.mapVariablesToValues(abcijcd, abXcd);
		abicjd2abXcYd = ProductionGenerator.mapVariablesToValues(abicjd, abXcYd);
		abiZcd2abXcd = ProductionGenerator.mapVariablesToValues(abiZcd, abXcd);
		abcd2X = ProductionGenerator.mapVariablesToValues(abcd, Arrays.asList(new ISymbol[] {x}));
		abcijcd2aXcYcZ = ProductionGenerator.mapVariablesToValues(abcijcd, aXcYcZ);
		
		boolean test1 = abXcd2acd == null;
		boolean test2 = abijcd2abXcd.equals(abijcd2abXcdEXPECTED);
		boolean test3 = abcd2abXcd.equals(abcd2abXcdEXPECTED);
		boolean test4 = abcd2abx.equals(abcd2abxEXPECTED);
		boolean test5 = abcijcd2abXcd.equals(abcijcd2abXcdEXPECTED);
		boolean test6 = abicjd2abXcYd.equals(abicjd2abXcYdEXPECTED);
		boolean test7 = abiZcd2abXcd.equals(abiZcd2abXcdEXPECTED);
		assertTrue(abXcd2acd == null
				&& abijcd2abXcd.equals(abijcd2abXcdEXPECTED)
				&& abcd2abXcd.equals(abcd2abXcdEXPECTED)
				&& abcd2abx.equals(abcd2abxEXPECTED)
				&& abcijcd2abXcd.equals(abcijcd2abXcdEXPECTED)
				&& abicjd2abXcYd.equals(abicjd2abXcYdEXPECTED)
				&& abiZcd2abXcd.equals(abiZcd2abXcdEXPECTED)
				&& abcd2X.equals(abcd2XEXPECTED)
				&& abcijcd2aXcYcZ.equals(abcijcd2aXcYcZEXPECTED));
	}

}
