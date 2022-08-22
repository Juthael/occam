package com.tregouet.occam.data.modules;

import java.util.List;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.data.representations.classifications.concepts.IIsA;

public interface IModule {
	
	List<IContextObject> getContext();
	
	DirectedAcyclicGraph<IConcept, IIsA> getLatticeOfConcepts();

}
