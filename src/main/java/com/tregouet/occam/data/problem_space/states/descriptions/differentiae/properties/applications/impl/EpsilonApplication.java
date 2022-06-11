package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.impl;

import java.util.Arrays;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;
import com.tregouet.occam.data.problem_space.states.productions.impl.EpsilonProd;

public class EpsilonApplication extends Application implements IApplication {

	public static final EpsilonApplication INSTANCE = new EpsilonApplication();

	private EpsilonApplication() {
		super(null, null, Arrays.asList(new IProduction[] {EpsilonProd.INSTANCE}), null);
	}

	@Override
	public boolean isEpsilon() {
		return true;
	}

}
