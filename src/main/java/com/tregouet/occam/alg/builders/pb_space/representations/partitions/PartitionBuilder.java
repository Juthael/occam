package com.tregouet.occam.alg.builders.pb_space.representations.partitions;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.graphs.PartitionGraphBuilder;
import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.sortings.Sorting2StringConverter;
import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.weighs.partitions.PartitionWeigher;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;

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
