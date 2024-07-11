package com.app.mfi2.utils;

import org.modelmapper.ModelMapper;

public class MapperDTO {
    private final static ModelMapper modelMapper = new ModelMapper();
    public static  <D, E> D convertToDto(E entity, Class<D> dtoClass) {
        if (entity == null) {
            throw new IllegalArgumentException("Source cannot be null");
        }
        return modelMapper.map(entity,dtoClass);
    }
    public static <E, D> E convertToEntity(D dto, Class<E> entityClass) {
         if (dto == null) {
            throw new IllegalArgumentException("Source cannot be null");
        }
        return modelMapper.map(dto, entityClass);
    }
}
