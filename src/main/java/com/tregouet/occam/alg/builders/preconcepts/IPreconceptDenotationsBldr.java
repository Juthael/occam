package com.tregouet.occam.alg.builders.preconcepts;

import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.preconcepts.IContextObject;

public interface IPreconceptDenotationsBldr {
	
	Set<IConstruct> getCommonDenotationsOf(List<IContextObject> extent);
	
	Set<IConstruct> getCommonDenotationsOf(Set<IContextObject> extent);

}
