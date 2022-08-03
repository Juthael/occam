package com.tregouet.occam.alg.builders;

import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorerFactory;
import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorerStrategy;
import com.tregouet.occam.alg.builders.pb_space.classifications.ClassificationBuilder;
import com.tregouet.occam.alg.builders.pb_space.classifications.ClassificationBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.classifications.ClassificationBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.ConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.ConceptLatticeBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.ConceptLatticeBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.denotations.DenotationBuilder;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.denotations.DenotationBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.denotations.DenotationBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.ConceptTreeGrower;
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.ConceptTreeGrowerFactory;
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.ConceptTreeGrowerStrategy;
import com.tregouet.occam.alg.builders.pb_space.graph_updater.expander.ProblemSpaceGraphExpander;
import com.tregouet.occam.alg.builders.pb_space.graph_updater.expander.ProblemSpaceGraphExpanderFactory;
import com.tregouet.occam.alg.builders.pb_space.graph_updater.expander.ProblemSpaceGraphExpanderStrategy;
import com.tregouet.occam.alg.builders.pb_space.graph_updater.restrictor.ProblemSpaceGraphRestrictor;
import com.tregouet.occam.alg.builders.pb_space.graph_updater.restrictor.ProblemSpaceGraphRestrictorFactory;
import com.tregouet.occam.alg.builders.pb_space.graph_updater.restrictor.ProblemSpaceGraphRestrictorStrategy;
import com.tregouet.occam.alg.builders.pb_space.metrics.SimilarityMetricsBuilder;
import com.tregouet.occam.alg.builders.pb_space.metrics.SimilarityMetricsBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.metrics.SimilarityMetricsBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.asymetrical_sim.AsymmetricalSimilarityMatrixBuilder;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.asymetrical_sim.AsymmetricalSimilarityMatrixBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.asymetrical_sim.AsymmetricalSimilarityMatrixBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.difference.DifferenceMatrixBuilder;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.difference.DifferenceMatrixBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.difference.DifferenceMatrixBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.symmetrical_sim.SimilarityMatrixBuilder;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.symmetrical_sim.SimilarityMatrixBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.symmetrical_sim.SimilarityMatrixBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.typicality.TypicalityVectorBuilder;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.typicality.TypicalityVectorBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.typicality.TypicalityVectorBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.DescriptionBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.DescriptionBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.DifferentiaeBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.DifferentiaeBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.DifferentiaeBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.PropertyBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.PropertyBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.PropertyBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics.RelativeSimilarityMetricsBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics.RelativeSimilarityMetricsBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics.RelativeSimilarityMetricsBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.PartitionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.PartitionBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.PartitionBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.graphs.PartitionGraphBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.graphs.PartitionGraphBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.graphs.PartitionGraphBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.ProductionSetBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.ProductionSetBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.ProductionSetBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.productions.ProductionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.productions.ProductionBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.productions.ProductionBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.salience_setter.ProductionSalienceSetter;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.salience_setter.ProductionSalienceSetterFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.salience_setter.ProductionSalienceSetterStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilderStrategy;

public class BuildersAbstractFactory {

	public static final BuildersAbstractFactory INSTANCE = new BuildersAbstractFactory();

	private DenotationBuilderStrategy denotationBuilderStrategy = null;
	private ConceptLatticeBuilderStrategy conceptLatticeBuilderStrategy = null;
	private ConceptTreeGrowerStrategy conceptTreeGrowerStrategy = null;
	private ClassificationBuilderStrategy classificationBuilderStrategy = null;
	private ProductionBuilderStrategy productionBuilderStrategy = null;
	private ProductionSalienceSetterStrategy productionSalienceSetterStrategy = null;
	private ProductionSetBuilderStrategy productionSetBuilderStrategy = null;
	private RepresentationTransFuncBuilderStrategy representationTransFuncBuilderStrategy = null;
	private PropertyBuilderStrategy propertyBuilderStrategy = null;
	private DifferentiaeBuilderStrategy differentiaeBuilderStrategy = null;
	private DescriptionBuilderStrategy descriptionBuilderStrategy = null;
	private PartitionGraphBuilderStrategy partitionGraphBuilderStrategy = null;
	private PartitionBuilderStrategy partitionBuilderStrategy = null;
	private RepresentationBuilderStrategy representationBuilderStrategy = null;
	private RelativeSimilarityMetricsBuilderStrategy relativeSimilarityMetricsBuilderStrategy = null;
	private ProblemSpaceGraphExpanderStrategy problemSpaceGraphExpanderStrategy = null;
	private ProblemSpaceGraphRestrictorStrategy problemSpaceGraphRestrictorStrategy = null;
	private SimilarityMatrixBuilderStrategy similarityMatrixBuilderStrategy = null;
	private AsymmetricalSimilarityMatrixBuilderStrategy asymmetricalSimilarityMatrixBuilderStrategy = null;
	private DifferenceMatrixBuilderStrategy differenceMatrixBuilderStrategy = null;
	private TypicalityVectorBuilderStrategy typicalityVectorBuilderStrategy = null;
	private SimilarityMetricsBuilderStrategy similarityMetricsBuilderStrategy = null;
	private ProblemSpaceExplorerStrategy problemSpaceExplorerStrategy = null;

