package com.hyd.wkhtmltopdf.options.fragments;

import static com.hyd.wkhtmltopdf.WkHtmlToPdf.*;

import com.hyd.fx.components.IntegerSpinner;
import com.hyd.fx.dialog.AlertDialog;
import com.hyd.fx.utils.Str;
import com.hyd.wkhtmltopdf.WkHtmlToPdf;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class HeaderFooterFragmentController {

    public TabPane tpContent;

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

    public void collectOptions(WkHtmlToPdf wkHtmlToPdf, String prefix) {

        if (tpContent.getSelectionModel().getSelectedIndex() == 0) {
            setOptionIfNotBlank(wkHtmlToPdf, prefix + "-left", txtContentLeft.getText());
            setOptionIfNotBlank(wkHtmlToPdf, prefix + "-center", txtContentCenter.getText());
            setOptionIfNotBlank(wkHtmlToPdf, prefix + "-right", txtContentRight.getText());
        }

        if (tpContent.getSelectionModel().getSelectedIndex() == 1 && Str.isNotBlank(txtContentHtml.getText())) {
            try {
                final Path tempFile = Files.createTempFile("hydrogen-wkhtmltopdf", ".html");
                Files.deleteIfExists(tempFile);
                Files.write(tempFile, txtContentHtml.getText().getBytes());

                final String url = tempFile.toUri().toURL().toExternalForm().replace("file:/", "file:///");
                setOptionIfNotBlank(wkHtmlToPdf, prefix + "-html", url);
            } catch (IOException e) {
                AlertDialog.error("保存 HTML 到临时文件失败", e);
            }
        }

        setOptionIf(wkHtmlToPdf, prefix + "-line", chkSeparator.isSelected());
        setOptionIfNotEqualTo(wkHtmlToPdf, prefix + "-spacing", spnSpacing.getValue(), 0);
        setOptionIfNotEqualTo(wkHtmlToPdf, prefix + "-font-size", spnFontSize.getValue(), 12);
        setOptionIfNotEqualTo(wkHtmlToPdf, prefix + "-font-name", txtFontName.getText(), "Arial");
    }
}
