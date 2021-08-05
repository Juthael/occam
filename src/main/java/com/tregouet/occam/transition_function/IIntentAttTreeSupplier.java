package com.tregouet.occam.transition_function;

import java.util.Iterator;

import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.tree_finder.data.InTree;

public interface IIntentAttTreeSupplier extends Iterator<InTree<IIntentAttribute, IProduction>> {

}
