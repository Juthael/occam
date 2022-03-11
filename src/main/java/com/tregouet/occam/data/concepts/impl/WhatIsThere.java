package com.tregouet.occam.data.concepts.impl;

import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.preconcepts.impl.NothingPreconcept;

public class WhatIsThere extends Concept implements IState {

	public static final WhatIsThere INSTANCE = new WhatIsThere();
	
	private WhatIsThere() {
		super(NothingPreconcept.INSTANCE);
	}

}
