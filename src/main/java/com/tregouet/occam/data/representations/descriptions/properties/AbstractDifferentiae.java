package com.tregouet.occam.data.representations.descriptions.properties;

import java.util.Set;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.alg.setters.weighs.Weighed;

public abstract class AbstractDifferentiae extends DefaultEdge implements Weighed {

	private static final long serialVersionUID = -1429808993785838851L;

	/**
	 * must not make use of rank, coeff, weigh (non-final class parameters)
	 */
	@Override
	abstract public boolean equals(Object o);

	abstract public int getGenusID();

	abstract public Set<IProperty> getProperties();

	@Override
	abstract public Integer getSource();

	abstract public int getSpeciesID();

	@Override
	abstract public Integer getTarget();

	abstract public Double getWeightCoeff();

	/**
	 * must not make use of rank, coeff, weigh (non-final class parameters)
	 */
	@Override
	abstract public int hashCode();

	abstract public Integer rank();

	abstract public void setCoeffFreeWeight(double weight);

	abstract public void setRank(int rank);

	abstract public void setWeightCoeff(double coeff);

}
