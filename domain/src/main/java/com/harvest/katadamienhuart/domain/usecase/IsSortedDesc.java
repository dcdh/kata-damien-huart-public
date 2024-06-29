package com.harvest.katadamienhuart.domain.usecase;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class IsSortedDesc<T extends Comparable<? super T>> implements Function<List<T>, Boolean> {
    @Override
    public Boolean apply(final List<T> elements) {
        return IntStream.range(0, elements.size() - 1)
                .allMatch(index -> elements.get(index).compareTo(elements.get(index + 1)) > 0);
    }
}
