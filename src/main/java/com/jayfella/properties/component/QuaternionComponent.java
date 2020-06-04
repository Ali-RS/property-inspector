package com.jayfella.properties.component;

import com.jayfella.properties.NumberFilters;
import com.jme3.math.Quaternion;
import com.simsilica.lemur.*;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.text.DocumentModel;
import com.simsilica.lemur.text.DocumentModelFilter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class QuaternionComponent extends JmeComponent {
    
    private Container content;
    
    private TextField xTextField;
    private TextField yTextField;
    private TextField zTextField;
    
    private VersionedReference<DocumentModel> xVerRef;
    private VersionedReference<DocumentModel> yVerRef;
    private VersionedReference<DocumentModel> zVerRef;

    public QuaternionComponent() {
        this((Object)null, (Method)null, (Method)null);
    }

    public QuaternionComponent(Object parent, Field field) {
        super(parent, field);
        create();
    }

    public QuaternionComponent(Object parent, Method getter, Method setter) {
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
        DocumentModelFilter zFilter = new DocumentModelFilter();
        zFilter.setInputTransform(NumberFilters.floatFilter());
        
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
        
        Container containerZ = content.addChild(new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Last, FillMode.Last)), 0, 2);
        Label zLabel = containerZ.addChild(new Label("Z"), 0, 0);
        zLabel.setTextVAlignment(VAlignment.Center);
        zLabel.setInsets(new Insets3f(0.0F, 10.0F, 0.0F, 5.0F));
        this.zTextField = containerZ.addChild(new TextField(zFilter), 0, 1);
        this.zTextField.setPreferredWidth(minWidth);
        
        if (this.getReflectedItem() != null) {
            Quaternion quaternion = (Quaternion)this.getReflectedItem().getValue();
            if (quaternion != null) {
                float[] euler = quaternion.toAngles(null);
                this.xTextField.setText("" + euler[0]);
                this.yTextField.setText("" + euler[1]);
                this.zTextField.setText("" + euler[2]);
            } else {
                this.xTextField.setText("0");
                this.yTextField.setText("0");
                this.zTextField.setText("0");
            }
        } else {
            this.xTextField.setText("0");
            this.yTextField.setText("0");
            this.zTextField.setText("0");
        }

        this.xVerRef = this.xTextField.getDocumentModel().createReference();
        this.yVerRef = this.yTextField.getDocumentModel().createReference();
        this.zVerRef = this.zTextField.getDocumentModel().createReference();
    }

    private Quaternion getQuaternion() {
        float x = Float.parseFloat(this.xTextField.getText());
        float y = Float.parseFloat(this.yTextField.getText());
        float z = Float.parseFloat(this.zTextField.getText());
        return (new Quaternion()).fromAngles(x, y, z);
    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);
        Quaternion quaternion = (Quaternion)value;
        float[] euler = quaternion.toAngles(null);
        this.xTextField.setText("" + euler[0]);
        this.yTextField.setText("" + euler[1]);
        this.zTextField.setText("" + euler[2]);
    }

    @Override
    public Panel getPanel() {
        return this.content;
    }

    @Override
    public void update(float tpf) {
        boolean xUpdate = this.xVerRef.update();
        boolean yUpdate = this.yVerRef.update();
        boolean zUpdate = this.zVerRef.update();
        if (xUpdate) {
            this.xTextField.setText(NumberFilters.filterFloatValue(this.xTextField.getText()));
        }

        if (yUpdate) {
            this.yTextField.setText(NumberFilters.filterFloatValue(this.yTextField.getText()));
        }

        if (zUpdate) {
            this.zTextField.setText(NumberFilters.filterFloatValue(this.zTextField.getText()));
        }

        Quaternion oldValue;
        if ((xUpdate || yUpdate || zUpdate) && this.getPropertyChangedEvent() != null) {
            oldValue = this.getQuaternion();
            this.getPropertyChangedEvent().propertyChanged(oldValue);
        }

        if (this.getReflectedItem() != null) {
            if (this.isFocused(this.xTextField, this.yTextField, this.zTextField)) {
                return;
            }

            oldValue = this.getQuaternion();
            Quaternion newValue = (Quaternion)this.getReflectedItem().getValue();
            if (!oldValue.equals(newValue)) {
                this.setValue(newValue);
            }
        }

    }
}
