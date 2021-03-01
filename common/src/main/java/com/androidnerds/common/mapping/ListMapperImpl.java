package com.androidnerds.common.mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListMapperImpl<I,O> implements ListMapper<I,O> {

    private final Mapper<I,O> mapper;

    public ListMapperImpl(Mapper<I,O> mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<O> map(List<I> input) {
        return null == input ? Collections.emptyList(): input.stream().map(mapper::map).collect(Collectors.toList());
    }
}

