package pw.looka.btcrate.fetcher;

import javafx.application.Platform;
import pw.looka.btcrate.BTCRate;

import java.util.concurrent.TimeUnit;

/**
 * @author Luca
 */
public class RateClock extends Thread {

    public void run() {
        setName("Rate clock");
        while(true) {
            Platform.runLater(()->BTCRate.getInstance().handleFetch());
            try {
                sleep(TimeUnit.MINUTES.toMillis(BTCRate.getInstance().getFetchDelay()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
