package com.tregouet.occam.alg.builders;

import com.tregouet.occam.alg.builders.representations.concept_lattices.ConceptLatticeBuilderFactory;
import com.tregouet.occam.alg.builders.representations.concept_lattices.ConceptLatticeBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.concept_lattices.IConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.representations.concept_lattices.denotations.DenotationBuilderFactory;
import com.tregouet.occam.alg.builders.representations.concept_lattices.denotations.DenotationBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.concept_lattices.denotations.IDenotationBuilder;
import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilderFactory;
import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.concept_trees.IConceptTreeBuilder;
import com.tregouet.occam.alg.builders.representations.productions.IProductionBuilder;
import com.tregouet.occam.alg.builders.representations.productions.ProductionBuilderFactory;
import com.tregouet.occam.alg.builders.representations.productions.ProductionBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.IProdBuilderFromDenotations;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.ProdBldrFromDenotationsFactory;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.ProdBuilderFromDenotationsStrategy;
import com.tregouet.occam.alg.builders.representations.transition_functions.IRepresentationTransFuncBuilder;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilderFactory;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.transition_functions.transition_saliences.ITransitionSalienceSetter;
import com.tregouet.occam.alg.builders.representations.transition_functions.transition_saliences.TransitionSalienceSetterFactory;
import com.tregouet.occam.alg.builders.representations.transition_functions.transition_saliences.TransitionSalienceSetterStrategy;
import com.tregouet.occam.alg.builders.representations_dep.properties.IPropertyBuilder;
import com.tregouet.occam.alg.builders.representations_dep.properties.PropertyBuilderFactory;
import com.tregouet.occam.alg.builders.representations_dep.properties.PropertyConstructionStrategy;

public class GeneratorsAbstractFactory {
	
	public static final GeneratorsAbstractFactory INSTANCE = new GeneratorsAbstractFactory();
	
	private DenotationBuilderStrategy denotationBuilderStrategy = null;
	private ConceptLatticeBuilderStrategy conceptLatticeBuilderStrategy = null;
	private ConceptTreeBuilderStrategy conceptTreeBuilderStrategy = null;
	private ProdBuilderFromDenotationsStrategy prodBuilderFromDenotationsStrategy = null;
	private ProductionBuilderStrategy productionBuilderStrategy = null;
	private TransitionSalienceSetterStrategy transitionSalienceSetterStrategy = null;
	private RepresentationTransFuncBuilderStrategy representationTransFuncBuilderStrategy = null;
	private PropertyConstructionStrategy propertyConstructionStrategy = null;
	
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
				propertyConstructionStrategy = PropertyConstructionStrategy.GROUP_APPLICATIONS_BY_FUNCTION;
				break;
			default : 
				break;
		}
	}
	
	public ITransitionSalienceSetter getTransitionSalienceSetter() {
		return TransitionSalienceSetterFactory.INSTANCE.apply(transitionSalienceSetterStrategy);
	}
	
	public IProductionBuilder getProdBuilderFromConceptLattice() {
		return ProductionBuilderFactory.INSTANCE.apply(productionBuilderStrategy);
	}
	
	public IProdBuilderFromDenotations getProdBuilderFromDenotations() {
		return ProdBldrFromDenotationsFactory.INSTANCE.apply(prodBuilderFromDenotationsStrategy);
	}
	
	public IDenotationBuilder getDenotationBuilder() {
		return DenotationBuilderFactory.INSTANCE.apply(denotationBuilderStrategy);
	}
	
	public IConceptLatticeBuilder getConceptLatticeBuilder() {
		return ConceptLatticeBuilderFactory.INSTANCE.apply(conceptLatticeBuilderStrategy);
	}
	
	public IConceptTreeBuilder getConceptTreeBuilder() {
		return ConceptTreeBuilderFactory.INSTANCE.apply(conceptTreeBuilderStrategy);
	}
	
	public IRepresentationTransFuncBuilder getTransitionsConstructionManager() {
		return RepresentationTransFuncBuilderFactory.INSTANCE.apply(representationTransFuncBuilderStrategy);
	}
	
	public IPropertyBuilder getPropertyBuilder() {
		return PropertyBuilderFactory.INSTANCE.apply(propertyConstructionStrategy);
	}

}
