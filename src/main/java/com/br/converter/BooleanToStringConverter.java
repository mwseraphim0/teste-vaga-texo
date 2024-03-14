package com.br.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {

    private static final String ACTIVE = "yes";
    private static final String INACTIVE = "no";

    @Override
    public String convertToDatabaseColumn(Boolean value) {
        return (value == null || !value) ? INACTIVE : ACTIVE;
    }

    @Override
    public Boolean convertToEntityAttribute(String value) {
        return ACTIVE.equalsIgnoreCase(value);
    }
}
