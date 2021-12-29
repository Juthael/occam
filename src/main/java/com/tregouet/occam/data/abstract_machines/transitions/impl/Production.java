package com.tregouet.occam.data.abstract_machines.transitions.impl;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentConstruct;

public abstract class Production extends DefaultEdge implements IProduction {

	private static final long serialVersionUID = 1701074226278101143L;
	private final IIntentConstruct operatorInput;
	private final IIntentConstruct operatorOutput;
	private Double cost = null;
	
	public Production(IIntentConstruct operatorInput, IIntentConstruct operatorOutput) {
		this.operatorInput = operatorInput;
		this.operatorOutput = operatorOutput;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Production other = (Production) obj;
		if (operatorInput == null) {
			if (other.operatorInput != null)
				return false;
		} else if (!operatorInput.equals(other.operatorInput))
			return false;
		if (operatorOutput == null) {
			if (other.operatorOutput != null)
				return false;
		} else if (!operatorOutput.equals(other.operatorOutput))
			return false;
		return true;
	}

	public Double getCost() {
		return cost;
	}

	@Override
	public IConcept getGenus() {
		return operatorOutput.getConcept();
	}

	@Override
	public String getLabel() {
		return toString();
	}

	@Override
	public IIntentConstruct getSource() {
		return operatorInput;
	}

	@Override
	public IConcept getSourceCategory() {
		return operatorInput.getConcept();
	}

	@Override
	public IConcept getSpecies() {
		return operatorInput.getConcept();
	}

	@Override
	public IIntentConstruct getTarget() {
		return operatorOutput;
	}

	@Override
	public IConcept getTargetConcept() {
		return operatorOutput.getConcept();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operatorInput == null) ? 0 : operatorInput.hashCode());
		result = prime * result + ((operatorOutput == null) ? 0 : operatorOutput.hashCode());
		return result;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}	

}
