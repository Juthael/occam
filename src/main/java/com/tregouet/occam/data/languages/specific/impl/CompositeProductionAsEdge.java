package com.tregouet.occam.data.languages.specific.impl;

import java.util.List;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;
import com.tregouet.occam.data.languages.specific.IBasicProduction;
import com.tregouet.occam.data.languages.specific.ICompositeProduction;
import com.tregouet.occam.data.languages.specific.IProduction;
import com.tregouet.occam.data.languages.specific.IProductionAsEdge;

public class CompositeProductionAsEdge extends DefaultEdge implements ICompositeProduction, IProductionAsEdge {

	private static final long serialVersionUID = 8535238457655336260L;
	
	private final IDenotation input;
	private final IDenotation output;
	private final ICompositeProduction compositeProduction;

	public CompositeProductionAsEdge(IDenotation input, IDenotation output, ICompositeProduction compositeProduction) {
		this.input = input;
		this.output = output;
		this.compositeProduction = compositeProduction;
	}
	
	@Override
	public AVariable getVariable() {
		return compositeProduction.getVariable();
	}

	@Override
	public boolean derives(AVariable var) {
		return compositeProduction.derives(var);
	}

	@Override
	public ILambdaExpression semanticRule() {
		return compositeProduction.semanticRule();
	}

	@Override
	public ILambdaExpression asLambda(List<IProduction> nextProductions) {
		//CAUTION : a composite production must be the last element of a property, so the parameter must be empty
		return compositeProduction.asLambda(nextProductions);
	}

	@Override
	public ICompositeProduction combine(IBasicProduction component) {
		//NOT IMPLEMENTED YET
		return null;
	}

	@Override
	public boolean isEpsilon() {
		return compositeProduction.isEpsilon();
	}

	@Override
	public IConcept getGenusDenotationSet() {
		return input.getConcept();
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDenotation getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IConcept getSourceConcept() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IConcept getSpeciesDenotationSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDenotation getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IConcept getTargetConcept() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IConstruct> getValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILambdaExpression asLambda() {
		// TODO Auto-generated method stub
		return null;
	}

}
