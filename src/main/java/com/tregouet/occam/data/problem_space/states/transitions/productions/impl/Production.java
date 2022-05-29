package com.tregouet.occam.data.problem_space.states.transitions.productions.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IProduction;

public class Production implements IProduction {

	private final AVariable variable;
	private final IConstruct value;

	public Production(AVariable variable, IConstruct value) {
		this.variable = variable;
		this.value = value;
	}

	/**
	 * Given as an example to show what a Production is meant to be. No real
	 * utility.
	 * 
	 * @param construct
	 * @return
	 */
	public IConstruct derive(IConstruct construct) {
		List<ISymbol> returned = new ArrayList<>();
		for (ISymbol symbol : construct.asList()) {
			if (symbol.equals(variable))
				returned.addAll(value.asList());
			else
				returned.add(symbol);
		}
		return new Construct(returned);
	}

	@Override
	public boolean derives(AVariable var) {
		return var.equals(variable);
	}

	/**
	 * Given as an example to show what a Production is meant to be. No real
	 * utility.
	 * 
	 * @param construct
	 * @return
	 */
	public IConstruct doAbstract(IConstruct construct) {
		List<ISymbol> valueList = value.asList();
		List<ISymbol> returned = new ArrayList<>();
		List<ISymbol> buffer = new ArrayList<>();
		Iterator<ISymbol> constructIte = construct.asList().iterator();
		int valueIdx = 0;
		while (constructIte.hasNext()) {
			ISymbol nextSymbol = constructIte.next();
			if (nextSymbol.equals(valueList.get(valueIdx))) {
				if (valueIdx == valueList.size() - 1) {
					returned.add(variable);
					constructIte.forEachRemaining(s -> returned.add(s));
				} else {
					buffer.add(nextSymbol);
					valueIdx++;
				}
			} else {
				if (!buffer.isEmpty()) {
					returned.addAll(buffer);
					buffer.clear();
					valueIdx = 0;
				}
				returned.add(nextSymbol);
			}
		}
		return new Construct(returned);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Production other = (Production) obj;
		return Objects.equals(value, other.value) && Objects.equals(variable, other.variable);
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
	public boolean isEpsilon() {
		return false;
	}

	@Override
	public String toString() {
		return "[" + variable.toString() + " ::= " + value.toString() + "]";
	}

	@Override
	public boolean isBlank() {
		List<ISymbol> valueList = value.asList();
		return ((valueList.size() == 1) && (valueList.get(0).equals(variable))); 
	}

	@Override
	public boolean isAlphaConversion() {
		List<ISymbol> valueList = value.asList();
		return ((valueList.size() == 1) && (valueList.get(0) instanceof AVariable)); 
	}

}
