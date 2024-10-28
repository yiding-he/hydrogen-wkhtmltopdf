package com.hyd.wkhtmltopdf;

import com.hyd.wkhtmltopdf.options.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    protected AuthenticationController authenticationController;

    @FXML
    protected PageSizeController pageSizeController;

    @FXML
    protected WebContentController webContentController;

    @FXML
    protected PdfContentController pdfContentController;

    @FXML
    protected HeaderFooterController headerFooterController;
}
