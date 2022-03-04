package com.tregouet.occam.data.languages.specific.impl;

import java.util.List;
import java.util.Objects;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;
import com.tregouet.occam.data.languages.specific.IBasicProduction;
import com.tregouet.occam.data.languages.specific.ICompositeProduction;
import com.tregouet.occam.data.languages.specific.IProductionAsEdge;
import com.tregouet.occam.data.languages.specific.IProduction;

public class ProductionAsEdge extends DefaultEdge implements IProductionAsEdge {

	private static final long serialVersionUID = 1701074226278101143L;
	private final IDenotation input;
	private final IDenotation output;
	private final IBasicProduction production;
	
	public ProductionAsEdge(IDenotation input, IDenotation output, IBasicProduction production) {
		this.input = input;
		this.output = output;
		this.production = production;
	}

	@Override
	public IDenotationSet getGenusDenotationSet() {
		return output.getDenotationSet();
	}

	@Override
	public String getLabel() {
		return toString();
	}

	@Override
	public IDenotation getSource() {
		return input;
	}

	@Override
	public IDenotationSet getSourceDenotationSet() {
		return input.getDenotationSet();
	}

	@Override
	public IDenotationSet getSpeciesDenotationSet() {
		return input.getDenotationSet();
	}

	@Override
	public IDenotation getTarget() {
		return output;
	}

	@Override
	public IDenotationSet getTargetDenotationSet() {
		return output.getDenotationSet();
	}

	@Override
	public boolean derives(AVariable var) {
		return production.derives(var);
	}

	@Override
	public IConstruct getValue() {
		return production.getValue();
	}

	@Override
	public ICompositeProduction combine(IBasicProduction basicComponent) {
		//No composition for ProductionAsEdge
		return null;
	}

	@Override
	public AVariable getVariable() {
		return production.getVariable();
	}

	@Override
	public ILambdaExpression semanticRule() {
		return production.semanticRule();
	}

	@Override
	public ILambdaExpression asLambda(List<IProduction> nextProductions) {
		return production.asLambda(nextProductions);
	}
	
	@Override
	public String toString() {
		if (isBlank())
			return "inheritance";
		else return production.toString();
	}

	@Override
	public int hashCode() {
		if (isBlank())
			return Objects.hash(input, output);
		return Objects.hash(input, output, production);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductionAsEdge other = (ProductionAsEdge) obj;
		if (this.isBlank())
			return Objects.equals(input, other.input) && Objects.equals(output, other.output)
					&& other.isBlank();
		return Objects.equals(input, other.input) && Objects.equals(output, other.output)
				&& Objects.equals(production, other.production);
	}

}
