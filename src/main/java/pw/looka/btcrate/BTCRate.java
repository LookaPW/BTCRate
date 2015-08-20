package pw.looka.btcrate;

import javafx.scene.image.Image;
import javafx.util.Duration;
import pw.looka.btcrate.fetcher.BTCExchangeRate;
import pw.looka.btcrate.fetcher.Fetcher;
import pw.looka.btcrate.ui.OptionsApplication;
import pw.looka.btcrate.util.Configuration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Our core class to have everything in.
 *
 * @author Luca
 */
public class BTCRate {

    /**
     * Our singleton instance.
     */
    private static final BTCRate INSTANCE = new BTCRate();

    /**
     * Our fetcher to get all information.
     */
    private Fetcher fetcher = new Fetcher();

    /**
     * Our ui for customizing options and whatnot.
     */
    private OptionsApplication optionsApplication = new OptionsApplication();

    /**
     * Our configuration.
     */
    private Configuration config = new Configuration();

    /**
     * Base file directory to store our files.
     */
    public static final File BASE_DIR = new File(System.getProperty("user.home"));

    private File configFile;

    private BTCExchangeRate previousExchangeRate;

    /**
     * get our singleton instance.
     * @return singleton instance.
     */
    public static final BTCRate getInstance() {
        return INSTANCE;
    }

    public void setup() {
        configFile = new File(BASE_DIR, "btcrate.properties");
        if(!configFile.exists()) {
            try {
                Files.copy(getClass().getResourceAsStream("/resources/defaultconfig.properties"), configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            config.load(new FileInputStream(configFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        optionsApplication.launch(OptionsApplication.class, new String[]{});

    }


    public String[] getCurrencies() {
        return config.getList("currency-list");
    }

    public String getCurrency() {
        return config.getString("currency-used");
    }

    public boolean showNotifications() {
        return config.getBoolean("show-notification");
    }

    public void setShowNotification(boolean showNotification) {
        config.setBoolean("show-notification", showNotification);
    }

    public int getFetchDelay() {
        return config.getInt("fetch-delay");
    }

    public Configuration getConfig() {
        return config;
    }

    public File getConfigFile() {
        return configFile;
    }

    public void setOptionsApplication(OptionsApplication optionsApplication) {
        this.optionsApplication = optionsApplication;
    }

    public void handleFetch() {
        System.out.println("Fetching...");
        BTCExchangeRate exchangeRate = fetcher.getCurrentRate(getCurrency());
        if(previousExchangeRate == null) {
            previousExchangeRate = exchangeRate;
        }
        if(showNotifications()) {
            double percentage = ((100/previousExchangeRate.getBid()) * exchangeRate.getBid())-100;
            percentage = (Math.floor(percentage * 100) / 100);
            TrayNotification trayNotification = new TrayNotification();
            trayNotification.setTitle("BTC Rate in " + getCurrency() + " - " + (percentage > 0 ? "+" : "") + percentage + "% bid");
            trayNotification.setMessage("Ask: " + exchangeRate.getAsk() + " Bid: " +
                    exchangeRate.getBid() + " 24h Avg: " + exchangeRate.getDayAverage());
            trayNotification.setAnimationType(AnimationType.POPUP);
            Image btcImg = new Image(BTCRate.class.getResourceAsStream("/resources/btc_large.png"));
            trayNotification.setImage(btcImg);
            trayNotification.showAndDismiss(Duration.seconds(3));
        }
        System.out.println("Handling other checks");
        if(exchangeRate.getBid() > config.getInt("alert-exceed-bid") && config.getInt("alert-exceed-bid") != 0) {
            System.out.println("Bid Exceeds");
            showNotification("BTC Rate: Bid Exceeds", "Bid exceeds by " +
                    (exchangeRate.getBid() - config.getInt("alert-exceed-bid"))
                    + getCurrency() + " at a bid of " + exchangeRate.getBid() + getCurrency());
        }

        if(exchangeRate.getAsk() > config.getInt("alert-exceed-ask") && config.getInt("alert-exceed-ask") != 0) {
            System.out.println("Ask Exceeds");
            showNotification("BTC Rate: Ask Exceeds", "Ask exceeds by " +
                    (exchangeRate.getAsk()-config.getInt("alert-exceed-ask"))
                    + getCurrency()+ " at an ask of " + exchangeRate.getAsk() + getCurrency());
        }

        if(exchangeRate.getDayAverage() > config.getInt("alert-exceed-daily-average") && config.getInt("alert-exceed-daily-average") != 0) {
            System.out.println("Daily Average Exceeds");
            showNotification("BTC Rate: 24h Avg Exceeds", "24h Avg exceeds by " +
                    (exchangeRate.getDayAverage()-config.getInt("alert-exceed-daily-average"))
                    + getCurrency()+ " at an 24h avg of " + exchangeRate.getDayAverage() + getCurrency());
        }
    }

    private void showNotification(String s, String s1) {
        TrayNotification trayNotification = new TrayNotification();
        trayNotification.setTitle(s);
        trayNotification.setMessage(s1);
        trayNotification.setAnimationType(AnimationType.POPUP);
        Image btcImg = new Image(BTCRate.class.getResourceAsStream("/resources/btc_large.png"));
        trayNotification.setImage(btcImg);
        trayNotification.showAndDismiss(Duration.seconds(5));
    }
}
