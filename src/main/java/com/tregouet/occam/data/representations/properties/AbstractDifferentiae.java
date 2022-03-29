package com.tregouet.occam.data.representations.properties;

import java.util.Set;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.alg.setters.parameters.Parameterized;
import com.tregouet.occam.alg.setters.weighs.Weighed;

public abstract class AbstractDifferentiae extends DefaultEdge implements Parameterized, Weighed {
	
	private static final long serialVersionUID = -1429808993785838851L;

	abstract public int getGenusID();
	
	abstract public int getSpeciesID();
	
	abstract public Set<IProperty> getProperties();
	
	abstract public Double getWeightCoeff();
	
	abstract public void setWeightCoeff(double coeff);
	
	abstract public void setWeight(double weight);
	
	@Override
	abstract public Integer getSource();
	
	@Override
	abstract public Integer getTarget();
	
	@Override
	abstract public int hashCode();
	
	@Override
	abstract public boolean equals(Object o);

}
