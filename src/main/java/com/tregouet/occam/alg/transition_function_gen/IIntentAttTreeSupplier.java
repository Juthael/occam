package com.tregouet.occam.alg.transition_function_gen;

import java.util.Iterator;

import com.tregouet.occam.data.abstract_machines.transitions.impl.Production;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.tree_finder.data.Tree;

public interface IIntentAttTreeSupplier extends Iterator<Tree<IIntentAttribute, Production>> {

}
