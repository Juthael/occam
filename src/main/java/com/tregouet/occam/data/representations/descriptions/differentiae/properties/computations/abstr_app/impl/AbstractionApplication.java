package com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.abstr_app.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.abstr_app.OperatorType;
import com.tregouet.occam.data.representations.productions.IBasicProduction;
import com.tregouet.occam.data.representations.productions.IProduction;
import com.tregouet.occam.data.representations.productions.impl.EpsilonProd;
import com.tregouet.occam.data.representations.productions.impl.OmegaProd;
import com.tregouet.occam.data.structures.lambda_terms.IBindings;
import com.tregouet.occam.data.structures.lambda_terms.impl.Bindings;
import com.tregouet.occam.data.structures.languages.alphabets.AVariable;

public class AbstractionApplication implements IAbstractionApplication {

	private final IBindings bindings;
	private final List<IBasicProduction> arguments;

	public AbstractionApplication(List<IBasicProduction> arguments) {
		List<AVariable> boundVariables = new ArrayList<>();
		for (IProduction production : arguments) {
			if (!production.isEpsilon())
				boundVariables.add(production.getVariable());
		}
		bindings = new Bindings(boundVariables);
		this.arguments = arguments;
	}

	protected AbstractionApplication(OperatorType operatorType) {
		this.bindings = null;
		this.arguments = new ArrayList<>();
		switch (operatorType) {
		case EPSILON :
			arguments.add(EpsilonProd.INSTANCE);
			break;
		case OMEGA :
			arguments.add(OmegaProd.INSTANCE);
			break;
		default :
			//do nothing
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		AbstractionApplication other = (AbstractionApplication) obj;
		return Objects.equals(bindings, other.bindings) && Objects.equals(arguments, other.arguments);
	}

	@Override
	public List<IBasicProduction> getArguments() {
		return arguments;
	}

	@Override
	public IBindings getBindings() {
		return bindings;
	}

	@Override
	public int hashCode() {
		return Objects.hash(arguments, bindings);
	}

	@Override
	public boolean isEpsilonOperator() {
		return false;
	}

	@Override
	public boolean isIdentityOperator() {
		for (IBasicProduction argument : arguments) {
			if (!argument.isIdentityProd())
				return false;
		}
		return true;
	}

}
