package com.tregouet.occam.data.partitions;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IDifferentiae;

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
