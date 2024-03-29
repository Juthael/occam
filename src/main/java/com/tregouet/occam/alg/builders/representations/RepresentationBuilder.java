package com.tregouet.occam.alg.builders.representations;

import java.util.function.Function;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.builders.representations.partitions.PartitionBuilder;
import com.tregouet.occam.alg.builders.representations.production_sets.ProductionSetBuilder;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;

public interface RepresentationBuilder
	extends Function<IClassification, IRepresentation> {

	public static DescriptionBuilder descriptionBuilder() {
		return BuildersAbstractFactory.INSTANCE.getDescriptionBuilder();
	}

	public static PartitionBuilder partitionBuilder() {
		return BuildersAbstractFactory.INSTANCE.getPartitionBuilder();
	}

	public static ProductionSetBuilder productionSetBuilder() {
		return BuildersAbstractFactory.INSTANCE.getProductionSetBuilder();
	}

	public static RepresentationTransFuncBuilder transFuncBuilder() {
		return BuildersAbstractFactory.INSTANCE.getRepresentationTransFuncBuilder();
	}

}
