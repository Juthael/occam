package com.tregouet.occam.alg.displayers.visualizers.descriptions;

import com.tregouet.occam.alg.displayers.visualizers.descriptions.impl.BasicDescriptionViz;

public class DescriptionVizFactory {

	public static final DescriptionVizFactory INSTANCE = new DescriptionVizFactory();

	private DescriptionVizFactory() {
	}

	public DescriptionViz apply(DescriptionVizStrategy strategy) {
		switch (strategy) {
		case BASIC:
			return BasicDescriptionViz.INSTANCE;
		default:
			return null;
		}
	}

}
