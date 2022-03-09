package com.tregouet.occam.data.alphabets.productions.impl;

import java.util.List;
import java.util.Objects;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.alphabets.productions.Input;
import com.tregouet.occam.data.alphabets.productions.IProduction;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public class ContextualizedProduction extends DefaultEdge implements Input {

	private static final long serialVersionUID = 1701074226278101143L;
	private final IDenotation speciesDenotation;
	private final IDenotation genusDenotation;
	private final IProduction production;
	
	public ContextualizedProduction(IDenotation speciesDenotation, IDenotation genusDenotation, IProduction production) {
		this.speciesDenotation = speciesDenotation;
		this.genusDenotation = genusDenotation;
		this.production = production;
	}

	@Override
	public IPreconcept getGenus() {
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
	public IPreconcept getSpecies() {
		return speciesDenotation.getConcept();
	}

	@Override
	public IDenotation getTarget() {
		return genusDenotation;
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
		ContextualizedProduction other = (ContextualizedProduction) obj;
		return Objects.equals(speciesDenotation, other.speciesDenotation) && Objects.equals(genusDenotation, other.genusDenotation)
				&& Objects.equals(production, other.production);
	}

	@Override
	public IProduction getUncontextualizedProduction() {
		return production;
	}

}
