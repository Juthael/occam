package com.tregouet.occam.data.languages.specific;

import java.util.List;

public interface ICompositeProduction extends IProduction {
	
	public List<IBasicProduction> getComponents();

}
