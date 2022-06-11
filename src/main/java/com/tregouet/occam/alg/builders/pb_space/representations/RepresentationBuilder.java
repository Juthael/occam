package com.tregouet.occam.alg.builders.pb_space.representations;

import java.util.function.Function;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.PartitionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.ProductionSetBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;

public interface RepresentationBuilder 
	extends Function<IClassification, IRepresentation> {
	
	public static ProductionSetBuilder productionSetBuilder() {
		return BuildersAbstractFactory.INSTANCE.getProductionSetBuilder();
	}
	
	public static RepresentationTransFuncBuilder transFuncBuilder() {
		return BuildersAbstractFactory.INSTANCE.getRepresentationTransFuncBuilder();
	}
	
	public static DescriptionBuilder descriptionBuilder() {
		return BuildersAbstractFactory.INSTANCE.getDescriptionBuilder();
	}	
	
	public static PartitionBuilder partitionBuilder() {
		return BuildersAbstractFactory.INSTANCE.getPartitionBuilder();
	}

}
