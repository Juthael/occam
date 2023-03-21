package com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties;

import java.util.Set;

import com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.IDenotation;

public interface IRelationalProperty extends IProperty {

	Set<IDenotation> getFunctionSet();

}
