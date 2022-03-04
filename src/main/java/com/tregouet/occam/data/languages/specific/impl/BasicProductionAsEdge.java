package com.tregouet.occam.data.languages.specific.impl;

import java.util.List;
import java.util.Objects;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;
import com.tregouet.occam.data.languages.specific.IBasicProduction;
import com.tregouet.occam.data.languages.specific.ICompositeProduction;
import com.tregouet.occam.data.languages.specific.IProductionAsEdge;
import com.tregouet.occam.data.languages.specific.IProduction;

public class BasicProductionAsEdge extends DefaultEdge implements IBasicProduction, IProductionAsEdge {

	private static final long serialVersionUID = 1701074226278101143L;
	private final IDenotation speciesDenotation;
	private final IDenotation genusDenotation;
	private final IBasicProduction production;
	
	public BasicProductionAsEdge(IDenotation speciesDenotation, IDenotation genusDenotation, IBasicProduction production) {
		this.speciesDenotation = speciesDenotation;
		this.genusDenotation = genusDenotation;
		this.production = production;
	}

	@Override
	public IConcept getGenusDenotationSet() {
		return genusDenotation.getConcept();
	}

	@Override
	public String getLabel() {
		return toString();
	}

	@Override
	public IDenotation getSource() {
		return speciesDenotation;
	}

	@Override
	public IConcept getSourceConcept() {
		return speciesDenotation.getConcept();
	}

	@Override
	public IConcept getSpeciesDenotationSet() {
		return speciesDenotation.getConcept();
	}

	@Override
	public IDenotation getTarget() {
		return genusDenotation;
	}

	@Override
	public IConcept getTargetConcept() {
		return genusDenotation.getConcept();
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
		if (isEpsilon())
			return "inheritance";
		else return production.toString();
	}

	@Override
	public boolean isEpsilon() {
		return production.isEpsilon();
	}

	@Override
	public int hashCode() {
		return Objects.hash(speciesDenotation, genusDenotation, production);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicProductionAsEdge other = (BasicProductionAsEdge) obj;
		return Objects.equals(speciesDenotation, other.speciesDenotation) && Objects.equals(genusDenotation, other.genusDenotation)
				&& Objects.equals(production, other.production);
	}

}
