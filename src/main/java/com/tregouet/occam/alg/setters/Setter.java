package com.tregouet.occam.alg.setters;

import java.util.function.Consumer;

@FunctionalInterface
public interface Setter<T> extends Consumer<T> {

}
