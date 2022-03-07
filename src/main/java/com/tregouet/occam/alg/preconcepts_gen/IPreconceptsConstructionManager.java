package com.tregouet.occam.alg.preconcepts_gen;

import java.util.Collection;
import java.util.List;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.preconcepts_gen.impl.PreconceptsConstructionManager;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.tree_finder.data.UpperSemilattice;

public interface IPreconceptsConstructionManager {
	
	IPreconceptsConstructionManager input(Collection<IContextObject> objects);
	
	List<IContextObject> getObjects();
	
	DirectedAcyclicGraph<IPreconcept, IIsA> getLattice();
	
	UpperSemilattice<IPreconcept, IIsA> getUpperSemilattice();
	
	IPreconcept getOntologicalCommitment();
	
	List<IPreconcept> getTopologicalOrder();
	
	IPreconcept getTruism();
	
	List<IPreconcept> getObjectPreconcepts();
	
	IPreconcept getAbsurdity();
	
	public static IPreconceptsConstructionManager getInstance() {
		return new PreconceptsConstructionManager(); 
	}

}
