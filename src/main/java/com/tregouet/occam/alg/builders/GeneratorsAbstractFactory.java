package com.tregouet.occam.alg.builders;

import com.tregouet.occam.alg.builders.concepts.denotations.DenotationBuilderFactory;
import com.tregouet.occam.alg.builders.concepts.denotations.DenotationConstructionStrategy;
import com.tregouet.occam.alg.builders.concepts.denotations.IDenotationBuilder;
import com.tregouet.occam.alg.builders.concepts.lattices.IConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.concepts.lattices.ConceptLatticeBuilderFactory;
import com.tregouet.occam.alg.builders.concepts.lattices.ConceptLatticeConstructionStrategy;
import com.tregouet.occam.alg.builders.concepts.trees.IConceptTreeBuilder;
import com.tregouet.occam.alg.builders.concepts.trees.ConceptTreeBuilderFactory;
import com.tregouet.occam.alg.builders.concepts.trees.ConceptTreeConstructionStrategy;
import com.tregouet.occam.alg.builders.representations.productions.from_concepts.IProdBuilderFromConceptLattice;
import com.tregouet.occam.alg.builders.representations.productions.from_concepts.ProdBldrFromConceptLattFactory;
import com.tregouet.occam.alg.builders.representations.productions.from_concepts.ProdConstrFromConceptLattStrategy;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.IProdBuilderFromDenotations;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.ProdBldrFromDenotationsFactory;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.ProdConstrFromDenotationsStrategy;
import com.tregouet.occam.alg.builders.representations.properties.IPropertyBuilder;
import com.tregouet.occam.alg.builders.representations.properties.PropertyBuilderFactory;
import com.tregouet.occam.alg.builders.representations.properties.PropertyConstructionStrategy;
import com.tregouet.occam.alg.builders.representations.salience.ITransitionSalienceSetter;
import com.tregouet.occam.alg.builders.representations.salience.TransitionSalienceSetterFactory;
import com.tregouet.occam.alg.builders.representations.salience.TransitionSalienceSettingStrategy;
import com.tregouet.occam.alg.builders.representations.transition_functions.IRepresentationTransFuncBuilder;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilderFactory;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncConstructionStrategy;

public class GeneratorsAbstractFactory {
	
	public static final GeneratorsAbstractFactory INSTANCE = new GeneratorsAbstractFactory();
	
	private DenotationConstructionStrategy denotationConstructionStrategy = null;
	private ConceptLatticeConstructionStrategy conceptLatticeConstructionStrategy = null;
	private ConceptTreeConstructionStrategy conceptTreeConstructionStrategy = null;
	private ProdConstrFromDenotationsStrategy prodConstrFromDenotationsStrategy = null;
	private ProdConstrFromConceptLattStrategy prodConstrFromConceptLattStrategy = null;
	private TransitionSalienceSettingStrategy transitionSalienceSettingStrategy = null;
	private RepresentationTransFuncConstructionStrategy representationTransFuncConstructionStrategy = null;
	private PropertyConstructionStrategy propertyConstructionStrategy = null;
	
	private GeneratorsAbstractFactory() {
	}
	
	public void setUpStrategy(GenerationStrategy overallStrategy) {
		switch(overallStrategy) {
			case GENERATION_STRATEGY_1 : 
				denotationConstructionStrategy = DenotationConstructionStrategy.MAX_SYMBOL_SUBSEQUENCES;
				conceptLatticeConstructionStrategy = ConceptLatticeConstructionStrategy.GALOIS_CONNECTION;
				conceptTreeConstructionStrategy = ConceptTreeConstructionStrategy.UNIDIMENSIONAL_SORTING;
				prodConstrFromDenotationsStrategy = ProdConstrFromDenotationsStrategy.MAP_TARGET_VARS_TO_SOURCE_VALUES;
				prodConstrFromConceptLattStrategy = ProdConstrFromConceptLattStrategy.IF_SUBORDINATE_THEN_BUILD_PRODUCTIONS;
				transitionSalienceSettingStrategy = TransitionSalienceSettingStrategy.HIDDEN_BY_DEFAULT_THEN_FIND_SPECIFICS;
				representationTransFuncConstructionStrategy = RepresentationTransFuncConstructionStrategy.ABSTRACT_FACTS_ACCEPTED;
				propertyConstructionStrategy = PropertyConstructionStrategy.GROUP_APPLICATIONS_BY_FUNCTION;
				break;
			default : 
				break;
		}
	}
	
	public ITransitionSalienceSetter getTransitionSalienceSetter() {
		return TransitionSalienceSetterFactory.INSTANCE.apply(transitionSalienceSettingStrategy);
	}
	
	public IProdBuilderFromConceptLattice getProdBuilderFromConceptLattice() {
		return ProdBldrFromConceptLattFactory.INSTANCE.apply(prodConstrFromConceptLattStrategy);
	}
	
	public IProdBuilderFromDenotations getProdBuilderFromDenotations() {
		return ProdBldrFromDenotationsFactory.INSTANCE.apply(prodConstrFromDenotationsStrategy);
	}
	
	public IDenotationBuilder getDenotationBuilder() {
		return DenotationBuilderFactory.INSTANCE.apply(denotationConstructionStrategy);
	}
	
	public IConceptLatticeBuilder getConceptLatticeBuilder() {
		return ConceptLatticeBuilderFactory.INSTANCE.apply(conceptLatticeConstructionStrategy);
	}
	
	public IConceptTreeBuilder getConceptTreeBuilder() {
		return ConceptTreeBuilderFactory.INSTANCE.apply(conceptTreeConstructionStrategy);
	}
	
	public IRepresentationTransFuncBuilder getTransitionsConstructionManager() {
		return RepresentationTransFuncBuilderFactory.INSTANCE.apply(representationTransFuncConstructionStrategy);
	}
	
	public IPropertyBuilder getPropertyBuilder() {
		return PropertyBuilderFactory.INSTANCE.apply(propertyConstructionStrategy);
	}

}
