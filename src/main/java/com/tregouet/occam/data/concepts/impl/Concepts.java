package com.tregouet.occam.data.concepts.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.tregouet.occam.alg.conceptual_structure_gen.IConstrainedClassificationSupplier;
import com.tregouet.occam.alg.conceptual_structure_gen.IClassificationSupplier;
import com.tregouet.occam.alg.conceptual_structure_gen.impl.ClassificationSupplier;
import com.tregouet.occam.alg.conceptual_structure_gen.utils.IntentBldr;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IExtentStructureConstraint;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.IContextObject;
import com.tregouet.occam.data.languages.generic.ISymbol;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.languages.generic.impl.Variable;
import com.tregouet.tree_finder.data.UpperSemilattice;

public class Concepts implements IConcepts {
	
	private final List<IContextObject> objects;
	private final DirectedAcyclicGraph<IConcept, IsA> lattice;
	private final UpperSemilattice<IConcept, IsA> conceptUSL;
	private final IConcept ontologicalCommitment;
	private final List<IConcept> topologicalOrder;
	private final IConcept truism;
	private final List<IConcept> singletons;
	private final IConcept absurdity;
	
	@SuppressWarnings("unchecked")
	public Concepts(List<IContextObject> objects) {
		this.objects = objects;
		singletons = new ArrayList<>(Arrays.asList(new IConcept[objects.size()]));
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
				case IConcept.SINGLETON :
					singletons.set(objects.indexOf(concept.getExtent().iterator().next()), concept);
					break;
			}
		}
		this.truism = truism;
		this.absurdity = absurdity;
		ontologicalCommitment = instantiateOntologicalCommitment();
		DirectedAcyclicGraph<IConcept, IsA> ontologicalUSL = 
				(DirectedAcyclicGraph<IConcept, IsA>) lattice.clone();
		ontologicalUSL.removeVertex(absurdity);
		TransitiveReduction.INSTANCE.reduce(ontologicalUSL);
		List<IConcept> topologicalOrderedSet = new ArrayList<>();
		new TopologicalOrderIterator<>(ontologicalUSL).forEachRemaining(topologicalOrderedSet::add);
		this.conceptUSL = 
				new UpperSemilattice<>(ontologicalUSL, truism, new HashSet<>(singletons), topologicalOrderedSet);
		this.conceptUSL.addAsNewRoot(ontologicalCommitment, true);
		for (IConcept objectCat : singletons)
			updateCategoryRank(objectCat, 1);
		topologicalOrder = this.conceptUSL.getTopologicalOrder();
	}

	@Override
	public boolean areA(List<IConcept> concepts, IConcept concept) {
		boolean areA = true;
		int conIndex = 0;
		while (areA && conIndex < concepts.size()) {
			areA = isA(concepts.get(conIndex), concept);
			conIndex++;
		}
		return areA;
	}

	@Override
	public IConcept getAbsurdity() {
		return absurdity;
	}

	@Override
	public DirectedAcyclicGraph<IConcept, IsA> getCategoryLattice() {
		return lattice;
	}

	@Override
	public IClassificationSupplier getClassificationSupplier() throws IOException {
		return new ClassificationSupplier(
				conceptUSL, singletons, ontologicalCommitment);
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
	public IConstrainedClassificationSupplier getConstrainedClassificationSupplier(
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
		List<IConcept> conList = removeSubCategories(concepts);
		if (conList.size() == 1)
			return conList.get(0);
		IConcept leastCommonSuperordinate = null;
		ListIterator<IConcept> conIterator = topologicalOrder.listIterator(topologicalOrder.size());
		boolean abortSearch = false;
		while (conIterator.hasPrevious() && !abortSearch) {
			IConcept current = conIterator.previous();
			if (areA(conList, current))
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
	public UpperSemilattice<IConcept, IsA> getOntologicalUpperSemilattice() {
		return conceptUSL;
	}
	
	@Override
	public List<IConcept> getSingletonConcept() {
		return singletons;
	}	
	
	@Override
	public List<IConcept> getTopologicalSorting() {
		return topologicalOrder;
	}
	
	@Override
	public DirectedAcyclicGraph<IConcept, IsA> getTransitiveReduction() {
		return conceptUSL;
	}
	
	@Override
	public IConcept getTruism() {
		return truism;
	}

	@Override
	public boolean isA(IConcept concept1, IConcept concept2) {
		boolean isA = false;
		if (topologicalOrder.indexOf(concept1) < topologicalOrder.indexOf(concept2)) {
			BreadthFirstIterator<IConcept, IsA> iterator = 
					new BreadthFirstIterator<>(conceptUSL, concept1);
			iterator.next();
			while (!isA && iterator.hasNext())
				isA = concept2.equals(iterator.next());
		}
		return isA;		
	}

	@Override
	public boolean isADirectSubordinateOf(IConcept concept1, IConcept concept2) {
		return (conceptUSL.getEdge(concept1, concept2) != null);
	}
	
	private Map<Set<IConstruct>, Set<IContextObject>> buildIntentToExtentRel() {
		Map<Set<IConstruct>, Set<IContextObject>> intentsToExtents = new HashMap<>();
		Set<Set<IContextObject>> objectsPowerSet = buildObjectsPowerSet();
		for (Set<IContextObject> subset : objectsPowerSet) {
			Set<IConstruct> intent;
			if (subset.size() > 1)
				intent = IntentBldr.getIntent(subset);
			else if (subset.size() == 1)
				intent = new HashSet<IConstruct>(subset.iterator().next().getConstructs());
			else {
				intent = new HashSet<IConstruct>();
				for (IContextObject obj : objects)
					intent.addAll(obj.getConstructs());
			}
			if (intentsToExtents.containsKey(intent))
				intentsToExtents.get(intent).addAll(subset);
			else intentsToExtents.put(intent, subset);
		}
		intentsToExtents = singularizeConstructs(intentsToExtents);
		return intentsToExtents;
	}	
	
	private void buildLattice() {
		Map<Set<IConstruct>, Set<IContextObject>> intentsToExtents = buildIntentToExtentRel();
		for (Entry<Set<IConstruct>, Set<IContextObject>> entry : intentsToExtents.entrySet()) {
			IConcept concept = new Concept(entry.getKey(), entry.getValue());
			if (!concept.getExtent().isEmpty()) {
				if (concept.getExtent().size() == 1)
					concept.setType(IConcept.SINGLETON);
				else if (concept.getExtent().size() == objects.size()) {
					concept.setType(IConcept.TRUISM);
				}
				else {
					concept.setType(IConcept.SUBSET_CONCEPT);
				}
			}
			else {
				concept.setType(IConcept.ABSURDITY);
			}
			lattice.addVertex(concept);
		}
		List<IConcept> conceptList = new ArrayList<>(lattice.vertexSet());
		for (int i = 0 ; i < conceptList.size() - 1 ; i++) {
			IConcept iconcept = conceptList.get(i);
			for (int j = i+1 ; j < conceptList.size() ; j++) {
				IConcept jconcept = conceptList.get(j);
				if (iconcept.getExtent().containsAll(jconcept.getExtent()))
					lattice.addEdge(jconcept, iconcept);
				else if (jconcept.getExtent().containsAll(iconcept.getExtent()))
					lattice.addEdge(iconcept, jconcept);
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
		List<ISymbol> acceptProg = new ArrayList<ISymbol>();
		acceptProg.add(variable);
		IConstruct acceptConstruct = new Construct(acceptProg);
		Set<IConstruct> acceptIntent =  new HashSet<IConstruct>();
		acceptIntent.add(acceptConstruct);
		ontologicalCommitment = new Concept(acceptIntent, new HashSet<IContextObject>(objects));
		ontologicalCommitment.setType(IConcept.ONTOLOGICAL_COMMITMENT);
		return ontologicalCommitment;
	}

	private List<IConcept> removeSubCategories(Set<IConcept> concepts) {
		List<IConcept> conceptList = new ArrayList<>(concepts);
		for (int i = 0 ; i < conceptList.size() - 1 ; i++) {
			IConcept conceptI = conceptList.get(i);
			for (int j = i+1 ; j < conceptList.size() ; j++) {
				IConcept conceptJ = conceptList.get(j);
				if (isA(conceptI, conceptJ))
					concepts.remove(conceptI);
				else if (isA(conceptJ, conceptI))
					concepts.remove(conceptJ);
			}
		}
		return new ArrayList<>(concepts);
	}

	private Map<Set<IConstruct>, Set<IContextObject>> singularizeConstructs(
			Map<Set<IConstruct>, Set<IContextObject>> intentsToExtents) {
		AVariable.resetVarNaming();
		Map<Set<IConstruct>, Set<IContextObject>> mapWithSingularizedIntents 
			= new HashMap<Set<IConstruct>, Set<IContextObject>>();
		/*
		 * must use transitory collections not based on hash tables, since variable naming
		 * will modify hashcodes  
		 */
		List<Set<IConstruct>> listOfIntents = new ArrayList<Set<IConstruct>>();
		List<Set<IConstruct>> listOfSingularizedIntents = new ArrayList<Set<IConstruct>>();
		List<Set<IContextObject>> listOfExtents = new ArrayList<Set<IContextObject>>();
		for (Entry<Set<IConstruct>, Set<IContextObject>> entry : intentsToExtents.entrySet()) {
			listOfIntents.add(entry.getKey());
			listOfExtents.add(entry.getValue());
		}
		for (Set<IConstruct> intent : listOfIntents) {
			Set<IConstruct> singularizedIntent = new HashSet<IConstruct>();
			for (IConstruct construct : intent) {
				construct.nameVariables();
				singularizedIntent.add(construct);
			}
			listOfSingularizedIntents.add(singularizedIntent);
		}
		for (int i = 0 ; i < listOfSingularizedIntents.size() ; i++) {
			mapWithSingularizedIntents.put(listOfSingularizedIntents.get(i), listOfExtents.get(i));
		}
		return mapWithSingularizedIntents;
	}

	private void updateCategoryRank(IConcept concept, int rank) {
		if (concept.rank() < rank || concept.type() == IConcept.ABSURDITY) {
			concept.setRank(rank);
			for (IConcept successor : Graphs.successorListOf(conceptUSL, concept)) {
				updateCategoryRank(successor, rank + 1);
			}
		}
	}

}
