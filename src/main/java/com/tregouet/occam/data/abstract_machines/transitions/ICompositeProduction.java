package com.tregouet.occam.data.abstract_machines.transitions;

import java.util.List;

public interface ICompositeProduction extends IProduction {
	
	public List<IBasicProduction> getComponents();

}
