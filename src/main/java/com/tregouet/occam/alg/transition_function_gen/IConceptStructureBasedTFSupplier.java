package com.tregouet.occam.alg.transition_function_gen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.data.abstract_machines.functions.IStructurallyEquivalentTransFunctions;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.data.Tree;

public interface IConceptStructureBasedTFSupplier extends ITransitionFunctionSupplier, Iterator<IStructurallyEquivalentTransFunctions> {
	
	public static String getDefinitionOfObjects(Map<IConcept, String> objectCategoryToName) {
		StringBuilder sB = new StringBuilder();
		String newLine = System.lineSeparator();
		sB.append("*** DEFINITION OF OBJECTS ***" + newLine + newLine);
		List<IConcept> objectCategories = new ArrayList<>(objectCategoryToName.keySet());
		for (int i = 0 ; i < objectCategories.size() ; i++) {
			IConcept objCat = objectCategories.get(i);
			sB.append("**Object " + objectCategoryToName.get(objCat) + " :" + newLine);
			sB.append(objCat.toString());
			if (i < objectCategories.size() - 1)
				sB.append(newLine + newLine);
		}
		return sB.toString();
	}
	
	String getDefinitionOfObjects();
	
	Tree<IConcept, IsA> getOptimalCategoryStructure();

}
