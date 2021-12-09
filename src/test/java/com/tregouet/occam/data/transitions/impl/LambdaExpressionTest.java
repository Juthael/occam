package com.tregouet.occam.data.transitions.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.ISymbol;
import com.tregouet.occam.data.constructs.ITerminal;
import com.tregouet.occam.data.constructs.impl.Construct;
import com.tregouet.occam.data.constructs.impl.Terminal;
import com.tregouet.occam.data.constructs.impl.Variable;
import com.tregouet.occam.data.lambdas.ILambdaExpression;
import com.tregouet.occam.data.lambdas.impl.LambdaExpression;

public class LambdaExpressionTest {

	private ITerminal pluralitas = new Terminal("pluralitas");
	private ITerminal non = new Terminal("non");
	private ITerminal est = new Terminal("est");
	private ITerminal ponenda = new Terminal("ponenda");
	private ITerminal sine = new Terminal("sine");
	private ITerminal necessitate = new Terminal("necessitate");
	private AVariable var1 = new Variable(false);
	private AVariable var2 = new Variable(false);
	private AVariable var3 = new Variable(false);
	private String var1Name = var1.toString();
	private String var2Name = var2.toString();
	private String var3Name = var3.toString();
	private List<ISymbol> concreteProg = 
			new ArrayList<>(Arrays.asList(new ISymbol[] {pluralitas, non, est, ponenda, sine, necessitate}));
	private List<ISymbol> abstractProg = 
			new ArrayList<>(Arrays.asList(new ISymbol[] {pluralitas, var1, est, var2, sine, var3}));
	private IConstruct concreteConstr = new Construct(concreteProg);
	private IConstruct abstractConstr = new Construct(abstractProg);
	private ILambdaExpression concreteExp = new LambdaExpression(concreteConstr);
	private ILambdaExpression abstractExp = new LambdaExpression(abstractConstr);
	
	@Test
	public void whenToStringThenAsExpected() {		
		String expectedConcreteExp = "pluralitas non est ponenda sine necessitate";
		String expectedAbstExp = setExpectedAbstExp();
		/*
		System.out.println("expected concrete : " + expectedConcreteExp);
		System.out.println("returned concrete : " + concreteExp.toString());
		System.out.println("expected abstract : " + expectedAbstExp);
		System.out.println("returned abstract : " + abstractExp.toString());
		*/
		assertTrue(concreteExp.toString().equals(expectedConcreteExp) 
				&& abstractExp.toString().equals(expectedAbstExp));
	}
	
	private String setExpectedAbstExp() {
		StringBuilder sB = new StringBuilder();
		sB.append("(λ");
		sB.append(var1Name + var2Name + var3Name);
		sB.append(".pluralitas " + var1Name + " est " + var2Name + " sine " + var3Name + ")");
		sB.append(" (" + var1Name + ".deriv)");
		sB.append(" (" + var2Name + ".deriv)");
		sB.append(" (" + var3Name + ".deriv)");
		return sB.toString();
	}

}
