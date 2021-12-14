package com.tregouet.occam.data.concepts;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.abstract_machines.transitions.IOperator;
import com.tregouet.occam.data.abstract_machines.transitions.IReframer;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;

public abstract class IGenusDifferentiaDefinition extends IIsA {
	
	private static final long serialVersionUID = -1660518980107230824L;

	abstract public List<ITransition> getDifferentiae();
	
	abstract public IConcept getGenus();
	
	abstract public IConcept getSpecies();
	
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

}
