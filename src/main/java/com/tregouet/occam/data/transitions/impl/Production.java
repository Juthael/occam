package com.tregouet.occam.data.transitions.impl;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.transitions.IProduction;

public abstract class Production extends DefaultEdge implements IProduction {

	private static final long serialVersionUID = 1701074226278101143L;
	private final IIntentAttribute operatorInput;
	private final IIntentAttribute operatorOutput;
	
	public Production(IIntentAttribute operatorInput, IIntentAttribute operatorOutput) {
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

	@Override
	public IConcept getGenus() {
		return operatorOutput.getConcept();
	}

	@Override
	public IConcept getInstance() {
		return operatorInput.getConcept();
	}

	@Override
	public String getLabel() {
		return toString();
	}

	@Override
	public IIntentAttribute getSource() {
		return operatorInput;
	}

	@Override
	public IConcept getSourceCategory() {
		return operatorInput.getConcept();
	}

	@Override
	public IIntentAttribute getTarget() {
		return operatorOutput;
	}

	@Override
	public IConcept getTargetCategory() {
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

}
