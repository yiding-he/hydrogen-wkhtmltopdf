package com.hyd.wkhtmltopdf;

import com.hyd.fx.Fxml;
import com.hyd.fx.app.AppLogo;
import com.hyd.fx.app.AppPrimaryStage;
import com.hyd.fx.i18n.XMLResourceBundleControl;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class WkhtmltopdfApplication extends Application {

    public static HostServices hostServices;

    public static final Preferences PREFERENCES = Preferences.userNodeForPackage(WkhtmltopdfMain.class);

    public void start(Stage primaryStage) throws Exception {
        AppPrimaryStage.setPrimaryStage(primaryStage);
        AppLogo.setPath("/logo.png");
        AppLogo.setStageLogo(primaryStage);

        hostServices = getHostServices();

        var resourceBundle = ResourceBundle.getBundle("i18n.strings", new XMLResourceBundleControl());
        var fxmlLoader = Fxml.load("/main.fxml", resourceBundle);

        primaryStage.setTitle("WKHTMLTOPDF");
        primaryStage.setScene(new Scene(fxmlLoader.getRoot(), 850, 650));
        primaryStage.show();
    }
}
