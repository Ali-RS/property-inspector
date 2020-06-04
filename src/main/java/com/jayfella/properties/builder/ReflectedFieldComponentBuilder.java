package com.jayfella.properties.builder;


import com.jayfella.properties.PropertyRegistrationService;
import com.jayfella.properties.component.EnumComponent;
import com.jayfella.properties.component.JmeComponent;
import com.jayfella.properties.reflection.ReflectedFields;
import com.jayfella.properties.reflection.ReflectedProperties;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReflectedFieldComponentBuilder implements ComponentBuilder {

    // private ReflectedProperties reflectedProperties;
    private ReflectedFields reflectedFields;

    @Override
    public void setObject(Object object, String... ignoredProperties) {
        this.reflectedFields = new ReflectedFields(object, ignoredProperties);
    }

    @Override
    public List<JmeComponent> build() {
        Map<Class<?>, Class<? extends JmeComponent>> registeredComponents = PropertyRegistrationService.getInstance().getRegisteredComponents();
        List<JmeComponent> components = new ArrayList<>();

        for (Field field : this.reflectedFields.getFields()) {

            boolean accessible = field.isAccessible();

            if (!accessible) {
                field.setAccessible(true);
            }

            final Object value;

            try {
                value = field.get(reflectedFields.getParent());


                Map.Entry<Class<?>, Class<? extends JmeComponent>> entry = registeredComponents.entrySet().stream()
                        .filter( c -> value.getClass() == c.getKey() || value.getClass().isEnum() && c.getKey() == Enum.class)
                        .findFirst().orElse(null);

                if (entry != null) {

                    Class<? extends JmeComponent> componentClass = entry.getValue();
                    Constructor<? extends JmeComponent> constructor = componentClass.getConstructor(Object.class, Field.class);

                    JmeComponent component = constructor.newInstance(reflectedFields.getParent(), field);
                    component.setPropertyName(ReflectedProperties.getSuffix(field.getName()));

                    if (value.getClass().isEnum()) {
                        Class<? extends Enum<?>> values = (Class<? extends Enum<?>>) value.getClass();
                        EnumComponent enumComponent = (EnumComponent) component;
                        enumComponent.setEnumValues(values);
                    }

                    components.add(component);

                }

            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }

            if (!accessible) {
                field.setAccessible(false);
            }

        }

        return components;

    }



}
