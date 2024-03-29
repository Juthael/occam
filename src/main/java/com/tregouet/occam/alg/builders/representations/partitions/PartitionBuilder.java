package com.tregouet.occam.alg.builders.representations.partitions;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.representations.partitions.graphs.PartitionGraphBuilder;
import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.sortings.Sorting2StringConverter;
import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.weights.partitions.PartitionWeigher;
import com.tregouet.occam.data.modules.sorting.transitions.partitions.IPartition;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.descriptions.IDescription;

public interface PartitionBuilder extends BiFunction<IDescription, IClassification, Set<IPartition>> {

	public static PartitionGraphBuilder getPartitionGraphBuilder() {
		return BuildersAbstractFactory.INSTANCE.getPartitionGraphBuilder();
	}

	public static PartitionWeigher getPartitionWeigher() {
		return SettersAbstractFactory.INSTANCE.getPartitionWeigher();
	}

	public static Sorting2StringConverter getSorting2StringConverter() {
		return FormattersAbstractFactory.INSTANCE.getSorting2StringConverter();
	}

}
