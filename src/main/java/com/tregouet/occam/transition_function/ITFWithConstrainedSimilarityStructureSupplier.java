package com.tregouet.occam.transition_function;

public interface ITFWithConstrainedSimilarityStructureSupplier extends ITransitionFunctionSupplier {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);

}
