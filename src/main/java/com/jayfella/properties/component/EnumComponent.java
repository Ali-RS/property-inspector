package com.jayfella.properties.component;

import com.jayfella.properties.SimpleStringCellRenderer;
import com.simsilica.lemur.ListBox;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.RollupPanel;
import com.simsilica.lemur.core.VersionedReference;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

public class EnumComponent extends JmeComponent {

    private RollupPanel content;
    private ListBox<Enum<?>> enumListBox;
    private VersionedReference<Set<Integer>> ref;

    public EnumComponent() {
        this(null, null, null);
    }

    public EnumComponent(Object parent, Field field) {
        super(parent, field);
        create();
    }

    public EnumComponent(Object parent, Method getter, Method setter) {
        super(parent, getter, setter);
        create();
    }

    private void create() {
        this.enumListBox = new ListBox<>();
        this.enumListBox.setCellRenderer(new SimpleStringCellRenderer<>());
        this.ref = this.enumListBox.getSelectionModel().createReference();
        this.content = new RollupPanel("", this.enumListBox, null);
        this.content.setOpen(false);
    }

    public void setEnumValues(Class<? extends Enum<?>> enumData) {
        Enum<?>[] var2 = enumData.getEnumConstants();
        int index = var2.length;

        int i;
        for(i = 0; i < index; ++i) {
            Enum<?> val = var2[i];
            this.enumListBox.getModel().add(val);
        }

        if (this.getReflectedItem() != null) {
            Enum<?> enumVal = (Enum<?>)this.getReflectedItem().getValue();
            index = -1;

            for(i = 0; i < this.enumListBox.getModel().size(); ++i) {
                if (this.enumListBox.getModel().get(i) == enumVal) {
                    index = i;
                    break;
                }
            }

            if (index > -1) {
                this.enumListBox.getSelectionModel().setSelection(index);
            } else {
                // this should never happen, I guess.. Presumptions and all that.
            }
        }

    }

    public void setPropertyName(String name) {
        super.setPropertyName(name);
        this.content.setTitle("Enum: " + name);
    }

    public void setValue(Object value) {
        super.setValue(value);
        Enum<?> enumValue = (Enum<?>)value;
        int index = -1;

        for(int i = 0; i < this.enumListBox.getModel().size(); ++i) {
            Enum<?> val = this.enumListBox.getModel().get(i);
            if (enumValue == val) {
                index = i;
                break;
            }
        }

        if (index > -1) {
            this.enumListBox.getSelectionModel().setSelection(index);
        }

    }

    public Panel getPanel() {
        return this.content;
    }

    public void update(float tpf) {
        if (this.ref.update()) {
            Set<Integer> indices = this.ref.get();
            if (!indices.isEmpty()) {
                Integer index = indices.iterator().next();
                if (index != null && this.getReflectedItem() != null) {
                    Enum<?> newValue = this.enumListBox.getModel().get(index);
                    this.setValue(newValue);
                }
            }
        }

        if (this.getReflectedItem() != null) {
            if (this.isFocused(this.enumListBox)) {
                return;
            }

            Enum<?> newValue = (Enum<?>)this.getReflectedItem().getValue();
            this.setValue(newValue);
        }

    }
}
