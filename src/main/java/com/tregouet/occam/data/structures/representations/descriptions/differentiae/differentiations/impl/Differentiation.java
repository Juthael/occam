package com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.impl;

import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IWeighedProperty;

public class Differentiation implements IDifferentiation {
	
	private final List<IWeighedProperty> properties;
	private final double weight;
	
	public Differentiation(List<IWeighedProperty> properties) {
		this.properties = properties;
		double weight = 0.0;
		for (IWeighedProperty property : properties)
			weight += property.weight();
		this.weight = weight;
	}

	@Override
	public Double weight() {
		return weight;
	}

	@Override
	public List<IWeighedProperty> getProperties() {
		return properties;
	}

	@Override
	public int nbOfProperties() {
		return properties.size();
	}

	@Override
	public int hashCode() {
		return Objects.hash(properties, weight);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Differentiation other = (Differentiation) obj;
		return Double.doubleToLongBits(weight) == Double.doubleToLongBits(other.weight)
				&& Objects.equals(properties, other.properties);
	}

}
