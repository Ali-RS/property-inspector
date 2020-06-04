package com.jayfella.properties.component;

import com.simsilica.lemur.Container;
import com.simsilica.lemur.Insets3f;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.TextField;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.text.DocumentModel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StringComponent extends JmeComponent {

    private Container content;
    private TextField stringTextField;
    private VersionedReference<DocumentModel> ref;

    public StringComponent() {
        this(null, null, null);
    }

    public StringComponent(Object parent, Field field) {
        super(parent, field);
        create();
    }

    public StringComponent(Object parent, Method getter, Method setter) {
        super(parent, getter, setter);
        create();
    }

    private void create() {

        // Container contentContainer = new Container();
        content = new Container();
        content.setInsets(new Insets3f(5.0F, 5.0F, 5.0F, 5.0F));

        // this.content = new RollupPanel("", contentContainer, null);
        // this.content.setOpen(false);
        // float minWidth = 50.0F;

        this.stringTextField = content.addChild(new TextField(""));

        if (this.getReflectedItem() != null) {
            String value = (String) this.getReflectedItem().getValue();
            this.stringTextField.setText(value == null ? "" : value);
        }

        this.ref = this.stringTextField.getDocumentModel().createReference();

    }

//    public void setPropertyName(String name) {
//        super.setPropertyName(name);
//        this.content.setTitle("String: " + name);
//    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);
        String string = (String)value;
        this.stringTextField.setText(string);
    }

    @Override
    public Panel getPanel() {
        return this.content;
    }

    @Override
    public void update(float tpf) {
        String oldValue;

        if (this.ref.update() && this.getPropertyChangedEvent() != null) {
            oldValue = this.stringTextField.getText().trim();
            this.getPropertyChangedEvent().propertyChanged(oldValue.length() == 0 ? null : oldValue);
        }

        if (this.getReflectedItem() != null) {
            if (this.isFocused(this.stringTextField)) {
                return;
            }

            oldValue = this.stringTextField.getText();
            String newValue = (String)this.getReflectedItem().getValue();
            if (!oldValue.equals(newValue)) {
                this.setValue(newValue);
            }
        }

    }
}
