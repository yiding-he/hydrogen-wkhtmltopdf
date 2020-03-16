package com.hyd.wkhtmltopdf;

import com.hyd.fx.app.AppPreferences;
import com.hyd.fx.app.AppThread;
import com.hyd.fx.dialog.AlertDialog;
import com.hyd.fx.dialog.FileDialog;
import com.hyd.wkhtmltopdf.WkHtmlToPdf.Status;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.scene.control.Labeled;
import javafx.scene.control.Toggle;

public class MainController extends MainView {

    public void initialize() {
        final WkHtmlToPdf wkHtmlToPdf = WkHtmlToPdf.getInstance();
        this.txtExecutablePath.textProperty().bindBidirectional(wkHtmlToPdf.executablePathProperty());
        this.txtHtmlLocation.textProperty().bindBidirectional(wkHtmlToPdf.htmlPathProperty());
        this.txtOutputPath.textProperty().bindBidirectional(wkHtmlToPdf.outputPathProperty());

        this.txtExecutablePath.setText(AppPreferences.get().get(PrefKeys.ExecutablePath.name(), ""));

        this.tgPageSizeType.selectedToggleProperty().addListener(
            (_ob, _old, value) -> this.onPageSizeTypeChanged(value)
        );
        this.txtHtmlLocation.textProperty().addListener(
            (_ob, _old, value) -> this.txtOutputPath.setText(generateOutputPath(value))
        );
        this.cmbPageSize.valueProperty().addListener(
            (_ob, _old, value) -> this.onPageSizeSelectionChanged(value)
        );

        initWkHtmlToPdf();
    }

    private void onPageSizeSelectionChanged(String size) {
        switch (size) {
            case "A2":
                setCustomPageSize(420, 594);
                break;
            case "A3":
                setCustomPageSize(297, 420);
                break;
            case "A4":
                setCustomPageSize(210, 297);
                break;
            case "A5":
                setCustomPageSize(148, 210);
                break;
            case "A6":
                setCustomPageSize(105, 148);
                break;
            case "B3":
                setCustomPageSize(353, 500);
                break;
            case "B4":
                setCustomPageSize(250, 353);
                break;
            case "B5":
                setCustomPageSize(176, 250);
                break;
            case "B6":
                setCustomPageSize(125, 176);
                break;
        }
    }

    private void setCustomPageSize(int width, int height) {
        txtCustomPaperWidth.setText(width + "mm");
        txtCustomPaperHeight.setText(height + "mm");
    }

    private void onPageSizeTypeChanged(Toggle toggle) {
        PageSizeType pageSizeType = PageSizeType.valueOf(toggle);
        hbPageSizeTypeCustomized.setDisable(pageSizeType == PageSizeType.predefined);
        hbPageSizeTypePredefined.setDisable(pageSizeType == PageSizeType.customized);
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
                txtLog.appendText("");
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
                    this.setText(this.lnkResultAction, "打开");
                } else if (status == Status.Fail) {
                    this.btnStartConvert.setDisable(false);
                }
            });
        });
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

    // 选择 wkhtmltopdf 可执行文件路径
    public void selectExecutablePath() {
        final File file = FileDialog.showOpenFile(null, "选择 wkhtmltopdf.exe", "*.exe", "wkhtmltopdf.exe");
        if (file != null) {
            final String executablePath = file.getAbsolutePath();
            txtExecutablePath.setText(executablePath);
            AppPreferences.get().put(PrefKeys.ExecutablePath.name(), executablePath);
        }
    }

    // 选择源文件本地路径
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
        collectOptions();
        WkHtmlToPdf.getInstance().start();
    }

    public void execResultLink() {
        WkhtmltopdfMain.hostServices.showDocument(WkHtmlToPdf.getInstance().getOutputPath());
    }

    //////////////////////////////////////////////////////////////

    private void collectOptions() {
        final WkHtmlToPdf wkHtmlToPdf = WkHtmlToPdf.getInstance();
        collectPageSizeOptions(wkHtmlToPdf);
    }

    private void collectPageSizeOptions(WkHtmlToPdf wkHtmlToPdf) {
        final PageSizeType pageSizeType = PageSizeType.valueOf(tgPageSizeType.getSelectedToggle());

        if (pageSizeType == PageSizeType.predefined) {
            wkHtmlToPdf.setOption("page-size", cmbPageSize.getValue());
        }
    }
}
