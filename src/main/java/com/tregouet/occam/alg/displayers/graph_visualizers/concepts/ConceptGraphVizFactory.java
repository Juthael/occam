package com.tregouet.occam.alg.displayers.graph_visualizers.concepts;

import com.tregouet.occam.alg.displayers.graph_visualizers.concepts.impl.BasicConceptGraphViz;

public class ConceptGraphVizFactory {
	
	public static final ConceptGraphVizFactory INSTANCE = new ConceptGraphVizFactory();
	
	private ConceptGraphVizFactory() {
	}
	
	public ConceptGraphViz apply(ConceptGraphVizStrategy strategy) {
		switch (strategy) {
			case BASIC : 
				return BasicConceptGraphViz.INSTANCE;
			default : 
				return null;
		}
	}

}
