package com.jayfella.properties.component;

import com.jayfella.properties.NumberFilters;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Insets3f;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.TextField;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.text.DocumentModel;
import com.simsilica.lemur.text.DocumentModelFilter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FloatComponent extends JmeComponent {
    private Container content;
    private TextField floatTextField;
    private VersionedReference<DocumentModel> ref;

    public FloatComponent() {
        this(null, null, null);
    }

    public FloatComponent(Object parent, Field field) {
        super(parent, field);
        create();
    }

    public FloatComponent(Object parent, Method getter, Method setter) {
        super(parent, getter, setter);
        create();
    }

    private void create() {
        content = new Container();
        content.setInsets(new Insets3f(5.0F, 5.0F, 5.0F, 5.0F));

        float minWidth = 50.0F;

        DocumentModelFilter floatFilter = new DocumentModelFilter();
        floatFilter.setInputTransform(NumberFilters.floatFilter());
        this.floatTextField = content.addChild(new TextField(floatFilter), new Object[0]);
        this.floatTextField.setPreferredWidth(minWidth);
        if (this.getReflectedItem() != null) {
            Float floatValue = (Float)this.getReflectedItem().getValue();
            if (floatValue != null) {
                this.floatTextField.setText("" + floatValue);
            } else {
                this.floatTextField.setText("0");
            }
        } else {
            this.floatTextField.setText("0");
        }

        this.ref = this.floatTextField.getDocumentModel().createReference();
    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);
        Float floatValue = (Float)value;
        this.floatTextField.setText("" + floatValue);
    }

    @Override
    public Panel getPanel() {
        return this.content;
    }

    @Override
    public void update(float tpf) {
        if (this.ref.update()) {
            this.floatTextField.setText(NumberFilters.filterFloatValue(this.floatTextField.getText()));
            if (this.getPropertyChangedEvent() != null) {
                this.getPropertyChangedEvent().propertyChanged(Float.parseFloat(this.floatTextField.getText()));
            }
        }

        if (this.getReflectedItem() != null) {
            if (this.isFocused(this.floatTextField)) {
                return;
            }

            float oldValue = Float.parseFloat(this.floatTextField.getText());
            float newValue = (Float)this.getReflectedItem().getValue();
            if (oldValue != newValue) {
                this.setValue(newValue);
            }
        }

    }
}
