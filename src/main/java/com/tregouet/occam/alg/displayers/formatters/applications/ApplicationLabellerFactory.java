package com.tregouet.occam.alg.displayers.formatters.applications;

import com.tregouet.occam.alg.displayers.formatters.applications.impl.AngleBrackets;

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
