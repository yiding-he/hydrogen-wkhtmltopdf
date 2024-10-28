package com.hyd.wkhtmltopdf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import static com.hyd.wkhtmltopdf.WkhtmltopdfApplication.I18N;

public class FxUtil {

    public static void replaceResourceString(ComboBox<String> comboBox) {
        ObservableList<String> strings = comboBox.getItems();
        var converted = strings.stream().map(s -> {
            if (s.startsWith("%") && I18N.containsKey(s.substring(1))) {
                return I18N.getString(s.substring(1));
            } else {
                return s;
            }
        }).toList();
        comboBox.setItems(FXCollections.observableList(converted));
    }
}
