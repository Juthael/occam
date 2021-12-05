package com.tregouet.occam.transition_function;

import com.tregouet.occam.data.constructs.IConstruct;

public interface IFrame extends IConstruct {
	
	void restrictFrameWith(IFrame other);

}
