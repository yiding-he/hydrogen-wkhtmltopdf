package com.hyd.wkhtmltopdf;

import com.hyd.fx.dialog.AlertDialog;
import com.hyd.fx.utils.Str;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WkHtmlToPdf {

    private static final WkHtmlToPdf instance = new WkHtmlToPdf();

    public static WkHtmlToPdf getInstance() {
        return instance;
    }

    public enum Status {
        Ready, Running, Succes, Fail
    }

    //////////////////////////////////////////////////////////////

    private StringProperty executablePath = new SimpleStringProperty();

    private StringProperty htmlPath = new SimpleStringProperty();

    private StringProperty outputPath = new SimpleStringProperty();

    private Consumer<String> logListener = log -> {
    };

    private Consumer<Status> statusListener = status -> {
    };

    public Consumer<Status> getStatusListener() {
        return statusListener;
    }

    public void setStatusListener(Consumer<Status> statusListener) {
        this.statusListener = statusListener;
    }

    public String getOutputPath() {
        return outputPath.get();
    }

    public StringProperty outputPathProperty() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath.set(outputPath);
    }

    public void setLogListener(Consumer<String> logListener) {
        this.logListener = logListener;
    }

    public String getHtmlPath() {
        return htmlPath.get();
    }

    public StringProperty htmlPathProperty() {
        return htmlPath;
    }

    public void setHtmlPath(String htmlPath) {
        this.htmlPath.set(htmlPath);
    }

    public String getExecutablePath() {
        return executablePath.get();
    }

    public StringProperty executablePathProperty() {
        return executablePath;
    }

    public void setExecutablePath(String executablePath) {
        this.executablePath.set(executablePath);
    }

    public void start() {
        if (Str.isBlank(this.getExecutablePath())) {
            AlertDialog.error("请选择正确的 wkhtmltopdf 可执行文件路径。");
            return;
        }
        if (Str.isBlank(this.getHtmlPath())) {
            AlertDialog.error("请输入正确的网页地址或选择正确的 HTML 文件。");
            return;
        }

        statusListener.accept(Status.Running);
        new Thread(this::startProcess).start();
    }

    private void startProcess() {
        try {
            List<String> command = new ArrayList<>();
            command.add(this.getExecutablePath());

            this.options.forEach((key, value) -> {
                command.add("--" + key);
                if (Str.isNotBlank(value)) {
                    command.add(value);
                }
            });

            command.add(this.getHtmlPath());
            command.add(this.getOutputPath());
            logListener.accept(String.join(" ", command));

            final ProcessBuilder processBuilder = new ProcessBuilder(command);
            final Process process = processBuilder.start();

            readProcessOutput(process);

            final int result = process.waitFor();
            logListener.accept("Process terminated with code " + result + ".");
            statusListener.accept(result == 0 ? Status.Succes : Status.Fail);

        } catch (Exception e) {
            e.printStackTrace();
            logListener.accept(e.toString());
            statusListener.accept(Status.Fail);
        }
    }

    private void readProcessOutput(Process process) {
        Scanner outputScanner = new Scanner(process.getInputStream());
        while (outputScanner.hasNextLine()) {
            logListener.accept(outputScanner.nextLine());
        }

        Scanner errScanner = new Scanner(process.getErrorStream());
        while (errScanner.hasNextLine()) {
            logListener.accept(errScanner.nextLine());
        }
    }

    //////////////////////////////////////////////////////////////

    private Map<String, String> options = new HashMap<>();

    public void setOption(String key, String value) {
        this.options.put(key, value);
    }

    public void setOption(String key) {
        this.setOption(key, "");
    }

    public static void setOptionIfNotBlank(WkHtmlToPdf wkHtmlToPdf, String key, String value) {
        if (Str.isNotBlank(value)) {
            wkHtmlToPdf.setOption(key, value);
        }
    }

    public static void setOption(WkHtmlToPdf wkHtmlToPdf, String key) {
        wkHtmlToPdf.setOption(key);
    }

    public static void setOption(WkHtmlToPdf wkHtmlToPdf, String key, int value) {
        wkHtmlToPdf.setOption(key, String.valueOf(value));
    }
}
