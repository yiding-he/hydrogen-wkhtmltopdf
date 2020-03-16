package com.hyd.wkhtmltopdf;

import com.hyd.fx.app.AppPreferences;
import com.hyd.fx.app.AppThread;
import com.hyd.fx.dialog.AlertDialog;
import com.hyd.fx.dialog.FileDialog;
import com.hyd.wkhtmltopdf.WkHtmlToPdf.Status;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private TextField txtExecutablePath;

    @FXML
    private TextArea txtHtmlLocation;

    @FXML
    private TextField txtOutputPath;

    @FXML
    private TextArea txtLog;

    @FXML
    private Button btnStartConvert;

    @FXML
    private Label lblResultText;

    @FXML
    private Hyperlink lnkResultAction;

    public void initialize() {
        final WkHtmlToPdf wkHtmlToPdf = WkHtmlToPdf.getInstance();
        this.txtExecutablePath.textProperty().bindBidirectional(wkHtmlToPdf.executablePathProperty());
        this.txtHtmlLocation.textProperty().bindBidirectional(wkHtmlToPdf.htmlPathProperty());
        this.txtOutputPath.textProperty().bindBidirectional(wkHtmlToPdf.outputPathProperty());

        this.txtExecutablePath.setText(AppPreferences.get().get(PrefKeys.ExecutablePath.name(), ""));

        this.txtHtmlLocation.textProperty().addListener(
            (_ob, _old, value) -> this.txtOutputPath.setText(generateOutputPath(value))
        );

        initWkHtmlToPdf();
    }

    private void initWkHtmlToPdf() {

        // 处理过程中的进程输出内容显示到界面上
        WkHtmlToPdf.getInstance().setLogListener(log -> {
            AppThread.runUIThread(() -> {
                if (txtLog.getLength() == 0) {
                    txtLog.setText(log);
                } else {
                    txtLog.appendText("\n" + log);
                }
                txtLog.setScrollTop(Double.MAX_VALUE);
            });
        });

        // 开始处理时禁用按钮，停止处理时启用
        WkHtmlToPdf.getInstance().setStatusListener(status -> {
            AppThread.runUIThread(() -> {
                this.setText(this.lblResultText, null);
                this.setText(this.lnkResultAction, null);

                if (status == Status.Ready) {
                    this.btnStartConvert.setDisable(false);
                } else if (status == Status.Running) {
                    this.btnStartConvert.setDisable(true);
                } else if (status == Status.Succes) {
                    this.btnStartConvert.setDisable(false);
                    this.setText(this.lblResultText, "文件生成完毕。");
                    this.setText(this.lnkResultAction, "打开文件");
                } else if (status == Status.Fail) {
                    this.btnStartConvert.setDisable(false);
                }
            });
        });
    }

    private void setText(Labeled labeled, String text) {
        if (text == null) {
            labeled.setVisible(false);
        } else {
            labeled.setVisible(true);
            labeled.setText(text);
        }
    }

    private String generateOutputPath(String htmlPath) {
        String outputDir = "";

        try {
            URL url = new URL(htmlPath);
            outputDir = htmlPath.startsWith("file:") ?
                new File(new File(url.getFile()).getAbsolutePath()).getParentFile().getAbsolutePath() :
                new File("").getAbsolutePath();
        } catch (MalformedURLException e) {
            AlertDialog.error("错误", e);
        }

        String outputFilename = htmlPath.substring(htmlPath.lastIndexOf("/") + 1) + ".pdf";
        return outputDir + File.separator + outputFilename;
    }

    public void selectExecutablePath() {
        final File file = FileDialog.showOpenFile(null, "选择 wkhtmltopdf.exe", "*.exe", "wkhtmltopdf.exe");
        if (file != null) {
            final String executablePath = file.getAbsolutePath();
            txtExecutablePath.setText(executablePath);
            AppPreferences.get().put(PrefKeys.ExecutablePath.name(), executablePath);
        }
    }

    public void selectHtmlFile() {
        try {
            final File file = FileDialog.showOpenFile(null, "选择 HTML 文件", "*.html", "HTML 文件");
            if (file != null) {
                txtHtmlLocation.setText(file.toURI().toURL().toExternalForm());
            }
        } catch (MalformedURLException e) {
            AlertDialog.error("错误", e);
        }
    }

    public void startConvert() {
        WkHtmlToPdf.getInstance().start();
    }

    public void execResultLink() {
        WkhtmltopdfMain.hostServices.showDocument(WkHtmlToPdf.getInstance().getOutputPath());
    }
}
