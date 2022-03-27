package com.tregouet.occam.alg.setters.parameters.differentiae_coeff;

import com.tregouet.occam.alg.setters.parameters.Parameterizer;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;

public interface DifferentiaeCoeffSetter extends Parameterizer<AbstractDifferentiae> {
	
	DifferentiaeCoeffSetter setContext(IDescription description);

}
