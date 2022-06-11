package com.tregouet.occam.alg.displayers.formatters.differentiae.properties.applications;

import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.applications.impl.AngleBrackets;

public class ApplicationLabellerFactory {

	public static final ApplicationLabellerFactory INSTANCE = new ApplicationLabellerFactory();

	private ApplicationLabellerFactory() {
	}

	public ApplicationLabeller apply(ApplicationLabellerStrategy strategy) {
		switch (strategy) {
		case ANGLE_BRACKETS :
			return AngleBrackets.INSTANCE;
		default :
			return null;
		}
	}

}
