package com.jayfella.properties.component;

import com.jayfella.properties.NumberFilters;
import com.jme3.math.Vector2f;
import com.simsilica.lemur.*;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.text.DocumentModel;
import com.simsilica.lemur.text.DocumentModelFilter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Vector2fComponent extends JmeComponent {

    private Container content;
    private TextField xTextField;
    private TextField yTextField;
    private VersionedReference<DocumentModel> xVerRef;
    private VersionedReference<DocumentModel> yVerRef;

    public Vector2fComponent() {
        this(null, null, null);
    }

    public Vector2fComponent(Object parent, Field field) {
        super(parent, field);
        create();
    }

    public Vector2fComponent(Object parent, Method getter, Method setter) {
        super(parent, getter, setter);
        create();
    }

    private void create() {
        content = new Container();
        content.setInsets(new Insets3f(5.0F, 5.0F, 5.0F, 5.0F));

        float minWidth = 50.0F;

        DocumentModelFilter xFilter = new DocumentModelFilter();
        xFilter.setInputTransform(NumberFilters.floatFilter());

        DocumentModelFilter yFilter = new DocumentModelFilter();
        yFilter.setInputTransform(NumberFilters.floatFilter());

        Container containerX = content.addChild(new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Last, FillMode.Last)), 0, 0);
        Label xLabel = containerX.addChild(new Label("X"), 0, 0);
        xLabel.setTextVAlignment(VAlignment.Center);
        xLabel.setInsets(new Insets3f(0.0F, 2.0F, 0.0F, 5.0F));
        this.xTextField = containerX.addChild(new TextField(xFilter), 0, 1);
        this.xTextField.setPreferredWidth(minWidth);

        Container containerY = content.addChild(new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Last, FillMode.Last)), 0, 1);
        Label yLabel = containerY.addChild(new Label("Y"), 0, 0);
        yLabel.setTextVAlignment(VAlignment.Center);
        yLabel.setInsets(new Insets3f(0.0F, 10.0F, 0.0F, 5.0F));
        this.yTextField = containerY.addChild(new TextField(yFilter), 0, 1);
        this.yTextField.setPreferredWidth(minWidth);

        if (this.getReflectedItem() != null) {
            Vector2f vector2f = (Vector2f)this.getReflectedItem().getValue();
            if (vector2f != null) {
                this.xTextField.setText("" + vector2f.x);
                this.yTextField.setText("" + vector2f.y);
            } else {
                this.xTextField.setText("0");
                this.yTextField.setText("0");
            }
        } else {
            this.xTextField.setText("0");
            this.yTextField.setText("0");
        }

        this.xVerRef = this.xTextField.getDocumentModel().createReference();
        this.yVerRef = this.yTextField.getDocumentModel().createReference();
    }

    private Vector2f getVector2f() {
        float x = Float.parseFloat(this.xTextField.getText());
        float y = Float.parseFloat(this.yTextField.getText());
        return new Vector2f(x, y);
    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);
        Vector2f vector2f = (Vector2f)value;
        this.xTextField.setText("" + vector2f.x);
        this.yTextField.setText("" + vector2f.y);
    }

    @Override
    public Panel getPanel() {
        return this.content;
    }

    @Override
    public void update(float tpf) {
        boolean xUpdate = this.xVerRef.update();
        boolean yUpdate = this.yVerRef.update();
        if (xUpdate) {
            this.xTextField.setText(NumberFilters.filterFloatValue(this.xTextField.getText()));
        }

        if (yUpdate) {
            this.yTextField.setText(NumberFilters.filterFloatValue(this.yTextField.getText()));
        }

        Vector2f oldValue;
        if ((xUpdate || yUpdate) && this.getPropertyChangedEvent() != null) {
            oldValue = this.getVector2f();
            this.getPropertyChangedEvent().propertyChanged(oldValue);
        }

        if (this.getReflectedItem() != null) {
            if (this.isFocused(this.xTextField, this.yTextField)) {
                return;
            }

            oldValue = this.getVector2f();
            Vector2f newValue = (Vector2f)this.getReflectedItem().getValue();
            if (!oldValue.equals(newValue)) {
                this.setValue(newValue);
            }
        }

    }
}
