package com.tregouet.occam.alg.builders.representations.concept_lattices.impl;

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

import com.tregouet.occam.alg.builders.representations.concept_lattices.ConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.representations.concept_lattices.utils.MarkRedundantDenotations;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.languages.words.construct.IConstruct;
import com.tregouet.occam.data.representations.concepts.ConceptType;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.concepts.impl.Concept;
import com.tregouet.occam.data.representations.concepts.impl.ConceptLattice;
import com.tregouet.occam.data.representations.concepts.impl.Everything;
import com.tregouet.occam.data.representations.concepts.impl.IsA;
import com.tregouet.tree_finder.data.InvertedUpperSemilattice;

public class GaloisConnection implements ConceptLatticeBuilder {

	private List<IContextObject> objects = null;
	private DirectedAcyclicGraph<IConcept, IIsA> lattice = null;
	private InvertedUpperSemilattice<IConcept, IIsA> invertedUpperSemilattice = null;
	private IConcept ontologicalCommitment = null;
	private List<IConcept> topologicalOrder = null;
	private IConcept truism = null;
	private List<IConcept> particulars = null;
	private IConcept absurdity = null;
	
	public GaloisConnection() {
	}
	
	private void buildLattice() {
		Map<Set<IConstruct>, Set<IContextObject>> denotatingConstructsToExtents = buildIntentToExtentRel();
		for (Entry<Set<IConstruct>, Set<IContextObject>> entry : denotatingConstructsToExtents.entrySet()) {
			IConcept concept = new Concept(entry.getKey(), entry.getValue());
			if (!concept.getExtent().isEmpty()) {
				if (concept.getExtent().size() == 1)
					concept.setType(ConceptType.PARTICULAR);
				else if (concept.getExtent().size() == objects.size()) {
					concept.setType(ConceptType.TRUISM);
				}
				else {
					concept.setType(ConceptType.UNIVERSAL);
				}
			}
			else {
				concept.setType(ConceptType.ABSURDITY);
			}
			lattice.addVertex(concept);
		}
		List<IConcept> denotSetList = new ArrayList<>(lattice.vertexSet());
		for (int i = 0 ; i < denotSetList.size() - 1 ; i++) {
			IConcept iDenotSet = denotSetList.get(i);
			for (int j = i+1 ; j < denotSetList.size() ; j++) {
				IConcept jDenotSet = denotSetList.get(j);
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
				denotatingConstructs = ConceptLatticeBuilder.denotationBuilder().apply(subset);
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
	
	private void markRedundantDenotationsOfUSLConcepts() {
		for (IConcept concept : invertedUpperSemilattice) {
			MarkRedundantDenotations.of(concept);
		}
	}

	@Override
	public IConceptLattice apply(Collection<IContextObject> objects) {
		AVariable.resetVarNaming();
		this.objects = new ArrayList<>(objects);
		particulars = new ArrayList<>(Arrays.asList(new IConcept[objects.size()]));
		lattice = new DirectedAcyclicGraph<>(null, IsA::new, false);
		buildLattice();
		IConcept truism = null;
		IConcept absurdity = null;
		for (IConcept concept : lattice.vertexSet()) {
			switch(concept.type()) {
				case TRUISM :
					truism = concept;
					break;
				case ABSURDITY :
					absurdity = concept;
					break;
				case PARTICULAR :
					particulars.set(this.objects.indexOf(concept.getExtent().iterator().next()), concept);
					break;
				default : 
					break;
			}
		}
		this.truism = truism;
		this.absurdity = absurdity;
		ontologicalCommitment = new Everything(new HashSet<>(objects));
		@SuppressWarnings("unchecked")
		DirectedAcyclicGraph<IConcept, IIsA> upperSemilattice = 
				(DirectedAcyclicGraph<IConcept, IIsA>) lattice.clone();
		upperSemilattice.removeVertex(absurdity);
		TransitiveReduction.INSTANCE.reduce(upperSemilattice);
		topologicalOrder = new ArrayList<>();
		new TopologicalOrderIterator<>(upperSemilattice).forEachRemaining(topologicalOrder::add);
		this.invertedUpperSemilattice = 
				new InvertedUpperSemilattice<>(upperSemilattice, truism, new HashSet<>(particulars), topologicalOrder);
		this.invertedUpperSemilattice.addAsNewRoot(ontologicalCommitment, true);
		markRedundantDenotationsOfUSLConcepts();
		return output();
	}
	
	private IConceptLattice output() {
		return new ConceptLattice(
				objects, lattice, invertedUpperSemilattice, topologicalOrder, ontologicalCommitment, truism, 
				particulars, absurdity);
	}

}
