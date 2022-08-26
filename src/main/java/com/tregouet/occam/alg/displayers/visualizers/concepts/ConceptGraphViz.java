package com.tregouet.occam.alg.displayers.visualizers.concepts;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IIsA;

public interface ConceptGraphViz {
	
	String apply(DirectedAcyclicGraph<IConcept, IIsA> graph, String fileName, boolean printRedundantDenot);

}
