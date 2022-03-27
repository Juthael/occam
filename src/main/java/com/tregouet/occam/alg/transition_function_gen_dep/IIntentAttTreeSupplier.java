package com.tregouet.occam.alg.transition_function_gen_dep;

import java.util.Iterator;

import com.tregouet.occam.data.languages.alphabets.domain_specific.impl.ContextualizedProd;
import com.tregouet.occam.data.representations.concepts.IDenotation;
import com.tregouet.tree_finder.data.InvertedTree;

public interface IIntentAttTreeSupplier extends Iterator<InvertedTree<IDenotation, ContextualizedProd>> {

}
