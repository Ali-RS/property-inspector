package com.jayfella.properties.builder;


import com.jayfella.properties.PropertyRegistrationService;
import com.jayfella.properties.component.EnumComponent;
import com.jayfella.properties.component.JmeComponent;
import com.jayfella.properties.reflection.ReflectedProperties;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ReflectedComponentBuilder implements ComponentBuilder {
    private ReflectedProperties reflectedProperties;

    public ReflectedComponentBuilder() {
    }

    public void setObject(Object object, String... ignoredProperties) {
        this.reflectedProperties = new ReflectedProperties(object, ignoredProperties);
    }

    public ReflectedProperties getReflectedProperties() {
        return this.reflectedProperties;
    }

    public List<JmeComponent> build() {

        Map<Class<?>, Class<? extends JmeComponent>> registeredComponents = PropertyRegistrationService.getInstance().getRegisteredComponents();
        List<JmeComponent> components = new ArrayList<>();

        for (Method getter : this.reflectedProperties.getGetters()) {

            Entry<Class<?>, Class<? extends JmeComponent>> entry = registeredComponents.entrySet().stream()
                    .filter( c -> getter.getReturnType() == c.getKey() || getter.getReturnType().isEnum() && c.getKey() == Enum.class)
                    .findFirst().orElse(null);

            if (entry != null) {
                Method setter = this.reflectedProperties.getSetters().stream().filter((s) -> {
                    String getterSuffix = ReflectedProperties.getSuffix(getter.getName());
                    String setterSuffix = ReflectedProperties.getSuffix(s.getName());
                    return getterSuffix.equalsIgnoreCase(setterSuffix);
                }).findFirst().orElse(null);

                try {

                    Class<? extends JmeComponent> componentClass = entry.getValue();
                    Constructor<? extends JmeComponent> constructor = componentClass.getConstructor(Object.class, Method.class, Method.class);

                    JmeComponent component = constructor.newInstance(this.reflectedProperties.getObject(), getter, setter);
                    component.setPropertyName(ReflectedProperties.getSuffix(getter.getName()));

                    if (getter.getReturnType().isEnum()) {
                        Class<? extends Enum<?>> values = (Class<? extends Enum<?>>) getter.getReturnType();
                        EnumComponent enumComponent = (EnumComponent) component;
                        enumComponent.setEnumValues(values);
                    }

                    components.add(component);
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException var12) {
                    var12.printStackTrace();
                }
            }
        }

        return components;
    }
}
