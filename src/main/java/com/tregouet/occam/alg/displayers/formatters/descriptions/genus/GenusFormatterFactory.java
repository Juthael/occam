package com.tregouet.occam.alg.displayers.formatters.descriptions.genus;

import com.tregouet.occam.alg.displayers.formatters.descriptions.genus.impl.IdThenExtent;

public class GenusFormatterFactory {

	public static final GenusFormatterFactory INSTANCE = new GenusFormatterFactory();

	private GenusFormatterFactory() {
	}

	public GenusFormatter apply(GenusFormatterStrategy strategy) {
		switch(strategy) {
		case ID_THEN_EXTENT :
			return new IdThenExtent();
		default :
			return null;
		}
	}

}
