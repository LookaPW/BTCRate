package pw.looka.btcrate.ui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import pw.looka.btcrate.BTCRate;
import pw.looka.btcrate.fetcher.RateClock;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Our JFX application for the options where people can customize stuff.
 *
 * @author Luca
 */
public class OptionsApplication extends Application {

    private Stage currentStage;


    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p/>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        BTCRate.getInstance().setOptionsApplication(this);
        this.currentStage = primaryStage;
        TabPane mainTab = (TabPane) FXMLLoader.load(getClass().getResource("/resources/options_ui.fxml"));
        Scene scene = new Scene(mainTab);
        primaryStage.setTitle("BTCRate by Looka");
        primaryStage.getIcons().add(new Image(OptionsApplication.class.getResourceAsStream("/resources/btc_small.png")));
        primaryStage.setScene(scene);
        primaryStage.show();
        RateClock rateClock = new RateClock();
        rateClock.setDaemon(true);
        rateClock.start();
    }


    public Stage getCurrentStage() {
        return currentStage;
    }
}
