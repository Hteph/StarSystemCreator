package com.github.hteph.Utilities;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public class StreamUtilities {

    public static <T> Stream<T> getStreamEmptyIfNull(Collection<T> collection) {
        return Optional.ofNullable(collection)
                       .map(Collection::stream)
                       .orElseGet(Stream::empty);
    }
}
