package com.tregouet.occam.data.operators.impl;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;

public abstract class Production extends DefaultEdge implements IProduction {

	private static final long serialVersionUID = 1701074226278101143L;
	private final IIntentAttribute operatorInput;
	private final IIntentAttribute operatorOutput;
	private IOperator operator;
	
	public Production(IIntentAttribute operatorInput, IIntentAttribute operatorOutput) {
		this.operatorInput = operatorInput;
		this.operatorOutput = operatorOutput;
	}

	@Override
	public ICategory getGenus() {
		return operatorOutput.getCategory();
	}

	@Override
	public ICategory getInstance() {
		return operatorInput.getCategory();
	}

	public IIntentAttribute getOperatorInput() {
		return operatorInput;
	}

	public IIntentAttribute getOperatorOutput() {
		return operatorOutput;
	}

	@Override
	public void setOperator(IOperator operator) {
		this.operator = operator;
	}

	@Override
	public IOperator getOperator() {
		return operator;
	}

	@Override
	public IIntentAttribute getSource() {
		return operatorInput;
	}

	@Override
	public IIntentAttribute getTarget() {
		return operatorOutput;
	}

	@Override
	public ICategory getSourceCategory() {
		return operatorInput.getCategory();
	}

	@Override
	public ICategory getTargetCategory() {
		return operatorOutput.getCategory();
	}

	@Override
	public String getLabel() {
		return toString();
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

}
