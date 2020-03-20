package com.hyd.wkhtmltopdf;

import com.hyd.wkhtmltopdf.options.HeaderFooterController;
import com.hyd.wkhtmltopdf.options.PageSizeController;
import com.hyd.wkhtmltopdf.options.PdfContentController;
import com.hyd.wkhtmltopdf.options.WebContentController;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
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
    protected SplitMenuButton btnStartConvert;

    @FXML
    protected Label lblResultText;

    @FXML
    protected Hyperlink lnkResultAction;

    @FXML
    protected PageSizeController pageSizeController;

    @FXML
    protected WebContentController webContentController;

    @FXML
    protected PdfContentController pdfContentController;

    @FXML
    protected HeaderFooterController headerFooterController;
}
