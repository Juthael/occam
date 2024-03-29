package com.tregouet.occam.data.structures.representations.descriptions.differentiae.impl;

import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiationSet;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;

public class Differentiae extends ADifferentiae {

	private static final long serialVersionUID = 2949125748201321377L;

	private final int genusID;
	private final int speciesID;
	private final Set<IProperty> properties;
	private IDifferentiationSet differentiationSet;
	private Double coeff = null;
	private Double weight = null;
	private Integer rank = null;

	public Differentiae(int genusID, int speciesID, Set<IProperty> properties, IDifferentiationSet differentiationSet) {
		this.genusID = genusID;
		this.speciesID = speciesID;
		this.properties = properties;
		this.differentiationSet = differentiationSet;
		if (differentiationSet != null)
			weight = differentiationSet.weight();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Differentiae other = (Differentiae) obj;
		return speciesID == other.speciesID && genusID == other.genusID && Objects.equals(properties, other.properties);
	}

	@Override
	public Double getCoeffFreeWeight() {
		return weight;
	}

	@Override
	public IDifferentiationSet getDifferentiationSet() {
		return differentiationSet;
	}

	@Override
	public int getGenusID() {
		return genusID;
	}

	@Override
	public Set<IProperty> getProperties() {
		return properties;
	}

	@Override
	public Integer getSource() {
		return genusID;
	}

	@Override
	public int getSpeciesID() {
		return speciesID;
	}

	@Override
	public Integer getTarget() {
		return speciesID;
	}

	@Override
	public Double getWeightCoeff() {
		return coeff;
	}

	@Override
	public int hashCode() {
		return Objects.hash(genusID, properties, speciesID);
	}

	@Override
	public Integer rank() {
		return rank;
	}

	@Override
	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public void setWeightCoeff(double coeff) {
		this.coeff = coeff;
	}

	@Override
	public Double weight() {
		return coeff * weight;
	}

}
