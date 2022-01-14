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

import com.tregouet.occam.alg.denotation_sets_gen.IConstrainedDenotationSetsTreeSupplier;
import com.tregouet.occam.alg.denotation_sets_gen.IDenotationSetsTreeSupplier;
import com.tregouet.occam.alg.denotation_sets_gen.impl.DenotationSetsTreeSupplier;
import com.tregouet.occam.alg.denotation_sets_gen.utils.DenotatingConstructBldr;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotationSets;
import com.tregouet.occam.data.denotations.IExtentStructureConstraint;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.IContextObject;
import com.tregouet.occam.data.languages.generic.ISymbol;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.languages.generic.impl.Variable;
import com.tregouet.tree_finder.data.UpperSemilattice;

public class DenotationSets implements IDenotationSets {
	
	private final List<IContextObject> objects;
	private final DirectedAcyclicGraph<IDenotationSet, IIsA> lattice;
	private final UpperSemilattice<IDenotationSet, IIsA> upperSemilattice;
	private final IDenotationSet ontologicalCommitment;
	private final List<IDenotationSet> topologicalOrder;
	private final IDenotationSet truism;
	private final List<IDenotationSet> objectDenotationSets;
	private final IDenotationSet absurdity;
	
	@SuppressWarnings("unchecked")
	public DenotationSets(Collection<IContextObject> objects) {
		this.objects = new ArrayList<>(objects);
		objectDenotationSets = new ArrayList<>(Arrays.asList(new IDenotationSet[objects.size()]));
		lattice = new DirectedAcyclicGraph<>(null, IsA::new, false);
		buildLattice();
		IDenotationSet truism = null;
		IDenotationSet absurdity = null;
		for (IDenotationSet denotationSet : lattice.vertexSet()) {
			switch(denotationSet.type()) {
				case IDenotationSet.TRUISM :
					truism = denotationSet;
					break;
				case IDenotationSet.ABSURDITY :
					absurdity = denotationSet;
					break;
				case IDenotationSet.OBJECT :
					objectDenotationSets.set(this.objects.indexOf(denotationSet.getExtent().iterator().next()), denotationSet);
					break;
			}
		}
		this.truism = truism;
		this.absurdity = absurdity;
		ontologicalCommitment = instantiateOntologicalCommitment();
		DirectedAcyclicGraph<IDenotationSet, IIsA> upperSemilattice = 
				(DirectedAcyclicGraph<IDenotationSet, IIsA>) lattice.clone();
		upperSemilattice.removeVertex(absurdity);
		TransitiveReduction.INSTANCE.reduce(upperSemilattice);
		topologicalOrder = new ArrayList<>();
		new TopologicalOrderIterator<>(upperSemilattice).forEachRemaining(topologicalOrder::add);
		this.upperSemilattice = 
				new UpperSemilattice<>(upperSemilattice, truism, new HashSet<>(objectDenotationSets), topologicalOrder);
		this.upperSemilattice.addAsNewRoot(ontologicalCommitment, true);
		for (IDenotationSet objectDenotationSet : objectDenotationSets)
			updateDenotationSetRank(objectDenotationSet, 1);
	}

	@Override
	public boolean areA(List<IDenotationSet> denotationSets, IDenotationSet denotationSet) {
		boolean areA = true;
		int dSIndex = 0;
		while (areA && dSIndex < denotationSets.size()) {
			areA = isA(denotationSets.get(dSIndex), denotationSet);
			dSIndex++;
		}
		return areA;
	}

	@Override
	public IDenotationSet getAbsurdity() {
		return absurdity;
	}

	@Override
	public IDenotationSetsTreeSupplier getDenotationSetsTreeSupplier() throws IOException {
		return new DenotationSetsTreeSupplier(upperSemilattice, ontologicalCommitment);
	}

	@Override
	public DirectedAcyclicGraph<IDenotationSet, IIsA> getLatticeOfDenotationSets() {
		return lattice;
	}

	@Override
	public IDenotationSet getDenotationSetWithExtent(Set<IContextObject> extent) {
		if (extent.containsAll(objects))
			return truism;
		for (IDenotationSet denotationSet : topologicalOrder) {
			if (denotationSet.getExtent().equals(extent))
				return denotationSet;
		}
		return null;
	}

