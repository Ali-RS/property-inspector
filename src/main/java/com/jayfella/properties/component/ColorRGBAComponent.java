package com.jayfella.properties.component;

import com.jayfella.properties.NumberFilters;
import com.jme3.math.ColorRGBA;
import com.simsilica.lemur.*;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.TextField;
import com.simsilica.lemur.component.IconComponent;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.text.DocumentModel;
import com.simsilica.lemur.text.DocumentModelFilter;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ColorRGBAComponent extends JmeComponent {

    private Container content;

    private TextField rTextField;
    private TextField gTextField;
    private TextField bTextField;
    private TextField aTextField;

    private VersionedReference<DocumentModel> rVerRef;
    private VersionedReference<DocumentModel> gVerRef;
    private VersionedReference<DocumentModel> bVerRef;
    private VersionedReference<DocumentModel> aVerRef;

    public ColorRGBAComponent() {
        this(null, null, null);
    }

    public ColorRGBAComponent(Object parent, Field field) {
        super(parent, field);
        create();
    }

    public ColorRGBAComponent(Object parent, Method getter, Method setter) {
        super(parent, getter, setter);
        create();
    }

    private void create() {

        content = new Container();
        content.setInsets(new Insets3f(5.0F, 5.0F, 5.0F, 5.0F));

        float minWidth = 100.0F;

        DocumentModelFilter rFilter = new DocumentModelFilter();
        rFilter.setInputTransform(NumberFilters.floatFilter());

        DocumentModelFilter gFilter = new DocumentModelFilter();
        gFilter.setInputTransform(NumberFilters.floatFilter());

        DocumentModelFilter bFilter = new DocumentModelFilter();
        bFilter.setInputTransform(NumberFilters.floatFilter());

        DocumentModelFilter aFilter = new DocumentModelFilter();
        aFilter.setInputTransform(NumberFilters.floatFilter());

        // red
        Container containerR = content.addChild(new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Last, FillMode.Last)), 0, 0);
        Label rLabel = containerR.addChild(new Label("R"), 0, 0);
        rLabel.setTextVAlignment(VAlignment.Center);
        rLabel.setInsets(new Insets3f(0.0F, 2.0F, 0.0F, 5.0F));
        rTextField = containerR.addChild(new TextField(rFilter), 0, 1);
        rTextField.setPreferredWidth(minWidth);
        rTextField.setTextVAlignment(VAlignment.Center);

        // green
        Container containerG = content.addChild(new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Last, FillMode.Last)), 0, 1);
        Label gLabel = containerG.addChild(new Label("G"), 0, 0);
        gLabel.setTextVAlignment(VAlignment.Center);
        gLabel.setInsets(new Insets3f(0.0F, 10.0F, 0.0F, 5.0F));
        gTextField = containerG.addChild(new TextField(gFilter), 0, 1);
        gTextField.setPreferredWidth(minWidth);
        gTextField.setTextVAlignment(VAlignment.Center);

        // blue
        Container containerB = content.addChild(new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Last, FillMode.Last)), 0, 2);
        Label bLabel = containerB.addChild(new Label("B"), 0, 0);
        bLabel.setTextVAlignment(VAlignment.Center);
        bLabel.setInsets(new Insets3f(0.0F, 10.0F, 0.0F, 5.0F));
        bTextField = containerB.addChild(new TextField(bFilter), 0, 1);
        bTextField.setPreferredWidth(minWidth);
        bTextField.setTextVAlignment(VAlignment.Center);

        // alpha
        Container containerA = content.addChild(new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Last, FillMode.Last)), 0, 3);
        Label aLabel = containerA.addChild(new Label("A"), 0, 0);
        aLabel.setTextVAlignment(VAlignment.Center);
        aLabel.setInsets(new Insets3f(0.0F, 10.0F, 0.0F, 5.0F));
        aTextField = containerA.addChild(new TextField(aFilter), 0, 1);
        aTextField.setPreferredWidth(minWidth);
        aTextField.setTextVAlignment(VAlignment.Center);

        // Color selection button
        Button colorSelector = content.addChild(new Button("Color..."), 0, 4);
        // colorSelector.setIcon(new IconComponent("Textures/color-picker.png"));
        colorSelector.addClickCommands(source -> {

            ColorRGBA currentColor = getColorRGBA();

            Color awtColor = new Color(
                    (int) (currentColor.r * 255),
                    (int) (currentColor.g * 255),
                    (int) (currentColor.b * 255),
                    (int) (currentColor.a * 255));

            Color newAwtColor = JColorChooser.showDialog(null, "Choose Color...", awtColor, true);

            if (newAwtColor != null) {

                ColorRGBA newColor = new ColorRGBA(
                        newAwtColor.getRed() / 255f,
                        newAwtColor.getGreen() / 255f,
                        newAwtColor.getBlue() / 255f,
                        newAwtColor.getAlpha() / 255f);

                setValue(newColor);

            }

        });

        if (this.getReflectedItem() != null) {
            ColorRGBA colorRGBA = (ColorRGBA)this.getReflectedItem().getValue();
            if (colorRGBA != null) {
                this.rTextField.setText("" + colorRGBA.r);
                this.gTextField.setText("" + colorRGBA.g);
                this.bTextField.setText("" + colorRGBA.b);
                this.aTextField.setText("" + colorRGBA.a);
            } else {
                this.rTextField.setText("0");
                this.gTextField.setText("0");
                this.bTextField.setText("0");
                this.aTextField.setText("0");
            }
        } else {
            this.rTextField.setText("0");
            this.gTextField.setText("0");
            this.bTextField.setText("0");
            this.aTextField.setText("0");
        }

        this.rVerRef = this.rTextField.getDocumentModel().createReference();
        this.gVerRef = this.gTextField.getDocumentModel().createReference();
        this.bVerRef = this.bTextField.getDocumentModel().createReference();
        this.aVerRef = this.aTextField.getDocumentModel().createReference();
    }

    private ColorRGBA getColorRGBA() {
        float r = Float.parseFloat(this.rTextField.getText());
        float g = Float.parseFloat(this.gTextField.getText());
        float b = Float.parseFloat(this.bTextField.getText());
        float a = Float.parseFloat(this.aTextField.getText());
        return new ColorRGBA(r, g, b, a);
    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);
        ColorRGBA colorRGBA = (ColorRGBA)value;
        this.rTextField.setText("" + colorRGBA.r);
        this.gTextField.setText("" + colorRGBA.g);
        this.bTextField.setText("" + colorRGBA.b);
        this.aTextField.setText("" + colorRGBA.a);
    }

    @Override
    public Panel getPanel() {
        return this.content;
    }

    @Override
    public void update(float tpf) {
        boolean rUpdate = this.rVerRef.update();
        boolean gUpdate = this.gVerRef.update();
        boolean bUpdate = this.bVerRef.update();
        boolean aUpdate = this.aVerRef.update();
        if (rUpdate) {
            this.rTextField.setText(NumberFilters.filterFloatValue(this.rTextField.getText()));
        }

        if (gUpdate) {
            this.gTextField.setText(NumberFilters.filterFloatValue(this.gTextField.getText()));
        }

        if (bUpdate) {
            this.bTextField.setText(NumberFilters.filterFloatValue(this.bTextField.getText()));
        }

        if (aUpdate) {
            this.aTextField.setText(NumberFilters.filterFloatValue(this.aTextField.getText()));
        }

        ColorRGBA oldValue;
        if ((rUpdate || gUpdate || bUpdate || aUpdate) && this.getPropertyChangedEvent() != null) {
            oldValue = this.getColorRGBA();
            this.getPropertyChangedEvent().propertyChanged(oldValue);
        }

        if (this.getReflectedItem() != null) {
            if (this.isFocused(this.rTextField, this.gTextField, this.bTextField, this.aTextField)) {
                return;
            }

            oldValue = this.getColorRGBA();
            ColorRGBA newValue = (ColorRGBA)this.getReflectedItem().getValue();
            if (!oldValue.equals(newValue)) {
                this.setValue(newValue);
            }
        }

    }
}
