package com.tregouet.occam.transition_function;

import java.util.Iterator;

import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.operators.impl.Production;
import com.tregouet.tree_finder.data.Tree;

public interface IIntentAttTreeSupplier extends Iterator<Tree<IIntentAttribute, Production>> {

}
