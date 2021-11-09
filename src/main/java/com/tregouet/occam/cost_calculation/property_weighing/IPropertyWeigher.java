package com.tregouet.occam.cost_calculation.property_weighing;

import java.util.List;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.exceptions.PropertyTargetingException;
import com.tregouet.tree_finder.data.Tree;

public interface IPropertyWeigher {
	
	double getPropertyWeight(int objectIndex, List<String> propertySpecification) throws PropertyTargetingException;
	
	double getPropertyWeight(IOperator property);
	
	void set(List<IContextObject> objects, Tree<ICategory, DefaultEdge> categories, 
			List<IOperator> properties);

}
