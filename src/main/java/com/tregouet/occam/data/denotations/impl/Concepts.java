package com.tregouet.occam.data.denotations.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.denotation_sets_gen.IConstrainedConceptTreeSupplier;
import com.tregouet.occam.alg.denotation_sets_gen.IConceptTreeSupplier;
import com.tregouet.occam.alg.denotation_sets_gen.impl.DenotationSetsTreeSupplier;
import com.tregouet.occam.alg.denotation_sets_gen.utils.DenotatingConstructBldr;
import com.tregouet.occam.data.denotations.IContextObject;
import com.tregouet.occam.data.denotations.IConcept;
import com.tregouet.occam.data.denotations.IConcepts;
import com.tregouet.occam.data.denotations.IExtentStructureConstraint;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.languages.ISymbol;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.languages.generic.impl.Variable;
import com.tregouet.tree_finder.data.UpperSemilattice;

public class Concepts implements IConcepts {
	
	private final List<IContextObject> objects;
	private final DirectedAcyclicGraph<IConcept, IIsA> lattice;
	private final UpperSemilattice<IConcept, IIsA> upperSemilattice;
	private final IConcept ontologicalCommitment;
	private final List<IConcept> topologicalOrder;
	private final IConcept truism;
	private final List<IConcept> objectDenotationSets;
	private final IConcept absurdity;
	
