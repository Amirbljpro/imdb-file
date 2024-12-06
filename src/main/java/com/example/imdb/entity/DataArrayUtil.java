package com.example.imdb.entity;


import java.util.Arrays;
import java.util.List;

public class DataArrayUtil {

    private static final String SEPARATOR = " ";

    public String convertToDatabaseColumn(List<String> list) {
        return list != null ? String.join(SEPARATOR, list) : "";
    }

    public List<String> convertToEntityAttribute(String data) {
        return data != null && !data.isEmpty() ? Arrays.asList(data.split(SEPARATOR)) : List.of();
    }
}
