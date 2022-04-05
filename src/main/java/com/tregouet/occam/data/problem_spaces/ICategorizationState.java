package com.tregouet.occam.data.problem_spaces;

import java.util.Set;

import com.tregouet.occam.data.logical_structures.partial_order.PartiallyComparable;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.descriptions.IDescription;

public interface ICategorizationState extends PartiallyComparable<ICategorizationState> {
	
	IDescription getDescription();
	
	Set<IRepresentation> getReacheableExhaustiveRepresentations();
	
	int id();

}
