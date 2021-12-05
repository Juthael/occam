package com.tregouet.occam.cost_calculation.property_weighing.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.tregouet.occam.cost_calculation.property_weighing.IPropertyWeigher;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.constructs.impl.Construct;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.exceptions.PropertyTargetingException;
import com.tregouet.tree_finder.data.Tree;

public class InformativityDiagnosticity implements IPropertyWeigher {

	private List<IContextObject> objects;
	private Tree<IConcept, IsA> concepts;
	private List<IOperator> properties;
	private double[] informativity;
	
	public InformativityDiagnosticity() {
	}
	
	public InformativityDiagnosticity(List<IContextObject> objects, Tree<IConcept, IsA> concepts, 
			List<IOperator> properties) {
		set(objects, concepts, properties);
	}	
	
	private static double binaryLogarithm(double arg) {
		return Math.log10(arg)/Math.log10(2);
	}

	@Override
	public double getPropertyWeight(int objectIndex, List<String> propertySpecification) throws PropertyTargetingException {
		//search for the categories that describe the specified object
		IConcept objCat;
		try{
			objCat = getObjectCategory(objects.get(objectIndex));
		}
		catch (Exception e) {
			throw new PropertyTargetingException("No object can be found at the specified index.");
		}
		Set<IConcept> objSuperCats = concepts.getDescendants(objCat);
		//among these, search for the ones that meet specifications
		List<IConcept> objSuperCatsMeetingSpecification = objSuperCats
				.stream()
				.filter(c -> c.meets(propertySpecification))
				.collect(Collectors.toList());
		if (objSuperCatsMeetingSpecification.isEmpty())
			throw new PropertyTargetingException("The target object doesn't meet the property specification.");
		//among these, search for the most general category
		Iterator<IConcept> catMeetingSpecIte = objSuperCatsMeetingSpecification.iterator();
		IConcept greatestCatMeetingSpecification = null;
		while (greatestCatMeetingSpecification == null && catMeetingSpecIte.hasNext()) {
			IConcept nextCat = catMeetingSpecIte.next();
			if (!concepts.getDescendants(nextCat).removeAll(objSuperCatsMeetingSpecification)) {
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

	@Override
	public double getPropertyWeight(IOperator property) {
		return informativity[properties.indexOf(property)];
	}
	
	@Override
	public void set(List<IContextObject> objects, Tree<IConcept, IsA> concepts, 
			List<IOperator> properties) {
		this.objects = objects;
		this.concepts = concepts;
		this.properties = properties;
		informativity = new double[properties.size()];
		int propertyIdx = 0;
		for (IOperator property : properties) {
			informativity[propertyIdx] = calculatePropertyWeight(property);
			propertyIdx++;
		}
	}
	
	private double calculatePropertyWeight(IOperator property) {
		if (property.isBlank())
			return 0.0;
		return informativity(property) * diagnosticity(property);
	}
	
	private double diagnosticity(IOperator property) {
		return (double) property.getOperatingState().getExtentSize() / (double) objects.size();
	}
	
	private IConcept getObjectCategory(IContextObject object) {
		IConcept objectCategory = null;
		Set<IConstruct> objConstructs = new HashSet<>(object.getConstructs());
		int objCatIdx = 0;
		List<IConcept> objectCategories = concepts.vertexSet()
				.stream()
				.filter(c -> c.type() == IConcept.SINGLETON)
				.collect(Collectors.toList());
		while (objectCategory == null && objCatIdx < objects.size()) {
			IConcept currentObjCat = objectCategories.get(objCatIdx);
			Set<IConstruct> currObjCatIntent = currentObjCat.getIntent()
					.stream()
					.map(i -> new Construct(i))
					.collect(Collectors.toSet());
			if (objConstructs.equals(currObjCatIntent)) {
				objectCategory = currentObjCat;
			}
			objCatIdx++;
		}
		return objectCategory;
	}
	
	private double informativity(IOperator property) {
		return -binaryLogarithm(
				(double) property.getOperatingState().getExtentSize() 
				/ (double) property.getNextState().getExtentSize()
				);
	}

}
