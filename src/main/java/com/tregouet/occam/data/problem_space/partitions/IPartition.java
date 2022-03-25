package com.tregouet.occam.data.problem_space.partitions;

import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.properties.IDifferentiae;

public interface IPartition {
	
	boolean subPartitionOf(IPartition partition);
	
	IConcept getGenus();
	
	IConcept[] getSpecies();
	
	IDifferentiae getDifferentiae();
	
	double[] getCardinality();
	
	double[] getInformativityCoef();
	
	String toString();
	
	double getInformativity();

}
