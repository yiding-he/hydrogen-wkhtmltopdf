package com.hyd.wkhtmltopdf;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public abstract class MainView {

    @FXML
    protected TextField txtExecutablePath;

    @FXML
    protected TextArea txtHtmlLocation;

    @FXML
    protected TextField txtOutputPath;

    @FXML
    protected TextArea txtLog;

    @FXML
    protected Button btnStartConvert;

    @FXML
    protected Label lblResultText;

    @FXML
    protected Hyperlink lnkResultAction;

    @FXML
    protected ToggleGroup tgPageSizeType;

    @FXML
    protected ComboBox<String> cmbPageSize;

    @FXML
    protected TextField txtCustomPaperWidth;

    @FXML
    protected TextField txtCustomPaperHeight;

    @FXML
    protected HBox hbPageSizeTypePredefined;

    @FXML
    protected HBox hbPageSizeTypeCustomized;

}
