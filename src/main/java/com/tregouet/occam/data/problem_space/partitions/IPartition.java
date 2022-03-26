package com.tregouet.occam.data.problem_space.partitions;

import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;

public interface IPartition {
	
	boolean subPartitionOf(IPartition partition);
	
	IConcept getGenus();
	
	IConcept[] getSpecies();
	
	AbstractDifferentiae getDifferentiae();
	
	double[] getCardinality();
	
	double[] getInformativityCoef();
	
	String toString();
	
	double getInformativity();

}
