package com.hyd.wkhtmltopdf.options.fragments;

import com.hyd.fx.components.IntegerSpinner;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class HeaderFooterFragmentController {

    public HBox root;

    public TextArea txtContentHtml;

    public TextField txtContentLeft;

    public TextField txtContentCenter;

    public TextField txtContentRight;

    public TextField txtFontName;

    public CheckBox chkSeparator;

    public IntegerSpinner spnSpacing;

    public IntegerSpinner spnFontSize;

    public void initialize() {
        this.spnFontSize.setValue(12);
        this.txtFontName.setText("Arial");
        this.spnSpacing.setValue(0);
    }

    public void replaceType(String type) {
        root.lookupAll("*").stream()
            .filter(node -> node instanceof Labeled)
            .map(node -> (Labeled) node)
            .forEach(
                labeled -> labeled.setText(labeled.getText().replace("[[TYPE]]", type))
            );
    }
}
