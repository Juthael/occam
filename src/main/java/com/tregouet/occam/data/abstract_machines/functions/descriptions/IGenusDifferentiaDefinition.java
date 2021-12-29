package com.tregouet.occam.data.abstract_machines.functions.descriptions;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.alg.scoring.costs.ICosted;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transitions.IReframer;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;

public abstract class IGenusDifferentiaDefinition extends DefaultEdge implements ICosted {
	
	private static final long serialVersionUID = -1660518980107230824L;
	
	private Double cost = null;

	abstract public List<IConjunctiveTransition> getDifferentiae();
	
	abstract public IState getGenus();
	
	abstract public IState getSpecies();
	
	abstract public boolean isReframer();
	
	public String toString() {
		StringBuilder sB = new StringBuilder();
		String nL = System.lineSeparator();
		List<ITransition> operators = new ArrayList<>();
		IReframer reframer = null;
		for (ITransition differentia : getDifferentiae()) {
			if (!differentia.isBlank()) {
				if (reframer == null && differentia instanceof IReframer)
					reframer = (IReframer) differentia;
				else operators.add(differentia);
			}
		}
		if (reframer != null) {
			sB.append(reframer.toString());
			if (!operators.isEmpty())
				sB.append(nL);
		}
		for (int i = 0 ; i < operators.size() ; i++) {
			sB.append(operators.get(i));
			if (i < operators.size() - 1)
				sB.append(nL);
		}
		return sB.toString();
	}
	
	@Override
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	@Override
	public Double getCost() {
		return cost;
	}
	
	@Override
	public int hashCode() {
		return getSource().hashCode() + getTarget().hashCode();
	}	
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof IGenusDifferentiaDefinition))
			return false;
		IGenusDifferentiaDefinition other = (IGenusDifferentiaDefinition) o;
		return (getSource().equals(other.getSource()) && getTarget().equals(other.getTarget()));
	}	

}
