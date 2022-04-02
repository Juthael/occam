package com.tregouet.occam.data.representations.properties.impl;

import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;
import com.tregouet.occam.data.representations.properties.IProperty;

public class Differentiae extends AbstractDifferentiae {

	private static final long serialVersionUID = 2949125748201321377L;
	
	private final int genusID;
	private final int speciesID;
	private final Set<IProperty> properties;
	private Double coeff = null;
	private Double weight = null;
	private Integer rank = null;
	
	public Differentiae(int genusID, int speciesID, Set<IProperty> properties){
		this.genusID = genusID;
		this.speciesID = speciesID;
		this.properties = properties;
	}
	
	@Override
	public int getGenusID() {
		return genusID;
	}

	@Override
	public int getSpeciesID() {
		return speciesID;
	}

	@Override
	public Set<IProperty> getProperties() {
		return properties;
	}

	@Override
	public Double getWeightCoeff() {
		return coeff;
	}

	@Override
	public Double weight() {
		return weight;
	}


	@Override
	public void setWeightCoeff(double coeff) {
		this.coeff = coeff;
	}


	@Override
	public void setCoeffFreeWeight(double weight) {
		this.weight = weight();
	}
	
	@Override
	public Integer getSource() {
		return genusID;
	}
	
	@Override
	public Integer getTarget() {
		return speciesID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(genusID, properties, speciesID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Differentiae other = (Differentiae) obj;
		return genusID == other.genusID && Objects.equals(properties, other.properties) && speciesID == other.speciesID;
	}
	
	@Override
	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public Integer rank() {
		return rank;
	}

}
