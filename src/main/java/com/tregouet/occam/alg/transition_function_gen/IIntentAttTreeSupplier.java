package com.tregouet.occam.alg.transition_function_gen;

import java.util.Iterator;

import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedProd;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.tree_finder.data.Tree;

public interface IIntentAttTreeSupplier extends Iterator<Tree<IDenotation, ContextualizedProd>> {

}