	private BuildersAbstractFactory() {
	}

	public AsymmetricalSimilarityMatrixBuilder getAsymmetricalSimilarityMatrixBuilder() {
		return AsymmetricalSimilarityMatrixBuilderFactory.INSTANCE.apply(asymmetricalSimilarityMatrixBuilderStrategy);
	}

	public ClassificationBuilder getClassificationBuilder() {
		return ClassificationBuilderFactory.INSTANCE.apply(classificationBuilderStrategy);
	}

	public ConceptLatticeBuilder getConceptLatticeBuilder() {
		return ConceptLatticeBuilderFactory.INSTANCE.apply(conceptLatticeBuilderStrategy);
	}

	public ConceptTreeGrower getConceptTreeGrower() {
		return ConceptTreeGrowerFactory.INSTANCE.apply(conceptTreeGrowerStrategy);
	}

	public DenotationBuilder getDenotationBuilder() {
		return DenotationBuilderFactory.INSTANCE.apply(denotationBuilderStrategy);
	}

	public DescriptionBuilder getDescriptionBuilder() {
		return DescriptionBuilderFactory.INSTANCE.apply(descriptionBuilderStrategy);
	}

	public DifferenceMatrixBuilder getDifferenceMatrixBuilder() {
		return DifferenceMatrixBuilderFactory.INSTANCE.apply(differenceMatrixBuilderStrategy);
	}

	public DifferentiaeBuilder getDifferentiaeBuilder() {
		return DifferentiaeBuilderFactory.INSTANCE.apply(differentiaeBuilderStrategy);
	}

	public PartitionBuilder getPartitionBuilder() {
		return PartitionBuilderFactory.INSTANCE.apply(partitionBuilderStrategy);
	}

	public PartitionGraphBuilder getPartitionGraphBuilder() {
		return PartitionGraphBuilderFactory.INSTANCE.apply(partitionGraphBuilderStrategy);
	}

	public ProblemSpaceExplorer getProblemSpaceExplorer() {
		return ProblemSpaceExplorerFactory.INSTANCE.apply(problemSpaceExplorerStrategy);
	}

	public ProblemSpaceGraphExpander getProblemSpaceGraphExpander() {
		return ProblemSpaceGraphExpanderFactory.INSTANCE.apply(problemSpaceGraphExpanderStrategy);
	}

	public ProblemSpaceGraphRestrictor getProblemSpaceGraphRestrictor() {
		return ProblemSpaceGraphRestrictorFactory.INSTANCE.apply(problemSpaceGraphRestrictorStrategy);
	}

	public ProductionBuilder getProductionBuilder() {
		return ProductionBuilderFactory.INSTANCE.apply(productionBuilderStrategy);
	}

	public ProductionSalienceSetter getProductionSalienceSetter() {
		return ProductionSalienceSetterFactory.INSTANCE.apply(productionSalienceSetterStrategy);
	}

	public ProductionSetBuilder getProductionSetBuilder() {
		return ProductionSetBuilderFactory.INSTANCE.apply(productionSetBuilderStrategy);
	}

	public PropertyBuilder getPropertyBuilder() {
		return PropertyBuilderFactory.INSTANCE.apply(propertyBuilderStrategy);
	}

	public RelativeSimilarityMetricsBuilder getRelativeSimilarityMetricsBuilder() {
		return RelativeSimilarityMetricsBuilderFactory.INSTANCE.apply(relativeSimilarityMetricsBuilderStrategy);
	}

