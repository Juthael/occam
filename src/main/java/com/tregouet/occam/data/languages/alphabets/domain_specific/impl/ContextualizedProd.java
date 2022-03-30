package com.tregouet.occam.data.languages.alphabets.domain_specific.impl;

import java.util.Objects;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.languages.alphabets.domain_specific.IProduction;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.languages.words.construct.IConstruct;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IDenotation;

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
	public IConcept getGenus() {
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
	public IConcept getSpecies() {
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
		ContextualizedProd other = (ContextualizedProd) obj;
		return Objects.equals(speciesDenotation, other.speciesDenotation) && Objects.equals(genusDenotation, other.genusDenotation)
				&& Objects.equals(production, other.production);
	}

	@Override
	public IProduction getUncontextualizedProduction() {
		return production;
	}
	
	public boolean isRedundant() {
		return getSource().isRedundant();
	}	

}
