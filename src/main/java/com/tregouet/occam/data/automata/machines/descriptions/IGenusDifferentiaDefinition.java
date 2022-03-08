package com.tregouet.occam.data.automata.machines.descriptions;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.alg.scoring.costs.ICosted;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transition_rules.IConjunctiveTransition;
import com.tregouet.occam.data.automata.transition_rules.IReframerRule;
import com.tregouet.occam.data.automata.transition_rules.ITransition;

public abstract class IGenusDifferentiaDefinition extends DefaultEdge implements ICosted {
	
	private static final long serialVersionUID = -1660518980107230824L;
	
	private Double cost = null;

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof IGenusDifferentiaDefinition))
			return false;
		IGenusDifferentiaDefinition other = (IGenusDifferentiaDefinition) o;
		return (getSource().equals(other.getSource()) && getTarget().equals(other.getTarget()));
	}
	
	@Override
	public Double getCost() {
		return cost;
	}
	
	abstract public List<IConjunctiveTransition> getDifferentiae();
	
	abstract public IState getGenusState();
	
	abstract public IState getSpeciesState();
	
	@Override
	public int hashCode() {
		return getSource().hashCode() + getTarget().hashCode();
	}
	
	abstract public boolean isReframer();
	
	@Override
	public void setCost(double cost) {
		this.cost = cost;
	}	
	
	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		String nL = System.lineSeparator();
		List<ITransition> operators = new ArrayList<>();
		IReframerRule reframerRule = null;
		for (ITransition differentia : getDifferentiae()) {
			if (!differentia.isBlank()) {
				if (reframerRule == null && differentia instanceof IReframerRule)
					reframerRule = (IReframerRule) differentia;
				else operators.add(differentia);
			}
		}
		if (reframerRule != null) {
			sB.append(reframerRule.toString());
			if (!operators.isEmpty())
				sB.append(nL);
		}
		for (int i = 0 ; i < operators.size() ; i++) {
			sB.append(operators.get(i).toString());
			if (i < operators.size() - 1)
				sB.append(nL);
		}
		return sB.toString();
	}	

}
