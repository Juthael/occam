package com.tregouet.occam.data.operators;

import java.util.List;

public interface ICompositeProduction extends IProduction {
	
	public List<IBasicProduction> getComponents();

}
