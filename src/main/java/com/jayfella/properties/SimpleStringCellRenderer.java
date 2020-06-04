package com.jayfella.properties;

import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.VAlignment;
import com.simsilica.lemur.list.CellRenderer;

public class SimpleStringCellRenderer<T> implements CellRenderer<T> {
    public SimpleStringCellRenderer() {
    }

    public Panel getView(T value, boolean selected, Panel existing) {

        SimpleStringCellRenderer.SimpleStringContainer container;

        if (existing != null) {
            container = (SimpleStringCellRenderer.SimpleStringContainer)existing;
        } else {
            container = new SimpleStringCellRenderer.SimpleStringContainer();
        }

        container.setText(value.toString());
        return container;
    }

    private static class SimpleStringContainer extends Container {
        private final Label label = (Label)this.addChild(new Label(""), new Object[0]);

        public SimpleStringContainer() {
            this.label.setTextVAlignment(VAlignment.Center);
        }

        public void setText(String text) {
            this.label.setText(text);
        }
    }
}
