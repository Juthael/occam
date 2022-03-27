package com.tregouet.occam.alg.setters;

import java.util.function.Consumer;

@FunctionalInterface
public interface Setter<W extends Setup> extends Consumer<W> {

}
