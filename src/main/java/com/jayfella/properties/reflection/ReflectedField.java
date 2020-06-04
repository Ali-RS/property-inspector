package com.jayfella.properties.reflection;

import com.jayfella.properties.component.JmeComponent;

import java.lang.reflect.Field;

public class ReflectedField<T> implements ReflectedItem<T> {

    private final Object parent;
    private final Field field;
    private final JmeComponent jmeComponent;

    @SuppressWarnings("unchecked")
    public ReflectedField(Object parent, Field field, JmeComponent jmeComponent) {
        this.parent = parent;
        this.field = field;
        this.jmeComponent = jmeComponent;

        jmeComponent.setPropertyChangedEvent(value -> setValue((T) value));
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getValue() {
        try {

            boolean accessible = field.isAccessible();

            if (!accessible) {
                field.setAccessible(true);
            }

            T value = (T) field.get(parent);

            if (!accessible) {
                field.setAccessible(false);
            }

            return value;

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setValue(T value) {
        try {

            boolean accessible = field.isAccessible();

            if (!accessible) {
                field.setAccessible(true);
            }

            field.set(parent, value);

            if (!accessible) {
                field.setAccessible(false);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        this.jmeComponent.setValue(getValue());
    }

}
