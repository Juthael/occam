package com.tregouet.occam.transition_function;

import java.util.Iterator;

import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.tree_finder.data.ClassificationTree;

public interface IIntentAttTreeSupplier extends Iterator<ClassificationTree<IIntentAttribute, IProduction>> {

}
