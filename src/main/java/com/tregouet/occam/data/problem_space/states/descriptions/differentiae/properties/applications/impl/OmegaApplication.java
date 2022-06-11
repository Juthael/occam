package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.impl;

import java.util.ArrayList;
import java.util.Arrays;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;
import com.tregouet.occam.data.problem_space.states.productions.impl.OmegaProd;

public class OmegaApplication extends Application implements IApplication {
	
	public static final OmegaApplication INSTANCE = new OmegaApplication();

	private OmegaApplication() {
		super(null, null, new ArrayList<>(Arrays.asList(new IProduction[] {OmegaProd.INSTANCE})), null);
	}

}
