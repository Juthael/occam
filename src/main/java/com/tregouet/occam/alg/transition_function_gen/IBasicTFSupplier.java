package com.tregouet.occam.alg.transition_function_gen;

import java.util.Iterator;

import com.tregouet.occam.data.logical_structures.automata.IAutomaton;

public interface IBasicTFSupplier extends ITransitionFunctionSupplier, Iterator<IAutomaton> {

}
