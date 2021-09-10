package com.tregouet.occam.transition_function.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.exceptions.PropertyTargetingException;
import com.tregouet.occam.transition_function.IInfoMeter;
import com.tregouet.tree_finder.data.InTree;

public class InfoMeter implements IInfoMeter {

	List<IContextObject> objects;
	InTree<ICategory, DefaultEdge> categories;
	List<IOperator> properties;
	double[] informativity;
	
	public InfoMeter(List<IContextObject> objects, InTree<ICategory, DefaultEdge> categories, 
			List<IOperator> properties) {
		this.objects = objects;
		this.categories = categories;
		this.properties = properties;
		informativity = new double[properties.size()];
		int propertyIdx = 0;
		for (IOperator property : properties) {
			informativity[propertyIdx] = calculatePropertyInformativity(property);
			propertyIdx++;
		}
	}

	@Override
	public double getInformativity(IOperator property) {
		return informativity[properties.indexOf(property)];
	}

	@Override
	public double getInformativity(int objectIndex, List<String> propertySpecification) throws PropertyTargetingException {
		//search for the categories that describe the specified object
		ICategory objCat;
		try{
			objCat = getObjectCategory(objects.get(objectIndex));
		}
		catch (Exception e) {
			throw new PropertyTargetingException("No object can be found at the specified index.");
		}
		Set<ICategory> objSuperCats = categories.getDescendants(objCat);
		//among these, search for the ones that meet specifications
		List<ICategory> objSuperCatsMeetingSpecification = objSuperCats
				.stream()
				.filter(c -> c.meets(propertySpecification))
				.collect(Collectors.toList());
		if (objSuperCatsMeetingSpecification.isEmpty())
			throw new PropertyTargetingException("The target object doesn't meet the property specification.");
		//among these, search for the most general category
		Iterator<ICategory> catMeetingSpecIte = objSuperCatsMeetingSpecification.iterator();
		ICategory greatestCatMeetingSpecification = null;
		while (greatestCatMeetingSpecification == null && catMeetingSpecIte.hasNext()) {
			ICategory nextCat = catMeetingSpecIte.next();
			if (!categories.getDescendants(nextCat).removeAll(objSuperCatsMeetingSpecification)) {
				greatestCatMeetingSpecification = nextCat;
			}
		}
		//in this category's intent, find the attribute targeted by the specification
		IIntentAttribute targetAttribute = greatestCatMeetingSpecification.getMatchingAttribute(propertySpecification);
		//the target property is the one that operates on the target attribute
		IOperator targetProperty = null;
		Iterator<IOperator> propIte = properties.iterator();
		while (targetProperty == null && propIte.hasNext()) {
			IOperator nextProp = propIte.next();
			if (nextProp.operateOn(targetAttribute) != null)
				targetProperty = nextProp;
		}
		return targetProperty.getInformativity();
	}
	
	private double calculatePropertyInformativity(IOperator property) {
		return amountOfSurprise(property) * diagnosticity(property);
	}
	
	private double amountOfSurprise(IOperator property) {
		return -binaryLogarithm(property.getOperatingState().getExtentSize() / property.getNextState().getExtentSize());
	}
	
	private double diagnosticity(IOperator property) {
		return property.getOperatingState().getExtentSize() / objects.size();
	}
	
	private static double binaryLogarithm(double arg) {
		return Math.log10(arg)/Math.log10(2);
	}
	
	private ICategory getObjectCategory(IContextObject object) {
		ICategory objectCategory = null;
		Set<IConstruct> objConstructs = new HashSet<>(object.getConstructs());
		int objCatIdx = 0;
		List<ICategory> objectCategories = categories.vertexSet()
				.stream()
				.filter(c -> c.type() == ICategory.OBJECT)
				.collect(Collectors.toList());
		while (objectCategory == null && objCatIdx < objects.size()) {
			ICategory currentObjCat = objectCategories.get(objCatIdx);
			Set<IConstruct> currObjCatIntent = new HashSet<>(currentObjCat.getIntent());
			if (objConstructs.equals(currObjCatIntent)) {
				objectCategory = currentObjCat;
			}
			objCatIdx++;
		}
		return objectCategory;
	}

}