package com.hyd.wkhtmltopdf.options;

import static com.hyd.wkhtmltopdf.WkHtmlToPdf.setOption;
import static com.hyd.wkhtmltopdf.WkHtmlToPdf.setOptionIfNotBlank;

import com.hyd.fx.components.DoubleSpinner;
import com.hyd.fx.components.IntegerSpinner;
import com.hyd.wkhtmltopdf.WkHtmlToPdf;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class WebContentController {

    @FXML
    private CheckBox chkEnableBackground;

    @FXML
    private CheckBox chkEnableImages;

    @FXML
    private CheckBox chkEnableExternalLink;

    @FXML
    private CheckBox chkEnableInternalLink;

    @FXML
    private CheckBox chkEnableForms;

    @FXML
    private CheckBox chkEnableJavaScript;

    @FXML
    private CheckBox chkUseXserver;

    @FXML
    private IntegerSpinner spnScriptDelayMillis;

    @FXML
    private IntegerSpinner spnMinFontSize;

    @FXML
    private DoubleSpinner spnZoomPercent;

    @FXML
    private TextField txtWebPageEncoding;

    public void initialize() {
        this.chkEnableBackground.setSelected(true);
        this.chkEnableImages.setSelected(true);
        this.chkEnableExternalLink.setSelected(true);
        this.chkEnableInternalLink.setSelected(true);
        this.chkEnableJavaScript.setSelected(true);
        this.spnScriptDelayMillis.setValue(200);
        this.spnMinFontSize.setValue(8);
        this.spnZoomPercent.setValue(100.0);
    }

    public void collectOptions(WkHtmlToPdf wkHtmlToPdf) {
        if (!this.chkEnableBackground.isSelected()) {
            setOption(wkHtmlToPdf, "no-background");
        }
        if (!this.chkEnableImages.isSelected()) {
            setOption(wkHtmlToPdf, "no-images");
        }
        if (!this.chkEnableExternalLink.isSelected()) {
            setOption(wkHtmlToPdf, "disable-external-links");
        }
        if (!this.chkEnableInternalLink.isSelected()) {
            setOption(wkHtmlToPdf, "disable-internal-links");
        }
        if (this.chkEnableForms.isSelected()) {
            setOption(wkHtmlToPdf, "enable-forms");
        }
        if (this.chkUseXserver.isSelected()) {
            setOption(wkHtmlToPdf, "use-xserver");
        }
        if (!this.chkEnableJavaScript.isSelected()) {
            setOption(wkHtmlToPdf, "disable-javascript");
        }
        if (this.spnScriptDelayMillis.getValue() != 200) {
            setOption(wkHtmlToPdf, "javascript-delay", this.spnScriptDelayMillis.getValue());
        }
        if (this.spnZoomPercent.getValue() != 100.0) {
            setOption(wkHtmlToPdf, "zoom", this.spnZoomPercent.getValue() / 100.0);
        }
        setOptionIfNotBlank(wkHtmlToPdf, "encoding", txtWebPageEncoding.getText());
        setOption(wkHtmlToPdf, "minimum-font-size", spnMinFontSize.getValue());
    }
}
