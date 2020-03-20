package com.hyd.wkhtmltopdf.options;

import com.hyd.wkhtmltopdf.WkHtmlToPdf;
import com.hyd.wkhtmltopdf.options.fragments.HeaderFooterFragmentController;

public class HeaderFooterController {

    public HeaderFooterFragmentController headerController;

    public HeaderFooterFragmentController footerController;

    public void initialize() {
        headerController.replaceType("页头");
        footerController.replaceType("页脚");
    }

    public void collectOptions(WkHtmlToPdf wkHtmlToPdf) {

    }
}
