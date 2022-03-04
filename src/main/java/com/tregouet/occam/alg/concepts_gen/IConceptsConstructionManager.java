package com.tregouet.occam.alg.concepts_gen;

import java.util.Collection;
import java.util.List;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.concepts_gen.impl.ConceptsConstructionManager;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IContextObject;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.tree_finder.data.UpperSemilattice;

public interface IConceptsConstructionManager {
	
	IConceptsConstructionManager input(Collection<IContextObject> objects);
	
	List<IContextObject> getObjects();
	
	DirectedAcyclicGraph<IConcept, IIsA> getLattice();
	
	UpperSemilattice<IConcept, IIsA> getUpperSemilattice();
	
	IConcept getOntologicalCommitment();
	
	List<IConcept> getTopologicalOrder();
	
	IConcept getTruism();
	
	List<IConcept> getObjectConcepts();
	
	IConcept getAbsurdity();
	
	public static IConceptsConstructionManager getInstance() {
		return new ConceptsConstructionManager(); 
	}

}
