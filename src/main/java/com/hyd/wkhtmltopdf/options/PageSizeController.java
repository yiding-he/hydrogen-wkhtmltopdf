package com.hyd.wkhtmltopdf.options;

import static com.hyd.wkhtmltopdf.WkHtmlToPdf.setOptionIfNotBlank;

import com.hyd.wkhtmltopdf.PageSizeType;
import com.hyd.wkhtmltopdf.WkHtmlToPdf;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class PageSizeController {

    private static final Map<String, int[]> PAPER_SIZE_MAP = new HashMap<>();

    static {
        PAPER_SIZE_MAP.put("A2", new int[]{420, 594});
        PAPER_SIZE_MAP.put("A3", new int[]{297, 420});
        PAPER_SIZE_MAP.put("A4", new int[]{210, 297});
        PAPER_SIZE_MAP.put("A5", new int[]{148, 210});
        PAPER_SIZE_MAP.put("A6", new int[]{105, 148});
        PAPER_SIZE_MAP.put("B3", new int[]{353, 500});
        PAPER_SIZE_MAP.put("B4", new int[]{250, 353});
        PAPER_SIZE_MAP.put("B5", new int[]{176, 250});
        PAPER_SIZE_MAP.put("B6", new int[]{125, 176});
    }

    @FXML
    protected ToggleGroup tgPageSizeType;

    @FXML
    protected RadioButton tgPageSizeTypePredefined;

    @FXML
    protected ComboBox<String> cmbPageSize;

    @FXML
    protected TextField txtCustomPaperWidth;

    @FXML
    protected TextField txtCustomPaperHeight;

    @FXML
    protected TextField txtCustomMarginTop;

    @FXML
    protected TextField txtCustomMarginBottom;

    @FXML
    protected TextField txtCustomMarginLeft;

    @FXML
    protected TextField txtCustomMarginRight;

    @FXML
    protected HBox hbPageSizeTypePredefined;

    @FXML
    protected HBox hbPageSizeTypeCustomized;

    public void initialize() {

        this.tgPageSizeType.selectedToggleProperty().addListener(
            (_ob, _old, value) -> this.onPageSizeTypeChanged(value)
        );
        this.cmbPageSize.valueProperty().addListener(
            (_ob, _old, value) -> this.onPageSizeSelectionChanged(value)
        );

        this.tgPageSizeType.selectToggle(this.tgPageSizeTypePredefined);
        this.cmbPageSize.setValue("A4");
    }

    private void onPageSizeSelectionChanged(String size) {
        int[] sizeArr = PAPER_SIZE_MAP.get(size);
        setCustomPageSize(sizeArr[0], sizeArr[1]);
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

    public void collectOptions(WkHtmlToPdf wkHtmlToPdf) {
        final PageSizeType pageSizeType = PageSizeType.valueOf(tgPageSizeType.getSelectedToggle());

        if (pageSizeType == PageSizeType.predefined) {
            setOptionIfNotBlank(wkHtmlToPdf, "page-size", cmbPageSize.getValue());
        }

        if (pageSizeType == PageSizeType.customized) {
            setOptionIfNotBlank(wkHtmlToPdf, "page-width", txtCustomPaperWidth.getText());
            setOptionIfNotBlank(wkHtmlToPdf, "page-height", txtCustomPaperHeight.getText());
            setOptionIfNotBlank(wkHtmlToPdf, "margin-top", txtCustomMarginTop.getText());
            setOptionIfNotBlank(wkHtmlToPdf, "margin-bottom", txtCustomMarginBottom.getText());
            setOptionIfNotBlank(wkHtmlToPdf, "margin-left", txtCustomMarginLeft.getText());
            setOptionIfNotBlank(wkHtmlToPdf, "margin-right", txtCustomMarginRight.getText());
        }
    }
}
