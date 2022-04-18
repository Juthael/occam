package com.tregouet.occam.alg.displayers.graph_visualizers.transition_functions;

import com.tregouet.occam.alg.displayers.graph_visualizers.transition_functions.impl.BasicTransitionFunctionViz;

public class TransitionFunctionVizFactory {

	public static final TransitionFunctionVizFactory INSTANCE = new TransitionFunctionVizFactory();

	private TransitionFunctionVizFactory() {
	}

	public TransitionFunctionViz apply(TransitionFunctionVizStrategy strategy) {
		switch (strategy) {
		case BASIC:
			return BasicTransitionFunctionViz.INSTANCE;
		default:
			return null;
		}
	}

}
