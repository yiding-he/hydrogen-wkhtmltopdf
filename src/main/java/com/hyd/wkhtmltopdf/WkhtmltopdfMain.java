package com.hyd.wkhtmltopdf;

import com.hyd.fx.Fxml;
import com.hyd.fx.app.AppLogo;
import com.hyd.fx.app.AppPrimaryStage;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WkhtmltopdfMain extends Application {

    public static HostServices hostServices;

    public static final Preferences PREFERENCES = Preferences.userNodeForPackage(WkhtmltopdfMain.class);

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        AppPrimaryStage.setPrimaryStage(primaryStage);
        AppLogo.setPath("/logo.png");
        AppLogo.setStageLogo(primaryStage);

        hostServices = getHostServices();

        primaryStage.setTitle("WKHTMLTOPDF");
        primaryStage.setScene(new Scene(Fxml.load("/main.fxml").getRoot(), 800, 500));
        primaryStage.show();
    }
}
