package com.tregouet.occam.data.categories.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.data.categories.IClassificationTreeSupplier;
import com.tregouet.occam.data.categories.ICatTreeWithConstrainedExtentStructureSupplier;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IExtentStructureConstraint;
import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.constructs.ISymbol;
import com.tregouet.occam.data.constructs.impl.Construct;
import com.tregouet.occam.data.constructs.impl.Variable;

public class Categories implements ICategories {
	
	private final List<IContextObject> objects;
	private final DirectedAcyclicGraph<ICategory, DefaultEdge> lattice;
	private final DirectedAcyclicGraph<ICategory, DefaultEdge> transitiveReduction;
	private final List<ICategory> topologicalOrder = new ArrayList<>();
	private final ICategory ontologicalCommitment;
	private final ICategory truism;
	private final List<ICategory> objCategories = new ArrayList<>();
	private final ICategory absurdity;
	
	@SuppressWarnings("unchecked")
	public Categories(List<IContextObject> objects) {
		this.objects = objects;
		lattice = new DirectedAcyclicGraph<>(null, DefaultEdge::new, false);
		buildLattice();
		ICategory truism = null;
		ICategory absurdity = null;
		for (ICategory category : lattice.vertexSet()) {
			switch(category.type()) {
				case ICategory.TRUISM :
					truism = category;
					break;
				case ICategory.ABSURDITY :
					absurdity = category;
					break;
				case ICategory.OBJECT :
					objCategories.add(category);
					break;
			}
		}
		this.truism = truism;
		this.absurdity = absurdity;
		ontologicalCommitment = addOntologicalCommitmentToLattice();
		updateCategoryRank(absurdity, 0);
		transitiveReduction = (DirectedAcyclicGraph<ICategory, DefaultEdge>) lattice.clone();
		TransitiveReduction.INSTANCE.reduce(transitiveReduction);
		TopologicalOrderIterator<ICategory, DefaultEdge> sorter = new TopologicalOrderIterator<>(transitiveReduction);
		sorter.forEachRemaining(d -> topologicalOrder.add(d));
	}

	@Override
	public boolean areA(List<ICategory> cats, ICategory cat) {
		boolean areA = true;
		int catsIndex = 0;
		while (areA && catsIndex < cats.size()) {
			areA = isA(cats.get(catsIndex), cat);
			catsIndex++;
		}
		return areA;
	}

	@Override
	public ICategory getAbsurdity() {
		return absurdity;
	}

	@Override
	public IClassificationTreeSupplier getCatTreeSupplier() {
		return new ClassificationTreeSupplier(lattice);
	}

