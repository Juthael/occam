package com.tregouet.occam.data.categories.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.Graphs;
import org.jgrapht.alg.TransitiveClosure;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.data.categories.ICatTreeSupplier;
import com.tregouet.occam.data.categories.ICatTreeWithConstrainedExtentStructureSupplier;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IExtentStructureConstraint;
import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.constructs.ISymbol;
import com.tregouet.occam.data.constructs.impl.Construct;
import com.tregouet.occam.data.constructs.impl.Terminal;
import com.tregouet.occam.data.constructs.impl.Variable;
import com.tregouet.subseq_finder.ISubseqFinder;
import com.tregouet.subseq_finder.ISymbolSeq;
import com.tregouet.subseq_finder.impl.SubseqFinder;
import com.tregouet.subseq_finder.impl.SymbolSeq;

public class Categories implements ICategories {
	
	List<IContextObject> objects;
	DirectedAcyclicGraph<ICategory, DefaultEdge> hasseDiagram;
	List<ICategory> topologicalOrder = new ArrayList<>();
	ICategory ontologicalCommitment;
	ICategory truismAboutTruism;
	ICategory truism;
	ICategory absurdity;
	ConnectivityInspector<ICategory, DefaultEdge> inspector;
	
	public Categories(List<IContextObject> objects) {
		this.objects = objects;
		hasseDiagram = new DirectedAcyclicGraph<>(null, DefaultEdge::new, false);
		AVariable.initializeNameProvider();
		buildCatLatticeRelationGraph();
		instantiateOntologicalCommitment();
		instantiateTruismAboutTruism();
		addTrAbTrAndOntologicalCommitmentToRelation();
		TransitiveClosure.INSTANCE.closeDirectedAcyclicGraph(hasseDiagram);
		updateCategoryRank(absurdity, 0);
		TopologicalOrderIterator<ICategory, DefaultEdge> sorter = new TopologicalOrderIterator<>(hasseDiagram);
		sorter.forEachRemaining(d -> topologicalOrder.add(d));
		inspector = new ConnectivityInspector<>(hasseDiagram);
	}

	@Override
	public List<ICategory> getObjects() {
		return hasseDiagram.vertexSet()
				.stream()
				.filter(d -> (d.type() == ICategory.LATT_OBJ))
				.collect(Collectors.toList());
	}

	@Override
	public ICategory getTruism() {
		return truism;
	}

	@Override
	public ICategory getTruismAboutTruism() {
		return truismAboutTruism;
	}

	@Override
	public ICategory getOntologicalCommitment() {
		return ontologicalCommitment;
	}

	@Override
	public List<ICategory> getTopologicallySortedCategories() {
		return topologicalOrder;
	}

	@Override
	public ICatTreeSupplier getCatTreeSupplier() {
		return new CatTreeSupplier(hasseDiagram);
	}

