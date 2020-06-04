package com.jayfella.properties.component;

import com.simsilica.lemur.*;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.text.DocumentModel;
import com.simsilica.lemur.text.DocumentModelFilter;
import com.simsilica.lemur.text.TextFilters;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class IntComponent extends JmeComponent {

    private RollupPanel content;
    private TextField intTextField;
    private VersionedReference<DocumentModel> ref;

    public IntComponent() {
        this(null, null, null);
    }

    public IntComponent(Object parent, Field field) {
        super(parent, field);
        create();
    }

    public IntComponent(Object parent, Method getter, Method setter) {
        super(parent, getter, setter);
        create();
    }

    private void create() {
        Container contentContainer = new Container();
        contentContainer.setInsets(new Insets3f(5.0F, 5.0F, 5.0F, 5.0F));

        this.content = new RollupPanel("", contentContainer, null);
        this.content.setOpen(false);
        float minWidth = 50.0F;

        DocumentModelFilter intFilter = new DocumentModelFilter();
        intFilter.setInputTransform(TextFilters.numeric());

        this.intTextField = contentContainer.addChild(new TextField(intFilter));
        this.intTextField.setPreferredWidth(minWidth);

        if (this.getReflectedItem() != null) {

            int intValue = (Integer)this.getReflectedItem().getValue();
            this.intTextField.setText("" + intValue);

        } else {
            this.intTextField.setText("0");
        }

        this.ref = this.intTextField.getDocumentModel().createReference();
    }

    @Override
    public void setPropertyName(String name) {
        super.setPropertyName(name);
        this.content.setTitle("Int: " + name);
    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);
        int intValue = (int) value;
        this.intTextField.setText("" + intValue);
    }

    @Override
    public Panel getPanel() {
        return content;
    }

    @Override
    public void update(float var1) {

        if (this.ref.update()) {

            if (this.getPropertyChangedEvent() != null) {
                this.getPropertyChangedEvent().propertyChanged(Integer.parseInt(this.intTextField.getText()));
            }
        }

        if (this.getReflectedItem() != null) {
            if (this.isFocused(this.intTextField)) {
                return;
            }

            int oldValue = Integer.parseInt(this.intTextField.getText());
            int newValue = (Integer) this.getReflectedItem().getValue();
            if (oldValue != newValue) {
                this.setValue(newValue);
            }
        }

    }
}
