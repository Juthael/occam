package com.tregouet.occam.data.structures.representations.productions.impl;

import java.util.Objects;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.structures.representations.productions.IBasicProduction;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;
import com.tregouet.occam.data.structures.representations.productions.Salience;

public class ContextualizedProd extends DefaultEdge implements IContextualizedProduction {

	private static final long serialVersionUID = 1701074226278101143L;

	private final IDenotation speciesDenotation;
	private final IDenotation genusDenotation;
	private final IBasicProduction production;

	public ContextualizedProd(IDenotation speciesDenotation, IDenotation genusDenotation, IBasicProduction production) {
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
	public String getLabel() {
		return toString();
	}

	@Override
	public Salience getSalience() {
		return production.getSalience();
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
	public int getSuperordinateID() {
		return genusDenotation.getConceptID();
	}

	@Override
	public IDenotation getTarget() {
		return genusDenotation;
	}

	@Override
	public IBasicProduction getUncontextualizedProduction() {
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
	public boolean isAlphaConversionProd() {
		return production.isAlphaConversionProd();
	}

	@Override
	public boolean isEpsilon() {
		return production.isEpsilon();
	}

	@Override
	public boolean isIdentityProd() {
		return production.isIdentityProd();
	}

	@Override
	public boolean isRedundant() {
		return getSource().isRedundant();
	}

	@Override
	public void setSalience(Salience salience) {
		production.setSalience(salience);
	}

	@Override
	public String toString() {
		if (isEpsilon())
			return "ε";
		else
			return production.toString();
	}

}
