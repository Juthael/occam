package com.tregouet.occam.alg.concepts_gen.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.jgrapht.Graphs;
import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.concepts_gen.IConceptsConstructionManager;
import com.tregouet.occam.alg.concepts_gen.utils.ConceptIntentBldr;
import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IContextObject;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.data.concepts.impl.Concept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.languages.generic.impl.Variable;
import com.tregouet.tree_finder.data.UpperSemilattice;

public class ConceptsConstructionManager implements IConceptsConstructionManager {

	private List<IContextObject> objects = null;
	private DirectedAcyclicGraph<IConcept, IIsA> lattice = null;
	private UpperSemilattice<IConcept, IIsA> upperSemilattice = null;
	private IConcept ontologicalCommitment = null;
	private List<IConcept> topologicalOrder = null;
	private IConcept truism = null;
	private List<IConcept> objectConcepts = null;
	private IConcept absurdity = null;
	
	public ConceptsConstructionManager() {
	}
	
	@Override
	public List<IContextObject> getObjects() {
		return objects;
	}

	@Override
	public DirectedAcyclicGraph<IConcept, IIsA> getLattice() {
		return lattice;
	}

	@Override
	public UpperSemilattice<IConcept, IIsA> getUpperSemilattice() {
		return upperSemilattice;
	}

	@Override
	public IConcept getOntologicalCommitment() {
		return ontologicalCommitment;
	}

	@Override
	public List<IConcept> getTopologicalOrder() {
		return topologicalOrder;
	}

	@Override
	public IConcept getTruism() {
		return truism;
	}

	@Override
	public List<IConcept> getObjectConcepts() {
		return objectConcepts;
	}

	@Override
	public IConcept getAbsurdity() {
		return absurdity;
	}
	
	private void buildLattice() {
		Map<Set<IConstruct>, Set<IContextObject>> denotatingConstructsToExtents = buildIntentToExtentRel();
		for (Entry<Set<IConstruct>, Set<IContextObject>> entry : denotatingConstructsToExtents.entrySet()) {
			IConcept concept = new Concept(entry.getKey(), entry.getValue());
			if (!concept.getExtent().isEmpty()) {
				if (concept.getExtent().size() == 1)
					concept.setType(IConcept.OBJECT);
				else if (concept.getExtent().size() == objects.size()) {
					concept.setType(IConcept.TRUISM);
				}
				else {
					concept.setType(IConcept.CONTEXT_SUBSET);
				}
			}
			else {
				concept.setType(IConcept.ABSURDITY);
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
				denotatingConstructs = ConceptIntentBldr.getDenotations(subset);
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
		denotatingConstructsToExtents = singularizeConstructs(denotatingConstructsToExtents);
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
	
	private Map<Set<IConstruct>, Set<IContextObject>> singularizeConstructs(
			Map<Set<IConstruct>, Set<IContextObject>> denotatingConstructsToExtents) {
		//HERE
		AVariable.resetVarNaming();
		Map<Set<IConstruct>, Set<IContextObject>> mapWithSingularizedIntents 
			= new HashMap<Set<IConstruct>, Set<IContextObject>>();
		/*
		 * must use transitory collections not based on hash tables, since variable naming
		 * will modify hashcodes  
		 */
		List<Set<IConstruct>> constructSets = new ArrayList<Set<IConstruct>>();
		List<Set<IConstruct>> singularizedConstructSets = new ArrayList<Set<IConstruct>>();
		List<Set<IContextObject>> listOfExtents = new ArrayList<Set<IContextObject>>();
		for (Entry<Set<IConstruct>, Set<IContextObject>> entry : denotatingConstructsToExtents.entrySet()) {
			constructSets.add(entry.getKey());
			listOfExtents.add(entry.getValue());
		}
		for (Set<IConstruct> constructSet : constructSets) {
			Set<IConstruct> singularizedConstructs = new HashSet<IConstruct>();
			for (IConstruct construct : constructSet) {
				construct.nameVariables();
				singularizedConstructs.add(construct);
			}
			singularizedConstructSets.add(singularizedConstructs);
		}
		for (int i = 0 ; i < singularizedConstructSets.size() ; i++) {
			mapWithSingularizedIntents.put(singularizedConstructSets.get(i), listOfExtents.get(i));
		}
		return mapWithSingularizedIntents;
	}	
	
	private IConcept instantiateOntologicalCommitment() {
		IConcept ontologicalCommitment;
		ISymbol variable = Variable.getInitialVariable();
		List<ISymbol> word = new ArrayList<ISymbol>();
		word.add(variable);
		IConstruct denotatingConstruct = new Construct(word);
		Set<IConstruct> denotatingConstructs =  new HashSet<IConstruct>();
		denotatingConstructs.add(denotatingConstruct);
		ontologicalCommitment = new Concept(denotatingConstructs, new HashSet<IContextObject>(objects));
		ontologicalCommitment.setType(IConcept.ONTOLOGICAL_COMMITMENT);
		return ontologicalCommitment;
	}
	
	private void updateConceptRank(IConcept concept, int rank) {
		if (concept.rank() < rank || concept.type() == IConcept.ABSURDITY) {
			concept.setRank(rank);
			for (IConcept successor : Graphs.successorListOf(upperSemilattice, concept)) {
				updateConceptRank(successor, rank + 1);
			}
		}
	}

	@Override
	public IConceptsConstructionManager input(Collection<IContextObject> objects) {
		AVariable.resetVarNaming();
		this.objects = new ArrayList<>(objects);
		objectConcepts = new ArrayList<>(Arrays.asList(new IConcept[objects.size()]));
		lattice = new DirectedAcyclicGraph<>(null, IsA::new, false);
		buildLattice();
		IConcept truism = null;
		IConcept absurdity = null;
		for (IConcept concept : lattice.vertexSet()) {
			switch(concept.type()) {
				case IConcept.TRUISM :
					truism = concept;
					break;
				case IConcept.ABSURDITY :
					absurdity = concept;
					break;
				case IConcept.OBJECT :
					objectConcepts.set(this.objects.indexOf(concept.getExtent().iterator().next()), concept);
					break;
			}
		}
		this.truism = truism;
		this.absurdity = absurdity;
		ontologicalCommitment = instantiateOntologicalCommitment();
		@SuppressWarnings("unchecked")
		DirectedAcyclicGraph<IConcept, IIsA> upperSemilattice = 
				(DirectedAcyclicGraph<IConcept, IIsA>) lattice.clone();
		upperSemilattice.removeVertex(absurdity);
		TransitiveReduction.INSTANCE.reduce(upperSemilattice);
		topologicalOrder = new ArrayList<>();
		new TopologicalOrderIterator<>(upperSemilattice).forEachRemaining(topologicalOrder::add);
		this.upperSemilattice = 
				new UpperSemilattice<>(upperSemilattice, truism, new HashSet<>(objectConcepts), topologicalOrder);
		this.upperSemilattice.addAsNewRoot(ontologicalCommitment, true);
		for (IConcept objectDenotationSet : objectConcepts)
			updateConceptRank(objectDenotationSet, 1);
		return this;
	}

}
