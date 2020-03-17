package com.hyd.wkhtmltopdf.options;

import com.hyd.fx.components.IntegerSpinner;
import com.hyd.wkhtmltopdf.WkHtmlToPdf;

public class PdfContentController {

    public IntegerSpinner spnDpi;

    public void initialize() {
        this.spnDpi.setValue(96);
    }

    public void collectOptions(WkHtmlToPdf wkHtmlToPdf) {
        if (this.spnDpi.getValue() != 96) {
            WkHtmlToPdf.setOption(wkHtmlToPdf, "dpi", this.spnDpi.getValue());
        }
    }
}
