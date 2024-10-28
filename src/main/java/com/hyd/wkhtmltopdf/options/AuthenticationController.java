package com.hyd.wkhtmltopdf.options;

import com.hyd.wkhtmltopdf.WkHtmlToPdf;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AuthenticationController {

    @FXML
    protected TextField txtAuthUserName;

    @FXML
    protected TextField txtAuthPassword;

    public void collectOptions(WkHtmlToPdf wkHtmlToPdf) {
        String username = txtAuthUserName.getText();
        String password = txtAuthPassword.getText();
        if (username != null && !username.isEmpty()) {
            wkHtmlToPdf.setOption("username", username);
        }
        if (password != null && !password.isEmpty()) {
            wkHtmlToPdf.setOption("password", password);
        }
    }
}
