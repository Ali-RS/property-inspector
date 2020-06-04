
package com.jayfella.properties;

import com.jayfella.properties.component.*;
import com.jme3.math.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PropertyRegistrationService {

    private static PropertyRegistrationService INSTANCE;

    private final Logger log = Logger.getLogger(PropertyRegistrationService.class.getName());
    private final Map<Class<?>, Class<? extends JmeComponent>> registeredComponents = new HashMap<>();

    private PropertyRegistrationService() {

        this.registerComponent(boolean.class, BooleanComponent.class);
        this.registerComponent(Boolean.class, BooleanComponent.class);
        this.registerComponent(ColorRGBA.class, ColorRGBAComponent.class);
        this.registerComponent(Enum.class, EnumComponent.class);
        this.registerComponent(float.class, FloatComponent.class);
        this.registerComponent(Float.class, FloatComponent.class);
        this.registerComponent(Quaternion.class, QuaternionComponent.class);
        this.registerComponent(String.class, StringComponent.class);
        this.registerComponent(Vector2f.class, Vector2fComponent.class);
        this.registerComponent(Vector3f.class, Vector3fComponent.class);
        this.registerComponent(Vector4f.class, Vector4fComponent.class);

    }

    public static void initialize() {
        INSTANCE = new PropertyRegistrationService();
    }

    public static PropertyRegistrationService getInstance() {
        return INSTANCE;
    }

    public void registerComponent(Class<?> clazz, Class<? extends JmeComponent> componentClass) {

        log.info("Registering " + clazz.getSimpleName() + " with component " + componentClass.getSimpleName());

        Class<?> existing = this.registeredComponents.get(clazz);

        if (existing != null) {
            log.warning("Component " + existing.getSimpleName() + " already exists for type: " + clazz.getSimpleName() + " and is being over-written!");
        }

        this.registeredComponents.put(clazz, componentClass);
    }

    public Map<Class<?>, Class<? extends JmeComponent>> getRegisteredComponents() {
        return Collections.unmodifiableMap(this.registeredComponents);
    }
}
