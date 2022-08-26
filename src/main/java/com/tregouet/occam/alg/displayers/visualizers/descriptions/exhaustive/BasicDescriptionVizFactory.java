package com.tregouet.occam.alg.displayers.visualizers.descriptions.exhaustive;

import com.tregouet.occam.alg.displayers.visualizers.descriptions.exhaustive.impl.UseDifferentiaeLabeller;

public class BasicDescriptionVizFactory {

	public static final BasicDescriptionVizFactory INSTANCE = new BasicDescriptionVizFactory();

	private BasicDescriptionVizFactory() {
	}

	public BasicDescriptionViz apply(BasicDescriptionVizStrategy strategy) {
		switch (strategy) {
		case USE_DIFF_LABELLER:
			return new UseDifferentiaeLabeller();
		default:
			return null;
		}
	}

}
