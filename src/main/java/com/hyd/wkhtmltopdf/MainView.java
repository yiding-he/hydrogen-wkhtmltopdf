package com.hyd.wkhtmltopdf;

import com.hyd.wkhtmltopdf.options.PageSizeController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    protected PageSizeController pageSizeController;
}
