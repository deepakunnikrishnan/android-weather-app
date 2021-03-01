package com.androidnerds.common.mapping;

public interface Mapper<I,O> {
    O map(I input);
}
