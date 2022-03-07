package com.tregouet.occam.alg.preconcepts_gen.impl;

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

import com.tregouet.occam.alg.preconcepts_gen.IPreconceptsConstructionManager;
import com.tregouet.occam.alg.preconcepts_gen.utils.PreconceptDenotationsBldr;
import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.languages.generic.impl.Variable;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.preconcepts.impl.IsA;
import com.tregouet.occam.data.preconcepts.impl.Preconcept;
import com.tregouet.tree_finder.data.UpperSemilattice;

public class PreconceptsConstructionManager implements IPreconceptsConstructionManager {

	private List<IContextObject> objects = null;
	private DirectedAcyclicGraph<IPreconcept, IIsA> lattice = null;
	private UpperSemilattice<IPreconcept, IIsA> upperSemilattice = null;
	private IPreconcept ontologicalCommitment = null;
	private List<IPreconcept> topologicalOrder = null;
	private IPreconcept truism = null;
	private List<IPreconcept> objectPreconcepts = null;
	private IPreconcept absurdity = null;
	
	public PreconceptsConstructionManager() {
	}
	
	@Override
	public List<IContextObject> getObjects() {
		return objects;
	}

	@Override
	public DirectedAcyclicGraph<IPreconcept, IIsA> getLattice() {
		return lattice;
	}

	@Override
	public UpperSemilattice<IPreconcept, IIsA> getUpperSemilattice() {
		return upperSemilattice;
	}

	@Override
	public IPreconcept getOntologicalCommitment() {
		return ontologicalCommitment;
	}

	@Override
	public List<IPreconcept> getTopologicalOrder() {
		return topologicalOrder;
	}

	@Override
	public IPreconcept getTruism() {
		return truism;
	}

	@Override
	public List<IPreconcept> getObjectPreconcepts() {
		return objectPreconcepts;
	}

	@Override
	public IPreconcept getAbsurdity() {
		return absurdity;
	}
	
	private void buildLattice() {
		Map<Set<IConstruct>, Set<IContextObject>> denotatingConstructsToExtents = buildIntentToExtentRel();
		for (Entry<Set<IConstruct>, Set<IContextObject>> entry : denotatingConstructsToExtents.entrySet()) {
			IPreconcept preconcept = new Preconcept(entry.getKey(), entry.getValue());
			if (!preconcept.getExtent().isEmpty()) {
				if (preconcept.getExtent().size() == 1)
					preconcept.setType(IPreconcept.OBJECT);
				else if (preconcept.getExtent().size() == objects.size()) {
					preconcept.setType(IPreconcept.TRUISM);
				}
				else {
					preconcept.setType(IPreconcept.CONTEXT_SUBSET);
				}
			}
			else {
				preconcept.setType(IPreconcept.ABSURDITY);
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
				denotatingConstructs = PreconceptDenotationsBldr.getDenotations(subset);
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
	
	private IPreconcept instantiateOntologicalCommitment() {
		IPreconcept ontologicalCommitment;
		ISymbol variable = Variable.getInitialVariable();
		List<ISymbol> word = new ArrayList<ISymbol>();
		word.add(variable);
		IConstruct denotatingConstruct = new Construct(word);
		Set<IConstruct> denotatingConstructs =  new HashSet<IConstruct>();
		denotatingConstructs.add(denotatingConstruct);
		ontologicalCommitment = new Preconcept(denotatingConstructs, new HashSet<IContextObject>(objects));
		ontologicalCommitment.setType(IPreconcept.ONTOLOGICAL_COMMITMENT);
		return ontologicalCommitment;
	}

	@Override
	public IPreconceptsConstructionManager input(Collection<IContextObject> objects) {
		AVariable.resetVarNaming();
		this.objects = new ArrayList<>(objects);
		objectPreconcepts = new ArrayList<>(Arrays.asList(new IPreconcept[objects.size()]));
		lattice = new DirectedAcyclicGraph<>(null, IsA::new, false);
		buildLattice();
		IPreconcept truism = null;
		IPreconcept absurdity = null;
		for (IPreconcept preconcept : lattice.vertexSet()) {
			switch(preconcept.type()) {
				case IPreconcept.TRUISM :
					truism = preconcept;
					break;
				case IPreconcept.ABSURDITY :
					absurdity = preconcept;
					break;
				case IPreconcept.OBJECT :
					objectPreconcepts.set(this.objects.indexOf(preconcept.getExtent().iterator().next()), preconcept);
					break;
			}
		}
		this.truism = truism;
		this.absurdity = absurdity;
		ontologicalCommitment = instantiateOntologicalCommitment();
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
		return this;
	}

}
