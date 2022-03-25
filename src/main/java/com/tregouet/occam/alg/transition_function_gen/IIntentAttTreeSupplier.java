package com.tregouet.occam.alg.transition_function_gen;

import java.util.Iterator;

import com.tregouet.occam.data.languages.alphabets.domain_specific.impl.ContextualizedProd;
import com.tregouet.occam.data.representations.concepts.IDenotation;
import com.tregouet.tree_finder.data.Tree;

public interface IIntentAttTreeSupplier extends Iterator<Tree<IDenotation, ContextualizedProd>> {

}