	@Override
	public IConstrainedDenotationSetsTreeSupplier getConstrainedDenotationSetsTreeSupplier(
			IExtentStructureConstraint constraint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IContextObject> getContextObjects() {
		return objects;
	}

	@Override
	public IDenotationSet getLeastCommonSuperordinate(Set<IDenotationSet> denotationSets) {
		if (denotationSets.isEmpty())
			return null;
		List<IDenotationSet> denotSetList = removeSubCategories(denotationSets);
		if (denotSetList.size() == 1)
			return denotSetList.get(0);
		IDenotationSet leastCommonSuperordinate = null;
		ListIterator<IDenotationSet> denotSetIterator = topologicalOrder.listIterator(topologicalOrder.size());
		boolean abortSearch = false;
		while (denotSetIterator.hasPrevious() && !abortSearch) {
			IDenotationSet current = denotSetIterator.previous();
			if (areA(denotSetList, current))
				leastCommonSuperordinate = current;
			else if (denotationSets.contains(current))
				abortSearch = true;
		}
		return leastCommonSuperordinate;		
	}

	@Override
	public IDenotationSet getOntologicalCommitment() {
		return ontologicalCommitment;
	}
	
	@Override
	public UpperSemilattice<IDenotationSet, IIsA> getOntologicalUpperSemilattice() {
		return upperSemilattice;
	}
	
	@Override
	public List<IDenotationSet> getObjectDenotationSets() {
		return objectDenotationSets;
	}	
	
	@Override
	public List<IDenotationSet> getTopologicalSorting() {
		return topologicalOrder;
	}
	
	@Override
	public DirectedAcyclicGraph<IDenotationSet, IIsA> getTransitiveReduction() {
		return upperSemilattice;
	}
	
	@Override
	public IDenotationSet getTruism() {
		return truism;
	}

	@Override
	public boolean isA(IDenotationSet denotationSet1, IDenotationSet denotationSet2) {
		boolean isA = false;
		if (topologicalOrder.indexOf(denotationSet1) < topologicalOrder.indexOf(denotationSet2)) {
			BreadthFirstIterator<IDenotationSet, IIsA> iterator = 
					new BreadthFirstIterator<>(upperSemilattice, denotationSet1);
			iterator.next();
			while (!isA && iterator.hasNext())
				isA = denotationSet2.equals(iterator.next());
		}
		return isA;		
	}

	@Override
	public boolean isADirectSubordinateOf(IDenotationSet denotationSet1, IDenotationSet denotationSet2) {
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
			IDenotationSet denotationSet = new DenotationSet(entry.getKey(), entry.getValue());
			if (!denotationSet.getExtent().isEmpty()) {
				if (denotationSet.getExtent().size() == 1)
					denotationSet.setType(IDenotationSet.OBJECT);
				else if (denotationSet.getExtent().size() == objects.size()) {
					denotationSet.setType(IDenotationSet.TRUISM);
				}
				else {
					denotationSet.setType(IDenotationSet.CONTEXT_SUBSET);
				}
			}
			else {
				denotationSet.setType(IDenotationSet.ABSURDITY);
			}
			lattice.addVertex(denotationSet);
		}
		List<IDenotationSet> denotSetList = new ArrayList<>(lattice.vertexSet());
		for (int i = 0 ; i < denotSetList.size() - 1 ; i++) {
			IDenotationSet iDenotSet = denotSetList.get(i);
			for (int j = i+1 ; j < denotSetList.size() ; j++) {
				IDenotationSet jDenotSet = denotSetList.get(j);
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

	private IDenotationSet instantiateOntologicalCommitment() {
		IDenotationSet ontologicalCommitment;
		ISymbol variable = new Variable(!AVariable.DEFERRED_NAMING);
		List<ISymbol> word = new ArrayList<ISymbol>();
		word.add(variable);
		IConstruct denotatingConstruct = new Construct(word);
		Set<IConstruct> denotatingConstructs =  new HashSet<IConstruct>();
		denotatingConstructs.add(denotatingConstruct);
		ontologicalCommitment = new DenotationSet(denotatingConstructs, new HashSet<IContextObject>(objects));
		ontologicalCommitment.setType(IDenotationSet.ONTOLOGICAL_COMMITMENT);
		return ontologicalCommitment;
	}

	private List<IDenotationSet> removeSubCategories(Set<IDenotationSet> denotationSets) {
		List<IDenotationSet> denotSetList = new ArrayList<>(denotationSets);
		for (int i = 0 ; i < denotSetList.size() - 1 ; i++) {
			IDenotationSet iDenotSet = denotSetList.get(i);
			for (int j = i+1 ; j < denotSetList.size() ; j++) {
				IDenotationSet jDenotSet = denotSetList.get(j);
				if (isA(iDenotSet, jDenotSet))
					denotationSets.remove(iDenotSet);
				else if (isA(jDenotSet, iDenotSet))
					denotationSets.remove(jDenotSet);
			}
		}
		return new ArrayList<>(denotationSets);
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

	private void updateDenotationSetRank(IDenotationSet denotationSet, int rank) {
		if (denotationSet.rank() < rank || denotationSet.type() == IDenotationSet.ABSURDITY) {
			denotationSet.setRank(rank);
			for (IDenotationSet successor : Graphs.successorListOf(upperSemilattice, denotationSet)) {
				updateDenotationSetRank(successor, rank + 1);
			}
		}
	}

}
