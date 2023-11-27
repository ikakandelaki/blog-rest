package com.blog.util;

import org.modelmapper.ModelMapper;

public final class MappingUtil {
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    private MappingUtil() {

    }

    public static <T> T map(Object source, Class<T> destinationType) {
        return MODEL_MAPPER.map(source, destinationType);
    }
}
