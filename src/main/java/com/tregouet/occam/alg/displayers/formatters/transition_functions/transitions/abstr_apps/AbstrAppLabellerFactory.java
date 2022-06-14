package com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions.abstr_apps;

import com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions.abstr_apps.impl.Conjunction;

public class AbstrAppLabellerFactory {

	public static final AbstrAppLabellerFactory INSTANCE = new AbstrAppLabellerFactory();

	private AbstrAppLabellerFactory() {
	}

	public AbstrAppLabeller apply(AbstrAppLabellerStrategy strategy) {
		switch(strategy) {
		case CONJUNCTION :
			return Conjunction.INSTANCE;
		default :
			return null;
		}
	}

}
