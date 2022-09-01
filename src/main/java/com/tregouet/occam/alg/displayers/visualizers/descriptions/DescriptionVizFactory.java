package com.tregouet.occam.alg.displayers.visualizers.descriptions;

import com.tregouet.occam.alg.displayers.visualizers.descriptions.impl.DelegateFormatManaging;

public class DescriptionVizFactory {

	public static final DescriptionVizFactory INSTANCE = new DescriptionVizFactory();

	private DescriptionVizFactory() {
	}

	public DescriptionViz apply(DescriptionVizStrategy strategy) {
		switch(strategy) {
		case DELEGATE_FORMAT_MANAGING :
			return new DelegateFormatManaging();
		default :
			return null;
		}
	}

}
