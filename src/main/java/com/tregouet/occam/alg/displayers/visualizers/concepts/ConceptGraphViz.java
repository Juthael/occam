package com.tregouet.occam.alg.displayers.visualizers.concepts;

import java.util.function.BiFunction;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;

public interface ConceptGraphViz extends BiFunction<DirectedAcyclicGraph<IConcept, IIsA>, String, String> {

}
