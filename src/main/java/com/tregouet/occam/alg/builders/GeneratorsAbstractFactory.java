package com.tregouet.occam.alg.builders;

import com.tregouet.occam.alg.builders.preconcepts.denotations.DenotationBuilderFactory;
import com.tregouet.occam.alg.builders.preconcepts.denotations.DenotationConstructionStrategy;
import com.tregouet.occam.alg.builders.preconcepts.denotations.IDenotationBuilder;
import com.tregouet.occam.alg.builders.preconcepts.lattices.IPreconceptLatticeBuilder;
import com.tregouet.occam.alg.builders.preconcepts.lattices.PreconceptLatticeBuilderFactory;
import com.tregouet.occam.alg.builders.preconcepts.lattices.PreconceptLatticeConstructionStrategy;
import com.tregouet.occam.alg.builders.preconcepts.trees.IPreconceptTreeBuilder;
import com.tregouet.occam.alg.builders.preconcepts.trees.PreconceptTreeBuilderFactory;
import com.tregouet.occam.alg.builders.preconcepts.trees.PreconceptTreeConstructionStrategy;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.IProdBuilderFromDenotations;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.ProdBldrFromDenotationsFactory;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.ProdConstrFromDenotationsStrategy;
import com.tregouet.occam.alg.builders.representations.productions.from_preconcepts.IProdBuilderFromPreconceptLattice;
import com.tregouet.occam.alg.builders.representations.productions.from_preconcepts.ProdBldrFromPreconceptLattFactory;
import com.tregouet.occam.alg.builders.representations.productions.from_preconcepts.ProdConstrFromPreconceptLattStrategy;
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
	private PreconceptLatticeConstructionStrategy preconceptLatticeConstructionStrategy = null;
	private PreconceptTreeConstructionStrategy preconceptTreeConstructionStrategy = null;
	private ProdConstrFromDenotationsStrategy prodConstrFromDenotationsStrategy = null;
	private ProdConstrFromPreconceptLattStrategy prodConstrFromPreconceptLattStrategy = null;
	private TransitionSalienceSettingStrategy transitionSalienceSettingStrategy = null;
	private RepresentationTransFuncConstructionStrategy representationTransFuncConstructionStrategy = null;
	private PropertyConstructionStrategy propertyConstructionStrategy = null;
	
	private GeneratorsAbstractFactory() {
	}
	
	public void setUpStrategy(GenerationStrategy overallStrategy) {
		switch(overallStrategy) {
			case GENERATION_STRATEGY_1 : 
				denotationConstructionStrategy = DenotationConstructionStrategy.MAX_SYMBOL_SUBSEQUENCES;
				preconceptLatticeConstructionStrategy = PreconceptLatticeConstructionStrategy.GALOIS_CONNECTION;
				preconceptTreeConstructionStrategy = PreconceptTreeConstructionStrategy.UNIDIMENSIONAL_SORTING;
				prodConstrFromDenotationsStrategy = ProdConstrFromDenotationsStrategy.MAP_TARGET_VARS_TO_SOURCE_VALUES;
				prodConstrFromPreconceptLattStrategy = ProdConstrFromPreconceptLattStrategy.IF_SUBORDINATE_THEN_BUILD_PRODUCTIONS;
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
	
	public IProdBuilderFromPreconceptLattice getProdBuilderFromPreconceptLattice() {
		return ProdBldrFromPreconceptLattFactory.INSTANCE.apply(prodConstrFromPreconceptLattStrategy);
	}
	
	public IProdBuilderFromDenotations getProdBuilderFromDenotations() {
		return ProdBldrFromDenotationsFactory.INSTANCE.apply(prodConstrFromDenotationsStrategy);
	}
	
	public IDenotationBuilder getDenotationBuilder() {
		return DenotationBuilderFactory.INSTANCE.apply(denotationConstructionStrategy);
	}
	
	public IPreconceptLatticeBuilder getPreconceptLatticeBuilder() {
		return PreconceptLatticeBuilderFactory.INSTANCE.apply(preconceptLatticeConstructionStrategy);
	}
	
	public IPreconceptTreeBuilder getPreconceptTreeBuilder() {
		return PreconceptTreeBuilderFactory.INSTANCE.apply(preconceptTreeConstructionStrategy);
	}
	
	public IRepresentationTransFuncBuilder getTransitionsConstructionManager() {
		return RepresentationTransFuncBuilderFactory.INSTANCE.apply(representationTransFuncConstructionStrategy);
	}
	
	public IPropertyBuilder getPropertyBuilder() {
		return PropertyBuilderFactory.INSTANCE.apply(propertyConstructionStrategy);
	}

}
