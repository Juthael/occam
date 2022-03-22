package com.tregouet.occam.alg.builders.preconcepts.lattices.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.preconcepts.lattices.IPreconceptLatticeBuilder;
import com.tregouet.occam.alg.builders.preconcepts.lattices.utils.MarkRedundantDenotations;
import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.preconcepts.IPreconceptLattice;
import com.tregouet.occam.data.preconcepts.impl.IsA;
import com.tregouet.occam.data.preconcepts.impl.Preconcept;
import com.tregouet.occam.data.preconcepts.impl.PreconceptLattice;
import com.tregouet.occam.data.preconcepts.impl.ThisPreconcept;
import com.tregouet.occam.data.representations.concepts.ConceptType;
import com.tregouet.tree_finder.data.UpperSemilattice;

public class PreconceptLatticeBuilder implements IPreconceptLatticeBuilder {

	private List<IContextObject> objects = null;
	private DirectedAcyclicGraph<IPreconcept, IIsA> lattice = null;
	private UpperSemilattice<IPreconcept, IIsA> upperSemilattice = null;
	private IPreconcept ontologicalCommitment = null;
	private List<IPreconcept> topologicalOrder = null;
	private IPreconcept truism = null;
	private List<IPreconcept> objectPreconcepts = null;
	private IPreconcept absurdity = null;
	
	public PreconceptLatticeBuilder() {
	}
	
	private void buildLattice() {
		Map<Set<IConstruct>, Set<IContextObject>> denotatingConstructsToExtents = buildIntentToExtentRel();
		for (Entry<Set<IConstruct>, Set<IContextObject>> entry : denotatingConstructsToExtents.entrySet()) {
			IPreconcept preconcept = new Preconcept(entry.getKey(), entry.getValue());
			if (!preconcept.getExtent().isEmpty()) {
				if (preconcept.getExtent().size() == 1)
					preconcept.setType(ConceptType.PARTICULAR);
				else if (preconcept.getExtent().size() == objects.size()) {
					preconcept.setType(ConceptType.TRUISM);
				}
				else {
					preconcept.setType(ConceptType.UNIVERSAL);
				}
			}
			else {
				preconcept.setType(ConceptType.ABSURDITY);
			}
			lattice.addVertex(preconcept);
		}
		List<IPreconcept> denotSetList = new ArrayList<>(lattice.vertexSet());
		for (int i = 0 ; i < denotSetList.size() - 1 ; i++) {
			IPreconcept iDenotSet = denotSetList.get(i);
			for (int j = i+1 ; j < denotSetList.size() ; j++) {
				IPreconcept jDenotSet = denotSetList.get(j);
				if (iDenotSet.getExtent().containsAll(jDenotSet.getExtent()))
					lattice.addEdge(jDenotSet, iDenotSet);
				else if (jDenotSet.getExtent().containsAll(iDenotSet.getExtent()))
					lattice.addEdge(iDenotSet, jDenotSet);
			}
		}
	}	
	
	private Map<Set<IConstruct>, Set<IContextObject>> buildIntentToExtentRel() {
		Map<Set<IConstruct>, Set<IContextObject>> denotatingConstructsToExtents = new HashMap<>();
		Set<Set<IContextObject>> objectsPowerSet = buildObjectsPowerSet();
		for (Set<IContextObject> subset : objectsPowerSet) {
			Set<IConstruct> denotatingConstructs;
			if (subset.size() > 1)
				denotatingConstructs = 
					GeneratorsAbstractFactory.INSTANCE.getDenotationBuilder().input(subset).output();
			else if (subset.size() == 1)
				denotatingConstructs = new HashSet<IConstruct>(subset.iterator().next().getConstructs());
			else {
				denotatingConstructs = new HashSet<IConstruct>();
				for (IContextObject obj : objects)
					denotatingConstructs.addAll(obj.getConstructs());
			}
			if (denotatingConstructsToExtents.containsKey(denotatingConstructs))
				denotatingConstructsToExtents.get(denotatingConstructs).addAll(subset);
			else denotatingConstructsToExtents.put(denotatingConstructs, subset);
		}
		denotatingConstructsToExtents = 
				replaceEqualConstructsByUniqueInstanceAndNameVariables(denotatingConstructsToExtents);
		return denotatingConstructsToExtents;
	}	
	
	private Set<Set<IContextObject>> buildObjectsPowerSet() {
	    Set<Set<IContextObject>> powerSet = new HashSet<Set<IContextObject>>();
	    for (int i = 0; i < (1 << objects.size()); i++) {
	    	Set<IContextObject> subset = new HashSet<IContextObject>();
	        for (int j = 0; j < objects.size(); j++) {
	            if(((1 << j) & i) > 0)
	            	subset.add(objects.get(j));
	        }
	        powerSet.add(subset);
	    }
	    return powerSet;
	}	
	
