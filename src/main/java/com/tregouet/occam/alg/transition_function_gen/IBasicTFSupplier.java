package com.tregouet.occam.alg.transition_function_gen;

import java.util.Iterator;

import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;

public interface IBasicTFSupplier extends ITransitionFunctionSupplier, Iterator<IAutomaton> {

}
