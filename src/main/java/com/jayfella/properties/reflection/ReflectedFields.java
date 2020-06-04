package com.jayfella.properties.reflection;

import java.lang.reflect.Field;
import java.util.*;

public class ReflectedFields {
    private Object parent;
    private List<Field> fields = new ArrayList<>();
    private String[] ignoredProperties;

    public ReflectedFields(Object parent, String... ignoredProperties) {
        this.parent = parent;
        this.ignoredProperties = ignoredProperties;
        create();
    }

    private void create() {

        Collection<Field> declaredFields = getAllDeclaredFields(parent.getClass());

        for (Field field : declaredFields) {

            boolean ignore = false;

            for (String ignored : ignoredProperties) {
                if (field.getName().equals(ignored)) {
                    ignore = true;
                    break;
                }
            }

            if (!ignore) {
                fields.add(field);
            }

        }

    }

    public Object getParent() {
        return parent;
    }

    public List<Field> getFields() {
        return fields;
    }

    public static Collection<Field> getAllDeclaredFields(Class<?> clazz) {

        Map<String, Field> fieldsMap = new HashMap<>();

        Field[] classFields = clazz.getDeclaredFields();

        for (Field f : classFields) {
            fieldsMap.put(f.getName(), f);
        }

        clazz = clazz.getSuperclass();

        while (clazz != Object.class) {
            classFields = clazz.getDeclaredFields();

            for (Field f : classFields) {
                fieldsMap.putIfAbsent(f.getName(), f);
            }

            clazz = clazz.getSuperclass();
        }

        return fieldsMap.values();

    }

}
