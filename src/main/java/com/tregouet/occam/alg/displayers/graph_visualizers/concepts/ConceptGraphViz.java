package com.tregouet.occam.alg.displayers.graph_visualizers.concepts;

import java.util.function.BiFunction;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;

public interface ConceptGraphViz extends BiFunction<DirectedAcyclicGraph<IConcept, IIsA>, String, String> {

}
