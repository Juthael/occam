package com.tregouet.occam.data.operators.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.operators.IBasicProduction;
import com.tregouet.occam.data.operators.ICompositeProduction;

public class CompositeProduction extends Production implements ICompositeProduction {

	private static final long serialVersionUID = 3178405428399810436L;
	private final List<IBasicProduction> basicProductions = new ArrayList<>();
	
	public CompositeProduction(IBasicProduction basicProduction) {
		super(basicProduction.getSource(), basicProduction.getTarget());
		this.basicProductions.add(basicProduction);
	}
	
	//Unsafe
	public CompositeProduction(IBasicProduction basicProduction1, IBasicProduction basicProduction2) {
		super(basicProduction1.getSource(), basicProduction2.getTarget());
		this.basicProductions.add(basicProduction1);
		this.basicProductions.add(basicProduction2);
	}

	@Override
	public ICompositeProduction compose(IBasicProduction basicProduction) {
		if (basicProduction.getSource().equals(getSource())
				&& basicProduction.getTarget().equals(getTarget())) {
			basicProductions.add(basicProduction);
			return this;
		}
		else return null;
	}

	@Override
	public boolean derives(AVariable var) {
		for (IBasicProduction prod : basicProductions) {
			if (prod.derives(var))
				return true;
		}
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompositeProduction other = (CompositeProduction) obj;
		if (basicProductions == null) {
			if (other.basicProductions != null)
				return false;
		} else if (!basicProductions.equals(other.basicProductions))
			return false;
		return true;
	}

	@Override
	public List<IBasicProduction> getComponents() {
		return new ArrayList<>(basicProductions);
	}

	@Override
	public List<IConstruct> getValues() {
		List<IConstruct> values = new ArrayList<>();
		for (IBasicProduction prod : basicProductions)
			values.add(prod.getValue());
		return values;
	}

	@Override
	public List<AVariable> getVariables() {
		List<AVariable> variables = new ArrayList<>();
		for (IBasicProduction prod : basicProductions)
			variables.add(prod.getVariable());
		return variables;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((basicProductions == null) ? 0 : basicProductions.hashCode());
		return result;
	}
	
	@Override
	public boolean isBlank() {
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		for (int i = 0 ; i < basicProductions.size() ; i++) {
			sB.append(basicProductions.get(i).toString());
			if (i < basicProductions.size() - 1)
				sB.append(" ");
		}
		return sB.toString();
	}

}
