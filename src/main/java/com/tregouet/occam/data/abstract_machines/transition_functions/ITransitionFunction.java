package com.tregouet.occam.data.abstract_machines.transition_functions;

import java.util.Set;

import com.tregouet.occam.data.concepts.IDifferentiae;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.specific.IProduction;
import com.tregouet.occam.data.languages.specific.IProperty;

public interface ITransitionFunction {
	
	Set<IProduction> getInputAlphabet();
	
	Set<AVariable> getStackAlphabet();
	
	Set<IProperty> getStateLanguage(int iD);
	
	Set<IProperty> getMachineLanguage();
	
	Set<IDifferentiae> getDifferentiae(int iD);

}
