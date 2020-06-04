package com.jayfella.properties.reflection;

public interface ReflectedItem<T> {

    T getValue();
    void setValue(T value);

    void update();
}
