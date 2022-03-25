package com.tregouet.occam.data.languages.specific.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tregouet.occam.data.languages.alphabets.ISymbol;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.languages.alphabets.generic.ITerminal;
import com.tregouet.occam.data.languages.alphabets.generic.impl.Terminal;
import com.tregouet.occam.data.languages.alphabets.generic.impl.Variable;
import com.tregouet.occam.data.languages.words.construct.IConstruct;
import com.tregouet.occam.data.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.languages.words.fact.ISimpleEdgeProduction;
import com.tregouet.occam.data.languages.words.fact.IStronglyContextualized;
import com.tregouet.occam.data.languages.words.fact.impl.SimpleEdgeProduction;
import com.tregouet.occam.data.languages.words.lambda.ILambdaExpression;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IDenotation;
import com.tregouet.occam.data.representations.concepts.impl.Concept;
import com.tregouet.occam.data.representations.concepts.impl.Denotation;

public class EdgeProductionTest {

	private ITerminal pluralitas = new Terminal("pluralitas");
	private ITerminal non = new Terminal("non");
	private ITerminal est = new Terminal("est");
	private ITerminal ponenda = new Terminal("ponenda");
	private ITerminal sine = new Terminal("sine");
	private ITerminal necessitate = new Terminal("necessitate");
	private AVariable var1 = new Variable(false);
	private AVariable var2 = new Variable(false);
	private AVariable var3 = new Variable(false);
	private AVariable var4 = new Variable(false);
	private AVariable var5 = new Variable(false);
	private List<ISymbol> prog1 = 
			new ArrayList<>(Arrays.asList(new ISymbol[] {pluralitas, non, est, ponenda, sine, necessitate}));
	private List<ISymbol> prog2 = 
			new ArrayList<>(Arrays.asList(new ISymbol[] {pluralitas, var1, est, var2, sine, var3}));
	private List<ISymbol> prog3 = 
			new ArrayList<>(Arrays.asList(new ISymbol[] {pluralitas, var4}));
	private List<ISymbol> prog4 = 
			new ArrayList<>(Arrays.asList(new ISymbol[] {var5}));
	private List<ISymbol> var1ValueProg = 
			new ArrayList<>(Arrays.asList(new ISymbol[] {non}));
	private List<ISymbol> var2ValueProg = 
			new ArrayList<>(Arrays.asList(new ISymbol[] {ponenda}));
	private List<ISymbol> var3ValueProg = 
			new ArrayList<>(Arrays.asList(new ISymbol[] {necessitate}));
	private List<ISymbol> var4ValueProg = 
			new ArrayList<>(Arrays.asList(new ISymbol[] {var1, est, var2, sine, var3}));
	private List<ISymbol> var5ValueProg = 
			new ArrayList<>(Arrays.asList(new ISymbol[] {pluralitas, var4}));
	private Set<IConstruct> mockIntent1 = new HashSet<>();
	private Set<IConstruct> mockIntent2 = new HashSet<>();
	private Set<IConstruct> mockIntent3 = new HashSet<>();
	private Set<IConstruct> mockIntent4 = new HashSet<>();
	private Set<IContextObject> mockExtent1 = new HashSet<>();
	private Set<IContextObject> mockExtent2 = new HashSet<>();
	private Set<IContextObject> mockExtent3 = new HashSet<>();
	private Set<IContextObject> mockExtent4 = new HashSet<>();
	private IDenotation constr1 = new Denotation(new Construct(prog1), new Concept(mockIntent1, mockExtent1));
	private IDenotation constr2 = new Denotation(new Construct(prog2), new Concept(mockIntent2, mockExtent2));
	private IDenotation constr3 = new Denotation(new Construct(prog3), new Concept(mockIntent3, mockExtent3));
	private IDenotation constr4 = new Denotation(new Construct(prog4), new Concept(mockIntent4, mockExtent4));
	private IConstruct var1Value = new Construct(var1ValueProg);
	private IConstruct var2Value = new Construct(var2ValueProg);
	private IConstruct var3Value = new Construct(var3ValueProg);
	private IConstruct var4Value = new Construct(var4ValueProg);
	private IConstruct var5Value = new Construct(var5ValueProg);
	private ISimpleEdgeProduction prodVar1 = new SimpleEdgeProduction(var1, var1Value, constr1, constr2);
	private ISimpleEdgeProduction prodVar2 = new SimpleEdgeProduction(var2, var2Value, constr1, constr2);
	private ISimpleEdgeProduction prodVar3 = new SimpleEdgeProduction(var3, var3Value, constr1, constr2);
	private ISimpleEdgeProduction prodVar4 = new SimpleEdgeProduction(var4, var4Value, constr2, constr3);
	private ISimpleEdgeProduction prodVar5 = new SimpleEdgeProduction(var5, var5Value, constr3, constr4);
	private List<ISimpleEdgeProduction> productions = 
			new ArrayList<>(Arrays.asList(new ISimpleEdgeProduction[] {prodVar1, prodVar2, prodVar3, prodVar4, prodVar5}));
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void whenLambdaExpressionRequestedThenExpectedReturned() {
		StringBuilder sB = new StringBuilder();
		sB.append("(λ");
		sB.append(var4.toString());
		sB.append(".pluralitas ");
		sB.append(var4.toString());
		sB.append(") ((λ");
		sB.append(var1.toString());
		sB.append(var2.toString());
		sB.append(var3.toString());
		sB.append(".");
		sB.append(var1.toString());
		sB.append(" est ");
		sB.append(var2.toString());
		sB.append(" sine ");
		sB.append(var3.toString());
		sB.append(") non ponenda necessitate)");
		ILambdaExpression prod5Lambda;
		List<IStronglyContextualized> remainingProds = new ArrayList<>(productions);
		remainingProds.remove(prodVar5);
		prod5Lambda = prodVar5.asLambda(remainingProds);
		String prod5LambdaString = prod5Lambda.toString();
		String expected = sB.toString();
		/*
		System.out.println(prod5LambdaString);
		System.out.println(expected);
		*/
		assertTrue(prod5LambdaString.equals(expected));
	}

}

