package pw.looka.btcrate;

/**
 * Our code entry to start our program.
 *
 * @author Luca
 */
public class Start {

    /**
     * Our main method.
     * @param arguments program arguments. Aren't used at this time.
     */
    public static void main(String[] arguments) {
        System.out.println("Starting BTCRate by Looka!");
        BTCRate.getInstance().setup();
    }
}
