package com.tregouet.occam.data.operators;

import com.tregouet.occam.transition_function.IState;

public interface IReframer extends ITransition {
	
	String getReframer();
	
	void reframe(IState complementedState);

}
