package com.jayfella.properties.reflection;


import com.jayfella.properties.component.JmeComponent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectedProperty<T> implements ReflectedItem<T> {
    private final Object parent;
    private final Method getter;
    private final Method setter;
    private final JmeComponent jmeComponent;

    public ReflectedProperty(Object parent, Method getter, Method setter, JmeComponent jmeComponent) {
        this.parent = parent;
        this.getter = getter;
        this.setter = setter;
        this.jmeComponent = jmeComponent;
        jmeComponent.setPropertyChangedEvent(value -> {
            try {
                setter.invoke(parent, value);
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getValue() {
        try {
            return (T) this.getter.invoke(this.parent);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setValue(T value) {
        try {
            this.setter.invoke(this.parent, value);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update() {
        try {
            Object value = this.getter.invoke(this.parent);
            this.jmeComponent.setValue(value);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
