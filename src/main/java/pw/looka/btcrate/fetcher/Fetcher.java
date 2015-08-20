package pw.looka.btcrate.fetcher;

import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class used to fetch bitcoin values and whatnot.
 *
 * @author Luca
 */
public class Fetcher {

    /**
     * Our Gson instance.
     */
    private Gson gson = new Gson();

    public Fetcher() {

    }

    /**
     * This method will return an object with the latest bitcoin rate information.
     * @param currency the currency in ISO format UPPER CASE.
     * @return the json-deserialized object.
     */
    public BTCExchangeRate getCurrentRate(final String currency) {
        try {
            URL obj = new URL("https://api.bitcoinaverage.com/ticker/global/"+currency.toUpperCase());
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            BTCExchangeRate value = gson.fromJson(new InputStreamReader(con.getInputStream()), BTCExchangeRate.class);
            return value;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
