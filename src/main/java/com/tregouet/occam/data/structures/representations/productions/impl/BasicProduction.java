package com.tregouet.occam.data.structures.representations.productions.impl;

import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.structures.languages.words.construct.impl.ConstructComparator;
import com.tregouet.occam.data.structures.representations.productions.IBasicProduction;
import com.tregouet.occam.data.structures.representations.productions.Salience;

public class BasicProduction implements IBasicProduction {

	private final AVariable variable;
	private final IConstruct value;
	private Salience salience = null;

	public BasicProduction(AVariable variable, IConstruct value) {
		this.variable = variable;
		this.value = value;
	}

	@Override
	public Integer compareTo(IBasicProduction prod) {
		if (!this.variable.equals(prod.getVariable()))
			return null;
		else return ConstructComparator.INSTANCE.compare(this.value, prod.getValue());
	}

	@Override
	public boolean derives(AVariable var) {
		return var.equals(variable);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		BasicProduction other = (BasicProduction) obj;
		return Objects.equals(value, other.value) && Objects.equals(variable, other.variable);
	}

	@Override
	public Salience getSalience() {
		return salience;
	}

	@Override
	public IConstruct getValue() {
		return value;
	}

	@Override
	public AVariable getVariable() {
		return variable;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, variable);
	}

	@Override
	public boolean isAlphaConversionProd() {
		List<ISymbol> valueList = value.asList();
		return ((valueList.size() == 1) && (valueList.get(0) instanceof AVariable));
	}

	@Override
	public boolean isEpsilon() {
		return false;
	}

	@Override
	public boolean isIdentityProd() {
		List<ISymbol> valueList = value.asList();
		return ((valueList.size() == 1) && (valueList.get(0).equals(variable)));
	}

	@Override
	public boolean isRelational() {
		if (value.getNbOfTerminals() != 1)
			return false;
		return value.getListOfTerminals().get(0).isRelational();
	}

	@Override
	public void setSalience(Salience salience) {
		this.salience = salience;
	}

	@Override
	public String toString() {
		return "[" + variable.toString() + " ::= " + value.toString() + "]";
	}

}
