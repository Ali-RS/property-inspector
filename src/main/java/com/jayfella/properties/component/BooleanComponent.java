package com.jayfella.properties.component;

import com.simsilica.lemur.Checkbox;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Insets3f;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.core.VersionedReference;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BooleanComponent extends JmeComponent {

    private Container content;

    private Checkbox checkbox;
    private VersionedReference<Boolean> ref;

    public BooleanComponent() {
        this(null, null, null);
    }

    public BooleanComponent(Object parent, Field field) {
        super(parent, field);
        create();
    }

    public BooleanComponent(Object parent, Method getter, Method setter) {
        super(parent, getter, setter);
        create();;
    }

    private void create() {

        content = new Container();
        content.setInsets(new Insets3f(5.0F, 5.0F, 5.0F, 5.0F));

        this.checkbox = content.addChild(new Checkbox("Enabled"));
        if (this.getReflectedItem() != null) {
            boolean value = (Boolean)this.getReflectedItem().getValue();
            this.checkbox.setChecked(value);
        }

        this.ref = this.checkbox.getModel().createReference();

    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);
        boolean bool = (Boolean)value;
        this.checkbox.setChecked(bool);
    }

    @Override
    public Panel getPanel() {
        return this.content;
    }

    @Override
    public void update(float tpf) {
        boolean oldValue;
        if (this.ref.update() && this.getPropertyChangedEvent() != null) {
            oldValue = this.checkbox.isChecked();
            this.getPropertyChangedEvent().propertyChanged(oldValue);
        }

        if (this.getReflectedItem() != null) {
            if (this.isFocused(this.checkbox)) {
                return;
            }

            oldValue = this.checkbox.isChecked();
            boolean newValue = (Boolean)this.getReflectedItem().getValue();
            if (!oldValue == newValue) {
                this.setValue(newValue);
            }
        }

    }
}
