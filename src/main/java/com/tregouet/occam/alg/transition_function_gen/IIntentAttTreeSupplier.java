package com.tregouet.occam.alg.transition_function_gen;

import java.util.Iterator;

import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.languages.specific.impl.ContextualizedProduction;
import com.tregouet.tree_finder.data.Tree;

public interface IIntentAttTreeSupplier extends Iterator<Tree<IDenotation, ContextualizedProduction>> {

}
