package com.tregouet.occam.alg.builders;

import com.tregouet.occam.alg.builders.representations.concept_lattices.ConceptLatticeBuilderFactory;
import com.tregouet.occam.alg.builders.representations.concept_lattices.ConceptLatticeBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.concept_lattices.ConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.representations.concept_lattices.denotations.DenotationBuilderFactory;
import com.tregouet.occam.alg.builders.representations.concept_lattices.denotations.DenotationBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.concept_lattices.denotations.IDenotationBuilder;
import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilderFactory;
import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.DifferentiaeBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.DifferentiaeBuilderFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.DifferentiaeBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.PropertyBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.PropertyBuilderFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.PropertyBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.productions.ProductionBuilder;
import com.tregouet.occam.alg.builders.representations.productions.ProductionBuilderFactory;
import com.tregouet.occam.alg.builders.representations.productions.ProductionBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.ProdBuilderFromDenotations;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.ProdBldrFromDenotationsFactory;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.ProdBuilderFromDenotationsStrategy;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilderFactory;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.transition_functions.transition_saliences.TransitionSalienceSetter;
import com.tregouet.occam.alg.builders.representations.transition_functions.transition_saliences.TransitionSalienceSetterFactory;
import com.tregouet.occam.alg.builders.representations.transition_functions.transition_saliences.TransitionSalienceSetterStrategy;

public class GeneratorsAbstractFactory {
	
	public static final GeneratorsAbstractFactory INSTANCE = new GeneratorsAbstractFactory();
	
	private DenotationBuilderStrategy denotationBuilderStrategy = null;
	private ConceptLatticeBuilderStrategy conceptLatticeBuilderStrategy = null;
	private ConceptTreeBuilderStrategy conceptTreeBuilderStrategy = null;
	private ProdBuilderFromDenotationsStrategy prodBuilderFromDenotationsStrategy = null;
	private ProductionBuilderStrategy productionBuilderStrategy = null;
	private TransitionSalienceSetterStrategy transitionSalienceSetterStrategy = null;
	private RepresentationTransFuncBuilderStrategy representationTransFuncBuilderStrategy = null;
	private PropertyBuilderStrategy propertyBuilderStrategy = null;
	private DifferentiaeBuilderStrategy differentiaeBuilderStrategy = null;
	
	private GeneratorsAbstractFactory() {
	}
	
	public void setUpStrategy(GenerationStrategy overallStrategy) {
		switch(overallStrategy) {
			case GENERATION_STRATEGY_1 : 
				denotationBuilderStrategy = DenotationBuilderStrategy.MAX_SYMBOL_SUBSEQUENCES;
				conceptLatticeBuilderStrategy = ConceptLatticeBuilderStrategy.GALOIS_CONNECTION;
				conceptTreeBuilderStrategy = ConceptTreeBuilderStrategy.UNIDIMENSIONAL_SORTING;
				prodBuilderFromDenotationsStrategy = ProdBuilderFromDenotationsStrategy.MAP_TARGET_VARS_TO_SOURCE_VALUES;
				productionBuilderStrategy = ProductionBuilderStrategy.IF_SUBORDINATE_THEN_BUILD_PRODUCTIONS;
				transitionSalienceSetterStrategy = TransitionSalienceSetterStrategy.HIDDEN_BY_DEFAULT_THEN_FIND_SPECIFICS;
				representationTransFuncBuilderStrategy = RepresentationTransFuncBuilderStrategy.ABSTRACT_FACTS_ACCEPTED;
				propertyBuilderStrategy = PropertyBuilderStrategy.GROUP_APPLICATIONS_BY_FUNCTION;
				differentiaeBuilderStrategy = DifferentiaeBuilderStrategy.IF_IS_A_THEN_DIFFER;
				break;
			default : 
				break;
		}
	}
	
	public DifferentiaeBuilder getDifferentiaeBuilder() {
		return DifferentiaeBuilderFactory.INSTANCE.apply(differentiaeBuilderStrategy);
	}
	
	public TransitionSalienceSetter getTransitionSalienceSetter() {
		return TransitionSalienceSetterFactory.INSTANCE.apply(transitionSalienceSetterStrategy);
	}
	
	public ProductionBuilder getProdBuilderFromConceptLattice() {
		return ProductionBuilderFactory.INSTANCE.apply(productionBuilderStrategy);
	}
	
	public ProdBuilderFromDenotations getProdBuilderFromDenotations() {
		return ProdBldrFromDenotationsFactory.INSTANCE.apply(prodBuilderFromDenotationsStrategy);
	}
	
	public IDenotationBuilder getDenotationBuilder() {
		return DenotationBuilderFactory.INSTANCE.apply(denotationBuilderStrategy);
	}
	
	public ConceptLatticeBuilder getConceptLatticeBuilder() {
		return ConceptLatticeBuilderFactory.INSTANCE.apply(conceptLatticeBuilderStrategy);
	}
	
	public ConceptTreeBuilder getConceptTreeBuilder() {
		return ConceptTreeBuilderFactory.INSTANCE.apply(conceptTreeBuilderStrategy);
	}
	
	public RepresentationTransFuncBuilder getTransitionsConstructionManager() {
		return RepresentationTransFuncBuilderFactory.INSTANCE.apply(representationTransFuncBuilderStrategy);
	}
	
	public PropertyBuilder getPropertyBuilder() {
		return PropertyBuilderFactory.INSTANCE.apply(propertyBuilderStrategy);
	}

}
