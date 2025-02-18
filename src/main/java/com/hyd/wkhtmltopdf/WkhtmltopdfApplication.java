package com.hyd.wkhtmltopdf;

import com.hyd.fx.Fxml;
import com.hyd.fx.app.AppLogo;
import com.hyd.fx.app.AppPrimaryStage;
import com.hyd.fx.i18n.XMLResourceBundleControl;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class WkhtmltopdfApplication extends Application {

    public static HostServices hostServices;

    public static final Preferences PREFERENCES = Preferences.userNodeForPackage(WkhtmltopdfMain.class);

    public static final ResourceBundle I18N = ResourceBundle.getBundle("i18n.strings", new XMLResourceBundleControl());

    public void start(Stage primaryStage) throws Exception {
        AppPrimaryStage.setPrimaryStage(primaryStage);
        AppLogo.setPath("/logo.png");
        AppLogo.setStageLogo(primaryStage);

        hostServices = getHostServices();

        primaryStage.setTitle("WKHTMLTOPDF");
        primaryStage.setScene(new Scene(Fxml.load("/main.fxml", I18N).getRoot(), 850, 650));
        primaryStage.show();
    }
}
