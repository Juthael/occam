package com.tregouet.occam.data.languages.specific.impl;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.languages.specific.IEdgeProduction;
import com.tregouet.occam.data.denotations.IDenotation;

public abstract class EdgeProduction extends DefaultEdge implements IEdgeProduction {

	private static final long serialVersionUID = 1701074226278101143L;
	private final IDenotation operatorInput;
	private final IDenotation operatorOutput;
	private Double cost = null;
	
	public EdgeProduction(IDenotation operatorInput, IDenotation operatorOutput) {
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
		EdgeProduction other = (EdgeProduction) obj;
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
	public IDenotationSet getGenusDenotationSet() {
		return operatorOutput.getDenotationSet();
	}

	@Override
	public String getLabel() {
		return toString();
	}

	@Override
	public IDenotation getSource() {
		return operatorInput;
	}

	@Override
	public IDenotationSet getSourceDenotationSet() {
		return operatorInput.getDenotationSet();
	}

	@Override
	public IDenotationSet getSpeciesDenotationSet() {
		return operatorInput.getDenotationSet();
	}

	@Override
	public IDenotation getTarget() {
		return operatorOutput;
	}

	@Override
	public IDenotationSet getTargetDenotationSet() {
		return operatorOutput.getDenotationSet();
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
