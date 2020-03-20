package com.hyd.wkhtmltopdf;

import javafx.fxml.FXMLLoader;

public class FXMLLoaderTest {

    public static void main(String[] args) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        System.out.println(fxmlLoader.impl_getLoadListener());
    }
}
