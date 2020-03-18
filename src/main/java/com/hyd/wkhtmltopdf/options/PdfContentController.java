package com.hyd.wkhtmltopdf.options;

import static com.hyd.wkhtmltopdf.WkHtmlToPdf.setOption;
import static com.hyd.wkhtmltopdf.WkHtmlToPdf.setOptionIfNotEqualTo;

import com.hyd.fx.components.IntegerSpinner;
import com.hyd.wkhtmltopdf.WkHtmlToPdf;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class PdfContentController {

    public IntegerSpinner spnDpi;

    public TextField txtPdfTitle;

    public IntegerSpinner spnImageDpi;

    public IntegerSpinner spnImageQuality;

    public CheckBox chkLowQuality;

    public void initialize() {
        this.spnDpi.setValue(96);
        this.spnImageDpi.setValue(600);
        this.spnImageQuality.setValue(94);
    }

    public void collectOptions(WkHtmlToPdf wkHtmlToPdf) {
        setOptionIfNotEqualTo(wkHtmlToPdf, "dpi", this.spnDpi.getValue(), 96);
        setOptionIfNotEqualTo(wkHtmlToPdf, "image-dpi", this.spnImageDpi.getValue(), 600);
        setOptionIfNotEqualTo(wkHtmlToPdf, "image-quality", this.spnImageQuality.getValue(), 94);

        if (chkLowQuality.isSelected()) {
            setOption(wkHtmlToPdf, "lowquality");
        }
    }
}
