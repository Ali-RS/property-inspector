package com.jayfella.properties.builder;

import com.jayfella.properties.component.JmeComponent;

import java.util.List;

public interface ComponentBuilder {

    void setObject(Object object, String... ignoredProperties);

    List<JmeComponent> build();
}
