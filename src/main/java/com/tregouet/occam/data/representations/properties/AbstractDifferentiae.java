package com.tregouet.occam.data.representations.properties;

import java.util.Set;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.alg.setters.weighs.Weighed;

public abstract class AbstractDifferentiae extends DefaultEdge implements Weighed {
	
	private static final long serialVersionUID = -1429808993785838851L;

	abstract public int getGenusID();
	
	abstract public int getSpeciesID();
	
	abstract public Set<IProperty> getProperties();
	
	abstract public Double getWeightCoeff();
	
	abstract public void setWeightCoeff(double coeff);
	
	abstract public void setCoeffFreeWeight(double weight);
	
	@Override
	abstract public Integer getSource();
	
	@Override
	abstract public Integer getTarget();
	
	/**
	 * must not make use of rank, coeff, weigh (non-final class parameters)
	 */
	@Override
	abstract public int hashCode();
	
	/**
	 * must not make use of rank, coeff, weigh (non-final class parameters)
	 */
	@Override
	abstract public boolean equals(Object o);
	
	abstract public void setRank(int rank);
	
	abstract public Integer rank();

}