	@SuppressWarnings("unchecked")
	public Concepts(Collection<IContextObject> objects) {
		this.objects = new ArrayList<>(objects);
		objectDenotationSets = new ArrayList<>(Arrays.asList(new IConcept[objects.size()]));
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
					objectDenotationSets.set(this.objects.indexOf(concept.getExtent().iterator().next()), concept);
					break;
			}
		}
		this.truism = truism;
		this.absurdity = absurdity;
		ontologicalCommitment = instantiateOntologicalCommitment();
		DirectedAcyclicGraph<IConcept, IIsA> upperSemilattice = 
				(DirectedAcyclicGraph<IConcept, IIsA>) lattice.clone();
		upperSemilattice.removeVertex(absurdity);
		TransitiveReduction.INSTANCE.reduce(upperSemilattice);
		topologicalOrder = new ArrayList<>();
		new TopologicalOrderIterator<>(upperSemilattice).forEachRemaining(topologicalOrder::add);
		this.upperSemilattice = 
				new UpperSemilattice<>(upperSemilattice, truism, new HashSet<>(objectDenotationSets), topologicalOrder);
		this.upperSemilattice.addAsNewRoot(ontologicalCommitment, true);
		for (IConcept objectDenotationSet : objectDenotationSets)
			updateDenotationSetRank(objectDenotationSet, 1);
	}

	@Override
	public boolean areA(List<IConcept> concepts, IConcept concept) {
		boolean areA = true;
		int dSIndex = 0;
		while (areA && dSIndex < concepts.size()) {
			areA = isA(concepts.get(dSIndex), concept);
			dSIndex++;
		}
		return areA;
	}

	@Override
	public IConcept getAbsurdity() {
		return absurdity;
	}

	@Override
	public IConceptTreeSupplier getConceptTreeSupplier() throws IOException {
		return new DenotationSetsTreeSupplier(upperSemilattice, ontologicalCommitment);
	}

	@Override
	public DirectedAcyclicGraph<IConcept, IIsA> getLatticeOfConcepts() {
		return lattice;
	}

	@Override
	public IConcept getConceptWithExtent(Set<IContextObject> extent) {
		if (extent.containsAll(objects))
			return truism;
		for (IConcept concept : topologicalOrder) {
			if (concept.getExtent().equals(extent))
				return concept;
		}
		return null;
	}

	@Override
	public IConstrainedConceptTreeSupplier getConstrainedConceptTreeSupplier(
			IExtentStructureConstraint constraint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IContextObject> getContextObjects() {
		return objects;
	}

	@Override
	public IConcept getLeastCommonSuperordinate(Set<IConcept> concepts) {
		if (concepts.isEmpty())
			return null;
		List<IConcept> denotSetList = removeSubCategories(concepts);
		if (denotSetList.size() == 1)
			return denotSetList.get(0);
		IConcept leastCommonSuperordinate = null;
		ListIterator<IConcept> denotSetIterator = topologicalOrder.listIterator(topologicalOrder.size());
		boolean abortSearch = false;
		while (denotSetIterator.hasPrevious() && !abortSearch) {
			IConcept current = denotSetIterator.previous();
			if (areA(denotSetList, current))
				leastCommonSuperordinate = current;
			else if (concepts.contains(current))
				abortSearch = true;
		}
		return leastCommonSuperordinate;		
	}

	@Override
	public IConcept getOntologicalCommitment() {
		return ontologicalCommitment;
	}
	
	@Override
	public UpperSemilattice<IConcept, IIsA> getOntologicalUpperSemilattice() {
		return upperSemilattice;
	}
	
	@Override
	public List<IConcept> getObjectConcepts() {
		return objectDenotationSets;
	}	
	
	@Override
	public List<IConcept> getTopologicalSorting() {
		return topologicalOrder;
	}
	
	@Override
	public DirectedAcyclicGraph<IConcept, IIsA> getTransitiveReduction() {
		return upperSemilattice;
	}
	
	@Override
	public IConcept getTruism() {
		return truism;
	}

	@Override
	public boolean isA(IConcept denotationSet1, IConcept denotationSet2) {
		boolean isA = false;
		if (topologicalOrder.indexOf(denotationSet1) < topologicalOrder.indexOf(denotationSet2)) {
			BreadthFirstIterator<IConcept, IIsA> iterator = 
					new BreadthFirstIterator<>(upperSemilattice, denotationSet1);
			iterator.next();
			while (!isA && iterator.hasNext())
				isA = denotationSet2.equals(iterator.next());
		}
		return isA;		
	}

	@Override
	public boolean isADirectSubordinateOf(IConcept denotationSet1, IConcept denotationSet2) {
		return (upperSemilattice.getEdge(denotationSet1, denotationSet2) != null);
	}
	
	private Map<Set<IConstruct>, Set<IContextObject>> buildIntentToExtentRel() {
		Map<Set<IConstruct>, Set<IContextObject>> denotatingConstructsToExtents = new HashMap<>();
		Set<Set<IContextObject>> objectsPowerSet = buildObjectsPowerSet();
		for (Set<IContextObject> subset : objectsPowerSet) {
			Set<IConstruct> denotatingConstructs;
			if (subset.size() > 1)
				denotatingConstructs = DenotatingConstructBldr.getDenotatingConstructs(subset);
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

	private IConcept instantiateOntologicalCommitment() {
		IConcept ontologicalCommitment;
		ISymbol variable = new Variable(!AVariable.DEFERRED_NAMING);
		List<ISymbol> word = new ArrayList<ISymbol>();
		word.add(variable);
		IConstruct denotatingConstruct = new Construct(word);
		Set<IConstruct> denotatingConstructs =  new HashSet<IConstruct>();
		denotatingConstructs.add(denotatingConstruct);
		ontologicalCommitment = new Concept(denotatingConstructs, new HashSet<IContextObject>(objects));
		ontologicalCommitment.setType(IConcept.ONTOLOGICAL_COMMITMENT);
		return ontologicalCommitment;
	}

	private List<IConcept> removeSubCategories(Set<IConcept> concepts) {
		List<IConcept> denotSetList = new ArrayList<>(concepts);
		for (int i = 0 ; i < denotSetList.size() - 1 ; i++) {
			IConcept iDenotSet = denotSetList.get(i);
			for (int j = i+1 ; j < denotSetList.size() ; j++) {
				IConcept jDenotSet = denotSetList.get(j);
				if (isA(iDenotSet, jDenotSet))
					concepts.remove(iDenotSet);
				else if (isA(jDenotSet, iDenotSet))
					concepts.remove(jDenotSet);
			}
		}
		return new ArrayList<>(concepts);
	}

	private Map<Set<IConstruct>, Set<IContextObject>> singularizeConstructs(
			Map<Set<IConstruct>, Set<IContextObject>> denotatingConstructsToExtents) {
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

	private void updateDenotationSetRank(IConcept concept, int rank) {
		if (concept.rank() < rank || concept.type() == IConcept.ABSURDITY) {
			concept.setRank(rank);
			for (IConcept successor : Graphs.successorListOf(upperSemilattice, concept)) {
				updateDenotationSetRank(successor, rank + 1);
			}
		}
	}

}
