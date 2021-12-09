package com.tregouet.occam.data.transitions;

import java.util.List;

public interface ICompositeProduction extends IProduction {
	
	public List<IBasicProduction> getComponents();

}