	@Override
	public ICatTreeWithConstrainedExtentStructureSupplier getCatTreeSupplier(IExtentStructureConstraint constraint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICategory getCatWithExtent(Set<IContextObject> extent) {
		if (extent.containsAll(objects))
			return truism;
		for (ICategory cat : topologicalOrder) {
			if (cat.getExtent().equals(extent))
				return cat;
		}
		return null;
	}

	@Override
	public List<IContextObject> getContextObjects() {
		return objects;
	}

	@Override
	public DirectedAcyclicGraph<ICategory, DefaultEdge> getTransitiveReduction() {
		return transitiveReduction;
	}

	@Override
	public ICategory getLeastCommonSuperordinate(Set<ICategory> categories) {
		if (categories.isEmpty())
			return null;
		List<ICategory> catList = removeSubCategories(categories);
		if (catList.size() == 1)
			return catList.get(0);
		ICategory leastCommonSuperordinate = null;
		ListIterator<ICategory> catIterator = topologicalOrder.listIterator(topologicalOrder.size());
		boolean abortSearch = false;
		while (catIterator.hasPrevious() && !abortSearch) {
			ICategory current = catIterator.previous();
			if (areA(catList, current))
				leastCommonSuperordinate = current;
			else if (categories.contains(current))
				abortSearch = true;
		}
		return leastCommonSuperordinate;		
	}

	@Override
	public List<ICategory> getObjectCategories() {
		return objCategories;
	}
	
	@Override
	public ICategory getOntologicalCommitment() {
		return ontologicalCommitment;
	}
	
	@Override
	public List<ICategory> getTopologicalSorting() {
		return topologicalOrder;
	}
	
	@Override
	public ICategory getTruism() {
		return truism;
	}	
	
	@Override
	public boolean isA(ICategory cat1, ICategory cat2) {
		boolean isA = false;
		if (topologicalOrder.indexOf(cat1) < topologicalOrder.indexOf(cat2)) {
			BreadthFirstIterator<ICategory, DefaultEdge> iterator = 
					new BreadthFirstIterator<>(transitiveReduction, cat1);
			iterator.next();
			while (!isA && iterator.hasNext())
				isA = cat2.equals(iterator.next());
		}
		return isA;		
	}
	
	@Override
	public boolean isADirectSubordinateOf(ICategory cat1, ICategory cat2) {
		return (transitiveReduction.getEdge(cat1, cat2) != null);
	}
	
	private void buildLattice() {
		Map<Set<IConstruct>, Set<IContextObject>> intentsToExtents = buildIntentToExtentRel();
		for (Entry<Set<IConstruct>, Set<IContextObject>> entry : intentsToExtents.entrySet()) {
			ICategory category = new Category(entry.getKey(), entry.getValue());
			if (!category.getExtent().isEmpty()) {
				if (category.getExtent().size() == 1)
					category.setType(ICategory.OBJECT);
				else if (category.getExtent().size() == objects.size()) {
					category.setType(ICategory.TRUISM);
				}
				else {
					category.setType(ICategory.SUBSET_CAT);
				}
			}
			else {
				category.setType(ICategory.ABSURDITY);
			}
			lattice.addVertex(category);
		}
		List<ICategory> catList = new ArrayList<>(lattice.vertexSet());
		for (int i = 0 ; i < catList.size() - 1 ; i++) {
			ICategory iCat = catList.get(i);
			for (int j = i+1 ; j < catList.size() ; j++) {
				ICategory jCat = catList.get(j);
				if (iCat.getExtent().containsAll(jCat.getExtent()))
					lattice.addEdge(jCat, iCat);
				else if (jCat.getExtent().containsAll(iCat.getExtent()))
					lattice.addEdge(iCat, jCat);
			}
		}
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
	
	private ICategory addOntologicalCommitmentToLattice() {
		ICategory ontologicalCommitment;
		ISymbol variable = new Variable(!AVariable.DEFERRED_NAMING);
		List<ISymbol> ocProg = new ArrayList<ISymbol>();
		ocProg.add(variable);
		IConstruct ocConstruct = new Construct(ocProg);
		Set<IConstruct> ocIntent =  new HashSet<IConstruct>();
		ocIntent.add(ocConstruct);
		ontologicalCommitment = new Category(ocIntent, new HashSet<IContextObject>(objects));
		ontologicalCommitment.setType(ICategory.ONTOLOGICAL_COMMITMENT);
		Set<ICategory> latticeCategories = lattice.vertexSet();
		lattice.addVertex(ontologicalCommitment);
		for (ICategory lattCategory : latticeCategories)
			lattice.addEdge(lattCategory, ontologicalCommitment);
		return ontologicalCommitment;
	}
	
	private List<ICategory> removeSubCategories(Set<ICategory> categories) {
		List<ICategory> catList = new ArrayList<>(categories);
		for (int i = 0 ; i < catList.size() - 1 ; i++) {
			ICategory catI = catList.get(i);
			for (int j = i+1 ; j < catList.size() ; j++) {
				ICategory catJ = catList.get(j);
				if (isA(catI, catJ))
					categories.remove(catI);
				else if (isA(catJ, catI))
					categories.remove(catJ);
			}
		}
		return new ArrayList<>(categories);
	}

	private Map<Set<IConstruct>, Set<IContextObject>> singularizeConstructs(
			Map<Set<IConstruct>, Set<IContextObject>> intentsToExtents) {
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

	private void updateCategoryRank(ICategory category, int rank) {
		if (category.rank() < rank || category.type() == ICategory.ABSURDITY) {
			category.setRank(rank);
			for (ICategory successor : Graphs.successorListOf(lattice, category)) {
				updateCategoryRank(successor, rank + 1);
			}
		}
	}

	@Override
	public DirectedAcyclicGraph<ICategory, DefaultEdge> getCategoryLattice() {
		return lattice;
	}

}
