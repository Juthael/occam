package com.tregouet.occam.data.alphabets.operators_deprecated;

import java.util.List;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.alphabets.productions.IProduction;

public interface IOperator extends ISymbol {
	
	String getName();
	
	List<IProduction> operation();
	
	@Override
	String toString();
	
	int hashCode();
	
	boolean equals(Object o);

}
