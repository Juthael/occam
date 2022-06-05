package com.tregouet.occam.data.problem_space.states.productions.impl;

import java.util.Objects;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;

public class ContextualizedProd extends DefaultEdge implements IContextualizedProduction {

	private static final long serialVersionUID = 1701074226278101143L;

	private final IDenotation speciesDenotation;
	private final IDenotation genusDenotation;
	private final IProduction production;

	public ContextualizedProd(IDenotation speciesDenotation, IDenotation genusDenotation, IProduction production) {
		this.speciesDenotation = speciesDenotation;
		this.genusDenotation = genusDenotation;
		this.production = production;
	}

	@Override
	public boolean derives(AVariable var) {
		return production.derives(var);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		ContextualizedProd other = (ContextualizedProd) obj;
		return Objects.equals(speciesDenotation, other.speciesDenotation)
				&& Objects.equals(genusDenotation, other.genusDenotation)
				&& Objects.equals(production, other.production);
	}

	@Override
	public int getSuperordinateID() {
		return genusDenotation.getConceptID();
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
	public int getSubordinateID() {
		return speciesDenotation.getConceptID();
	}

	@Override
	public IDenotation getTarget() {
		return genusDenotation;
	}

	@Override
	public IProduction getUncontextualizedProduction() {
		return production;
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
	public int hashCode() {
		return Objects.hash(speciesDenotation, genusDenotation, production);
	}

	@Override
	public boolean isEpsilon() {
		return production.isEpsilon();
	}

	@Override
	public boolean isRedundant() {
		return getSource().isRedundant();
	}

	@Override
	public String toString() {
		if (isEpsilon())
			return "ε";
		else
			return production.toString();
	}

	@Override
	public boolean isBlank() {
		return production.isBlank();
	}

	@Override
	public boolean isAlphaConversion() {
		return production.isAlphaConversion();
	}

}
