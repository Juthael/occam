package com.tregouet.occam.alg.weighers;

import java.util.function.Consumer;

@FunctionalInterface
public interface IWeigher<W extends IWeighed> extends Consumer<W> {

}
