package com.tregouet.occam.data.representations.transitions;

import com.tregouet.occam.data.representations.tapes.IRepresentationTapeSet;

public interface IApplication extends IConceptTransition {

	boolean isRedundant();
	
	IRepresentationTapeSet output(IRepresentationTapeSet input);

}