	public RepresentationBuilder getRepresentationBuilder() {
		return RepresentationBuilderFactory.INSTANCE.apply(representationBuilderStrategy);
	}

	public RepresentationTransFuncBuilder getRepresentationTransFuncBuilder() {
		return RepresentationTransFuncBuilderFactory.INSTANCE.apply(representationTransFuncBuilderStrategy);
	}

	public SimilarityMatrixBuilder getSimilarityMatrixBuilder() {
		return SimilarityMatrixBuilderFactory.INSTANCE.apply(similarityMatrixBuilderStrategy);
	}

	public SimilarityMetricsBuilder getSimilarityMetricsBuilder() {
		return SimilarityMetricsBuilderFactory.INSTANCE.apply(similarityMetricsBuilderStrategy);
	}

	public TypicalityVectorBuilder getTypicalityVectorBuilder() {
		return TypicalityVectorBuilderFactory.INSTANCE.apply(typicalityVectorBuilderStrategy);
	}

	public void setUpStrategy(BuildStrategy overallStrategy) {
		switch (overallStrategy) {
		case GENERATION_STRATEGY_2 :
			denotationBuilderStrategy = DenotationBuilderStrategy.NO_REDUNDANCY;
			conceptLatticeBuilderStrategy = ConceptLatticeBuilderStrategy.GALOIS_CONNECTION;
			conceptTreeGrowerStrategy = ConceptTreeGrowerStrategy.IF_LEAF_IS_UNIVERSAL_THEN_SORT;
			classificationBuilderStrategy = ClassificationBuilderStrategy.BUILD_PARAM_THEN_INST;
			productionBuilderStrategy = ProductionBuilderStrategy.SRCE_CNCPT_CANNOT_HAVE_TGET_DENOT;
			productionSalienceSetterStrategy = ProductionSalienceSetterStrategy.HIDDEN_THEN_FIND_SPECIFICS;
			productionSetBuilderStrategy = ProductionSetBuilderStrategy.NO_EPSILON;
			representationTransFuncBuilderStrategy = RepresentationTransFuncBuilderStrategy.EVERY_APP_IS_RELEVANT;
			propertyBuilderStrategy = PropertyBuilderStrategy.GROUP_PRODUCTIONS_BY_COMPUTATION;
			differentiaeBuilderStrategy = DifferentiaeBuilderStrategy.IF_IS_A_THEN_DIFFER;
			descriptionBuilderStrategy = DescriptionBuilderStrategy.BUILD_TREE_THEN_CALCULATE_METRICS;
			partitionGraphBuilderStrategy = PartitionGraphBuilderStrategy.RECURSIVE_FORK_EXPLORATION;
			partitionBuilderStrategy = PartitionBuilderStrategy.BUILD_GRAPH_FIRST;
			representationBuilderStrategy = RepresentationBuilderStrategy.BUILD_TREE_SPECIFIC_PRODUCTION_SET;
			relativeSimilarityMetricsBuilderStrategy = RelativeSimilarityMetricsBuilderStrategy.MOST_SPECIFIC_CONCEPT;
			problemSpaceGraphExpanderStrategy = ProblemSpaceGraphExpanderStrategy.ADD_NEW_STATES_THEN_BUILD_TRANSITIONS;
			problemSpaceGraphRestrictorStrategy = ProblemSpaceGraphRestrictorStrategy.BUILD_NEW_GRAPH;
			similarityMatrixBuilderStrategy = SimilarityMatrixBuilderStrategy.MAXIMAL_RELATIVE_SIMILARITY;
			asymmetricalSimilarityMatrixBuilderStrategy = AsymmetricalSimilarityMatrixBuilderStrategy.SIM_DIV_BY_TOTAL_SIM;
			differenceMatrixBuilderStrategy = DifferenceMatrixBuilderStrategy.TWO_LEAVES_TREE_FOR_EACH_PAIR;
			typicalityVectorBuilderStrategy = TypicalityVectorBuilderStrategy.ASYMM_SIM_MATRIX_ROW_AVERAGE;
			similarityMetricsBuilderStrategy = SimilarityMetricsBuilderStrategy.SIM_THEN_DIFF_THEN_TYPICALITY;
			problemSpaceExplorerStrategy = ProblemSpaceExplorerStrategy.DISCARD_UNINFORMATIVE_STATES;
			break;
		default:
			break;
		}
	}

}