	@Override
	public ICatTreeWithConstrainedExtentStructureSupplier getCatTreeSupplier(IExtentStructureConstraint constraint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isA(ICategory cat1, ICategory cat2) {
		return inspector.pathExists(cat1, cat2);
	}

	@Override
	public boolean isADirectSubCategoryOf(ICategory cat1, ICategory cat2) {
		return Graphs.successorListOf(hasseDiagram, cat1).contains(cat2);
	}
	
	private void buildCatLatticeRelationGraph() {
		Map<Set<IConstruct>, Set<IContextObject>> intentsToExtents = buildIntentToExtentRel();
		for (Entry<Set<IConstruct>, Set<IContextObject>> entry : intentsToExtents.entrySet()) {
			ICategory category = new Category(entry.getKey(), entry.getValue());
			if (!category.getExtent().isEmpty()) {
				if (category.getExtent().size() == 1)
					category.setType(Category.LATT_OBJ);
				else if (category.getExtent().size() == objects.size()) {
					category.setType(Category.LATT_MAX);
					truism = category;
				}
				else {
					category.setType(Category.LATT_CAT);
				}
			}
			else {
				category.setType(Category.LATT_MIN);
				absurdity = category;
			}
			hasseDiagram.addVertex(category);
		}
		List<ICategory> catList = new ArrayList<>(hasseDiagram.vertexSet());
		for (int i = 0 ; i < catList.size() - 1 ; i++) {
			for (int j = i+1 ; j < catList.size() ; j++) {
				ICategory iCat = catList.get(i);
				ICategory jCat = catList.get(j);
				if (iCat.getExtent().containsAll(jCat.getExtent()))
					hasseDiagram.addEdge(jCat, iCat);
				else if (jCat.getExtent().containsAll(iCat.getExtent()))
					hasseDiagram.addEdge(iCat, jCat);
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
	
	private void instantiateOntologicalCommitment() {
		ISymbol variable = new Variable(!AVariable.DEFERRED_NAMING);
		List<ISymbol> acceptProg = new ArrayList<ISymbol>();
		acceptProg.add(variable);
		IConstruct acceptConstruct = new Construct(acceptProg);
		Set<IConstruct> acceptIntent =  new HashSet<IConstruct>();
		acceptIntent.add(acceptConstruct);
		ontologicalCommitment = new Category(acceptIntent, new HashSet<IContextObject>(objects));
		ontologicalCommitment.setType(ICategory.ACCEPT);
	}
	
	private void instantiateTruismAboutTruism() {
		Set<IConstruct> preAccIntent = new HashSet<IConstruct>();
		Set<IConstruct> lattMaxIntent = new HashSet<>(truism.getIntent());
		List<ISymbolSeq> lattMaxSymbolSeq = new ArrayList<ISymbolSeq>();
		for (IConstruct construct : lattMaxIntent) {
			lattMaxSymbolSeq.add(new SymbolSeq(construct.toListOfStringsWithPlaceholders()));
		}
		ISubseqFinder subseqFinder = new SubseqFinder(lattMaxSymbolSeq);
		Set<ISymbolSeq> maxCommonSubsqs = subseqFinder.getMaxCommonSubseqs();
		for (ISymbolSeq subsq : maxCommonSubsqs) {
			List<ISymbol> preAccSymList = new ArrayList<ISymbol>();
			boolean lastSymStringWasPlaceholder = false;
			for (String symString : subsq.getStringSequence()) {
				if (symString.equals(ISymbolSeq.PLACEHOLDER)) {
					if (lastSymStringWasPlaceholder) {
						//do nothing. No use in consecutive placeholders.
					}
					else {
						preAccSymList.add(new Variable(AVariable.DEFERRED_NAMING));
						lastSymStringWasPlaceholder = true;
					}
				}
				else {
					preAccSymList.add(new Terminal(symString));
					lastSymStringWasPlaceholder = false;
				}
			}
			preAccIntent.add(new Construct(preAccSymList));
		}
		for (IConstruct construct : preAccIntent)
			construct.nameVariables();
		truismAboutTruism = new Category(preAccIntent, new HashSet<IContextObject>(objects));
		truismAboutTruism.setType(ICategory.PREACCEPT);
	}
	
	private void addTrAbTrAndOntologicalCommitmentToRelation() {
		Set<ICategory> categories = hasseDiagram.vertexSet();
		hasseDiagram.addVertex(truismAboutTruism);
		for (ICategory category : categories)
			hasseDiagram.addEdge(category, truismAboutTruism);
		categories.add(truismAboutTruism);
		hasseDiagram.addVertex(ontologicalCommitment);
		for (ICategory category : categories)
			hasseDiagram.addEdge(category, ontologicalCommitment);
	}
	
	private void updateCategoryRank(ICategory category, int rank) {
		if (category.rank() < rank || category.type() == ICategory.LATT_MIN) {
			category.setRank(rank);
			for (ICategory successor : Graphs.successorListOf(hasseDiagram, category)) {
				updateCategoryRank(successor, rank + 1);
			}
		}
	}

}
