package com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.impl;

import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;

/**
 * Weighing not supported for now
 * @author Gael Tregouet
 *
 */
public class Differentiation implements IDifferentiation {

	private final List<IProperty> properties;
	private final double weight;

	public Differentiation(List<IProperty> properties) {
		this.properties = properties;
		double weight = 0.0;
		for (IProperty property : properties)
			weight += property.weight();
		this.weight = weight;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Differentiation other = (Differentiation) obj;
		return Double.doubleToLongBits(weight) == Double.doubleToLongBits(other.weight)
				&& Objects.equals(properties, other.properties);
	}

	@Override
	public List<IProperty> getProperties() {
		return properties;
	}

	@Override
	public int hashCode() {
		return Objects.hash(properties, weight);
	}

	@Override
	public int nbOfProperties() {
		return properties.size();
	}

	@Override
	public Double weight() {
		return weight;
	}

}
