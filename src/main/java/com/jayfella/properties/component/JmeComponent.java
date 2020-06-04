package com.jayfella.properties.component;

import com.jayfella.properties.event.PropertyChangedEvent;
import com.jayfella.properties.reflection.ReflectedField;
import com.jayfella.properties.reflection.ReflectedItem;
import com.jayfella.properties.reflection.ReflectedProperty;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Insets3f;
import com.simsilica.lemur.Panel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class JmeComponent {

    public static final float TEXTFIELD_MIN_SIZE = 50.0f;
    public static final Insets3f INSETS = new Insets3f(5,5,5,5);

    private String propertyName = "";
    private final ReflectedItem<?> reflectedItem;
    private PropertyChangedEvent propertyChangedEvent;

    public JmeComponent(Object parent, Field field) {
        if (parent != null) {
            this.reflectedItem = new ReflectedField<>(parent, field, this);
        }
        else {
            this.reflectedItem = null;
        }
    }

    public JmeComponent(Object parent, Method getter, Method setter) {
        if (parent != null) {
            this.reflectedItem = new ReflectedProperty<>(parent, getter, setter, this);
        } else {
            this.reflectedItem = null;
        }

    }

    public ReflectedItem<?> getReflectedItem() {
        return this.reflectedItem;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public void setPropertyName(String name) {
        this.propertyName = name;
    }

    public void setValue(Object value) {
        if (this.propertyChangedEvent != null) {
            this.propertyChangedEvent.propertyChanged(value);
        }

    }

    public PropertyChangedEvent getPropertyChangedEvent() {
        return this.propertyChangedEvent;
    }

    public void setPropertyChangedEvent(PropertyChangedEvent event) {
        this.propertyChangedEvent = event;
    }

    public abstract Panel getPanel();

    public abstract void update(float tpf);

    protected boolean isFocused(Spatial... spatials) {

        if (GuiGlobals.getInstance().getCurrentFocus() != null) {

            for (Spatial spatial : spatials) {
                if (GuiGlobals.getInstance().getCurrentFocus().equals(spatial)) {
                    return true;
                }
            }
        }

        return false;
    }
}
