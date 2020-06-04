package com.jayfella.properties.component;

import com.jayfella.properties.NumberFilters;
import com.jme3.math.Vector4f;
import com.simsilica.lemur.*;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.text.DocumentModel;
import com.simsilica.lemur.text.DocumentModelFilter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Vector4fComponent extends JmeComponent {
    private RollupPanel content;
    private TextField xTextField;
    private TextField yTextField;
    private TextField zTextField;
    private TextField wTextField;
    private VersionedReference<DocumentModel> xVerRef;
    private VersionedReference<DocumentModel> yVerRef;
    private VersionedReference<DocumentModel> zVerRef;
    private VersionedReference<DocumentModel> wVerRef;

    public Vector4fComponent() {
        this((Object)null, (Method)null, (Method)null);
    }

    public Vector4fComponent(Object parent, Field field) {
        super(parent, field);
        create();
    }

    public Vector4fComponent(Object parent, Method getter, Method setter) {
        super(parent, getter, setter);
        create();
    }

    private void create() {
        
        Container contentContainer = new Container();
        contentContainer.setInsets(new Insets3f(5.0F, 5.0F, 5.0F, 5.0F));
        
        this.content = new RollupPanel("", contentContainer, null);
        this.content.setOpen(false);
        
        float minWidth = 50.0F;
        
        DocumentModelFilter xFilter = new DocumentModelFilter();
        xFilter.setInputTransform(NumberFilters.floatFilter());
        
        DocumentModelFilter yFilter = new DocumentModelFilter();
        yFilter.setInputTransform(NumberFilters.floatFilter());
        
        DocumentModelFilter zFilter = new DocumentModelFilter();
        zFilter.setInputTransform(NumberFilters.floatFilter());
        
        DocumentModelFilter wFilter = new DocumentModelFilter();
        wFilter.setInputTransform(NumberFilters.floatFilter());
        
        Container containerX = contentContainer.addChild(new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Last, FillMode.Last)), 0, 0);
        Label xLabel = containerX.addChild(new Label("X"), 0, 0);
        xLabel.setTextVAlignment(VAlignment.Center);
        xLabel.setInsets(new Insets3f(0.0F, 2.0F, 0.0F, 5.0F));
        this.xTextField = containerX.addChild(new TextField(xFilter), 0, 1);
        this.xTextField.setPreferredWidth(minWidth);

        Container containerY = contentContainer.addChild(new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Last, FillMode.Last)), 0, 1);
        Label yLabel = containerY.addChild(new Label("Y"), 0, 0);
        yLabel.setTextVAlignment(VAlignment.Center);
        yLabel.setInsets(new Insets3f(0.0F, 10.0F, 0.0F, 5.0F));
        this.yTextField = containerY.addChild(new TextField(yFilter), 0, 1);
        this.yTextField.setPreferredWidth(minWidth);

        Container containerZ = contentContainer.addChild(new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Last, FillMode.Last)), 0, 2);
        Label zLabel = containerZ.addChild(new Label("Z"), 0, 0);
        zLabel.setTextVAlignment(VAlignment.Center);
        zLabel.setInsets(new Insets3f(0.0F, 10.0F, 0.0F, 5.0F));
        this.zTextField = containerZ.addChild(new TextField(zFilter), 0, 1);
        this.zTextField.setPreferredWidth(minWidth);

        Container containerW = contentContainer.addChild(new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Last, FillMode.Last)), new Object[]{0, 2});
        Label wLabel = containerZ.addChild(new Label("W"), 0, 0);
        wLabel.setTextVAlignment(VAlignment.Center);
        wLabel.setInsets(new Insets3f(0.0F, 10.0F, 0.0F, 5.0F));
        this.wTextField = containerZ.addChild(new TextField(zFilter), 0, 1);
        this.wTextField.setPreferredWidth(minWidth);

        if (this.getReflectedItem() != null) {
            Vector4f vector4f = (Vector4f)this.getReflectedItem().getValue();
            if (vector4f != null) {
                this.xTextField.setText("" + vector4f.x);
                this.yTextField.setText("" + vector4f.y);
                this.zTextField.setText("" + vector4f.z);
                this.wTextField.setText("" + vector4f.w);
            } else {
                this.xTextField.setText("0");
                this.yTextField.setText("0");
                this.zTextField.setText("0");
                this.wTextField.setText("0");
            }
        } else {
            this.xTextField.setText("0");
            this.yTextField.setText("0");
            this.zTextField.setText("0");
            this.wTextField.setText("0");
        }

        this.xVerRef = this.xTextField.getDocumentModel().createReference();
        this.yVerRef = this.yTextField.getDocumentModel().createReference();
        this.zVerRef = this.zTextField.getDocumentModel().createReference();
        this.wVerRef = this.wTextField.getDocumentModel().createReference();
    }

    private Vector4f getVector4f() {
        float x = Float.parseFloat(this.xTextField.getText());
        float y = Float.parseFloat(this.yTextField.getText());
        float z = Float.parseFloat(this.zTextField.getText());
        float w = Float.parseFloat(this.wTextField.getText());
        return new Vector4f(x, y, z, w);
    }

    public void setPropertyName(String name) {
        super.setPropertyName(name);
        this.content.setTitle("Vector4f: " + name);
    }

    public void setValue(Object value) {
        super.setValue(value);
        Vector4f vector4f = (Vector4f)value;
        this.xTextField.setText("" + vector4f.x);
        this.yTextField.setText("" + vector4f.y);
        this.zTextField.setText("" + vector4f.z);
        this.wTextField.setText("" + vector4f.w);
    }

    public Panel getPanel() {
        return this.content;
    }

    public void update(float tpf) {
        boolean xUpdate = this.xVerRef.update();
        boolean yUpdate = this.yVerRef.update();
        boolean zUpdate = this.zVerRef.update();
        boolean wUpdate = this.wVerRef.update();
        if (xUpdate) {
            this.xTextField.setText(NumberFilters.filterFloatValue(this.xTextField.getText()));
        }

        if (yUpdate) {
            this.yTextField.setText(NumberFilters.filterFloatValue(this.yTextField.getText()));
        }

        if (zUpdate) {
            this.zTextField.setText(NumberFilters.filterFloatValue(this.zTextField.getText()));
        }

        if (wUpdate) {
            this.wTextField.setText(NumberFilters.filterFloatValue(this.wTextField.getText()));
        }

        Vector4f oldValue;
        if ((xUpdate || yUpdate || zUpdate || wUpdate) && this.getPropertyChangedEvent() != null) {
            oldValue = this.getVector4f();
            this.getPropertyChangedEvent().propertyChanged(oldValue);
        }

        if (this.getReflectedItem() != null) {
            if (this.isFocused(this.xTextField, this.yTextField, this.zTextField, this.wTextField)) {
                return;
            }

            oldValue = this.getVector4f();
            Vector4f newValue = (Vector4f)this.getReflectedItem().getValue();
            if (!oldValue.equals(newValue)) {
                this.setValue(newValue);
            }
        }

    }
}