	private Map<Set<IConstruct>, Set<IContextObject>> replaceEqualConstructsByUniqueInstanceAndNameVariables(
			Map<Set<IConstruct>, Set<IContextObject>> denotatingConstructsToExtents) {
		//reset variable name provider
		AVariable.resetVarNaming();
		/*
		 * Build a map such that all equal constructs will return the same construct instance
		 * (that will therefore be the reference instance). 
		 */
		int nbOfMappings = denotatingConstructsToExtents.size();
		Map<Set<IConstruct>, Set<IContextObject>> paramCopyWithRefInstancesAndNamedVars 
			= new HashMap<Set<IConstruct>, Set<IContextObject>>(nbOfMappings);
		Map<IConstruct, IConstruct> constructToRefInstance = new HashMap<>();
		for (Set<IConstruct> constructSet : denotatingConstructsToExtents.keySet()) {
			for (IConstruct construct : constructSet) {
				if (!constructToRefInstance.containsKey(construct)) {
					constructToRefInstance.put(construct, construct);
				}
			}
		}
		/*
		 * Build a copy of the parameter map with reference construct instances only in values, 
		 * and real variable names in constructs instead of placeholders.
		 * Must use transitory collections not based on hash tables, since variable naming
		 * will modify hashcodes.
		 */
		List<List<IConstruct>> setsOfReferenceConstrInstances = new ArrayList<List<IConstruct>>();
		List<Set<IContextObject>> listOfExtents = new ArrayList<Set<IContextObject>>();
		for (Entry<Set<IConstruct>, Set<IContextObject>> entry : denotatingConstructsToExtents.entrySet()) {
			List<IConstruct> withReferenceInstances = new ArrayList<>();
			for (IConstruct construct : entry.getKey()) {
				withReferenceInstances.add(constructToRefInstance.get(construct));
			}
			setsOfReferenceConstrInstances.add(withReferenceInstances);
			listOfExtents.add(entry.getValue());
		}
		//name variable, using side effects
		for (IConstruct refInstance : constructToRefInstance.values())
			refInstance.nameVariables();
		/*
		 * Populate the returned map with reference construct instances, whose 
		 * variables now have a name.
		 */
		for (int i = 0 ; i < setsOfReferenceConstrInstances.size() ; i++) {
			paramCopyWithRefInstancesAndNamedVars.put(
					new HashSet<>(setsOfReferenceConstrInstances.get(i)), listOfExtents.get(i));
		}
		return paramCopyWithRefInstancesAndNamedVars;
	}
	
	private void markRedundantDenotationsOfUSLPreconcepts() {
		for (IPreconcept preconcept : upperSemilattice) {
			MarkRedundantDenotations.of(preconcept);
		}
	}

	@Override
	public IPreconceptLatticeBuilder input(Collection<IContextObject> objects) {
		AVariable.resetVarNaming();
		this.objects = new ArrayList<>(objects);
		objectPreconcepts = new ArrayList<>(Arrays.asList(new IPreconcept[objects.size()]));
		lattice = new DirectedAcyclicGraph<>(null, IsA::new, false);
		buildLattice();
		IPreconcept truism = null;
		IPreconcept absurdity = null;
		for (IPreconcept preconcept : lattice.vertexSet()) {
			switch(preconcept.type()) {
				case TRUISM :
					truism = preconcept;
					break;
				case ABSURDITY :
					absurdity = preconcept;
					break;
				case PARTICULAR :
					objectPreconcepts.set(this.objects.indexOf(preconcept.getExtent().iterator().next()), preconcept);
					break;
				default : 
					break;
			}
		}
		this.truism = truism;
		this.absurdity = absurdity;
		ontologicalCommitment = new ThisPreconcept(new HashSet<>(objects));
		@SuppressWarnings("unchecked")
		DirectedAcyclicGraph<IPreconcept, IIsA> upperSemilattice = 
				(DirectedAcyclicGraph<IPreconcept, IIsA>) lattice.clone();
		upperSemilattice.removeVertex(absurdity);
		TransitiveReduction.INSTANCE.reduce(upperSemilattice);
		topologicalOrder = new ArrayList<>();
		new TopologicalOrderIterator<>(upperSemilattice).forEachRemaining(topologicalOrder::add);
		this.upperSemilattice = 
				new UpperSemilattice<>(upperSemilattice, truism, new HashSet<>(objectPreconcepts), topologicalOrder);
		this.upperSemilattice.addAsNewRoot(ontologicalCommitment, true);
		markRedundantDenotationsOfUSLPreconcepts();
		return this;
	}
	
	@Override
	public IPreconceptLattice output() {
		return new PreconceptLattice(
				objects, lattice, upperSemilattice, topologicalOrder, ontologicalCommitment, truism, 
				objectPreconcepts, absurdity);
	}

}
