package com.hyd.wkhtmltopdf;

import javafx.scene.control.Toggle;

public enum PageSizeType {

    predefined, customized;

    public static PageSizeType valueOf(Toggle toggle) {
        return valueOf((String) toggle.getProperties().get("pageSizeType"));
    }
}
