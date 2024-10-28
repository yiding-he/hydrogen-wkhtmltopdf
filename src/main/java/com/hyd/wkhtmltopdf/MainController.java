package com.hyd.wkhtmltopdf;

import com.hyd.fx.dialog.AlertDialog;
import com.hyd.fx.system.ClipboardHelper;
import com.hyd.fx.utils.Str;
import com.hyd.wkhtmltopdf.WkHtmlToPdf.Status;
import javafx.scene.control.Labeled;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static com.hyd.fx.app.AppThread.runUIThread;
import static com.hyd.fx.dialog.FileDialog.showOpenFile;
import static com.hyd.wkhtmltopdf.WkhtmltopdfApplication.I18N;
import static com.hyd.wkhtmltopdf.WkhtmltopdfApplication.PREFERENCES;

public class MainController extends MainView {

    public void initialize() {
        final var wkHtmlToPdf = WkHtmlToPdf.getInstance();

        this.txtExecutablePath.textProperty().bindBidirectional(wkHtmlToPdf.executablePathProperty());
        this.txtHtmlLocation.textProperty().bindBidirectional(wkHtmlToPdf.htmlPathProperty());
        this.txtOutputPath.textProperty().bindBidirectional(wkHtmlToPdf.outputPathProperty());

        this.txtExecutablePath.setText(PREFERENCES.get(PrefKeys.ExecutablePath.name(), ""));

        this.txtHtmlLocation.textProperty().addListener(
            (_ob, _old, value) -> this.txtOutputPath.setText(generateOutputPath(value))
        );

        //////////////////////////////////////////////////////////////

        // 响应 WkHtmlToPdf 事件
        initWkHtmlToPdf(wkHtmlToPdf);

        // 从参数 -Durl=xxx 中读取预定义地址，调试用
        this.txtHtmlLocation.setText(System.getProperty("url", ""));
    }

    private void initWkHtmlToPdf(WkHtmlToPdf wkHtmlToPdf) {

        // 处理过程中的进程输出内容显示到界面上
        wkHtmlToPdf.setLogListener(log -> runUIThread(() -> {
            if (txtLog.getLength() == 0) {
                txtLog.setText(log);
            } else {
                txtLog.appendText("\n" + log);
            }
            txtLog.appendText("");
        }));

        // 开始处理时禁用按钮，停止处理时启用
        wkHtmlToPdf.setStatusListener(status -> runUIThread(() -> {
            this.setText(this.lblResultText, null);
            this.setText(this.lnkResultAction, null);

            if (status == Status.Ready) {
                this.btnStartConvert.setDisable(false);
            } else if (status == Status.Running) {
                this.btnStartConvert.setDisable(true);
                this.txtLog.setText("");
            } else if (status == Status.Succes) {
                this.btnStartConvert.setDisable(false);
                this.setText(this.lblResultText, I18N.getString("msg_convert_success"));
                this.setText(this.lnkResultAction, I18N.getString("msg_convert_success_open"));
            } else if (status == Status.Fail) {
                this.btnStartConvert.setDisable(false);
            }
        }));
    }

    // 为 label 或 Hyperlink 设置文本内容。如果内容为空则隐藏控件
    private void setText(Labeled labeled, String text) {
        if (text == null) {
            labeled.setVisible(false);
        } else {
            labeled.setVisible(true);
            labeled.setText(text);
        }
    }

    // 根据输入的 html 路径生成输出 pdf 文件路径
    // 如果输入的是本地文件则默认置输出目录为源文件所在目录；
    // 如果输入的是远程地址则默认置输出目录为当前目录。
    private String generateOutputPath(String htmlPath) {
        if (Str.isBlank(htmlPath)) {
            return "";
        }

        var outputDir = "";

        try {
            var url = new URL(htmlPath);
            outputDir = htmlPath.startsWith("file:") ?
                new File(new File(url.getFile()).getAbsolutePath()).getParentFile().getAbsolutePath() :
                new File("").getAbsolutePath();
        } catch (MalformedURLException e) {

        }

        var fileBaseName = htmlPath.substring(htmlPath.lastIndexOf("/") + 1);
        if (fileBaseName.isEmpty()) {
            fileBaseName = "output";
        }

        var outputFilename = fileBaseName + ".pdf";
        return outputDir + File.separator + outputFilename;
    }

    // 选择 wkhtmltopdf 可执行文件路径
    public void selectExecutablePath() {
        final var file = showOpenFile(null, "选择 wkhtmltopdf.exe", "*.exe", "wkhtmltopdf.exe");
        if (file != null) {
            final var executablePath = file.getAbsolutePath();
            txtExecutablePath.setText(executablePath);
            PREFERENCES.put(PrefKeys.ExecutablePath.name(), executablePath);
        }
    }

    // 选择源文件本地路径
    public void selectHtmlFile() {
        try {
            final var file = showOpenFile(null, "选择 HTML 文件", "*.html", "HTML 文件");
            if (file != null) {
                txtHtmlLocation.setText(file.toURI().toURL().toExternalForm());
            }
        } catch (MalformedURLException e) {
            AlertDialog.error("错误", e);
        }
    }

    public void startConvert() {
        collectOptions();
        WkHtmlToPdf.getInstance().start();
    }

    public void copyCommand() {
        collectOptions();
        var commandText = String.join(" ", WkHtmlToPdf.getInstance().buildCommand());
        ClipboardHelper.putString(commandText);
        AlertDialog.info("复制命令", "命令已复制到剪切板。");
    }

    public void execResultLink() {
        WkhtmltopdfApplication.hostServices.showDocument(WkHtmlToPdf.getInstance().getOutputPath());
    }

    //////////////////////////////////////////////////////////////

    private void collectOptions() {
        final var wkHtmlToPdf = WkHtmlToPdf.getInstance();
        authenticationController.collectOptions(wkHtmlToPdf);
        pageSizeController.collectOptions(wkHtmlToPdf);
        webContentController.collectOptions(wkHtmlToPdf);
        pdfContentController.collectOptions(wkHtmlToPdf);
        headerFooterController.collectOptions(wkHtmlToPdf);
    }
}
