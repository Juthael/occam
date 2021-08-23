package com.tregouet.occam.transition_function;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.tree_finder.data.InTree;

public interface ICatStructureAwareTFSupplier extends ITransitionFunctionSupplier, Iterator<IRepresentedCatTree> {
	
	InTree<ICategory, DefaultEdge> getOptimalCategoryStructure();
	
	String getDefinitionOfObjects();
	
	public static String getDefinitionOfObjects(Map<ICategory, String> objectCategoryToName) {
		StringBuilder sB = new StringBuilder();
		String newLine = System.lineSeparator();
		sB.append("*** DEFINITION OF OBJECTS ***" + newLine + newLine);
		List<ICategory> objectCategories = new ArrayList<>(objectCategoryToName.keySet());
		for (int i = 0 ; i < objectCategories.size() ; i++) {
			ICategory objCat = objectCategories.get(i);
			sB.append("**Object " + objectCategoryToName.get(objCat) + " :" + newLine);
			sB.append(objCat.toString());
			if (i < objectCategories.size() - 1)
				sB.append(newLine + newLine);
		}
		return sB.toString();
	}

}
