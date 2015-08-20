package pw.looka.btcrate.fetcher;

import com.google.gson.annotations.SerializedName;

/**
 * Our POJO from the returned json which is converted to pojo using gson.
 */
public class BTCExchangeRate {

    /**
     * String timestamp of when it was updated.
     */
    private String timestamp;

    /**
     * weighted average of last prices
     */
    private String last;

    /**
     * 24h average price.
     */
    @SerializedName("24h_avg")
    private String dayAverage;

    /**
     * percent of global ? trading volume
     */
    private String volume_percent;

    /**
     * total trading volume across all exchanges in this currency in last 24 hours
     */
    private String volume_btc;

    /**
     * weighted average of ask prices
     */
    private String ask;

    /**
     * weighted average of bid prices
     */
    private String bid;

    public int getTimestamp ()
    {
        return Integer.parseInt(timestamp);
    }

    public void setTimestamp (String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getLast ()
    {
        return last;
    }

    public void setLast (String last)
    {
        this.last = last;
    }

    public double getDayAverage()
    {
        return Double.parseDouble(dayAverage);
    }

    public void setDayAverage (double dayAverage)
    {
        this.dayAverage = String.valueOf(dayAverage);
    }

    public double getVolumePercent ()
    {
        return Double.parseDouble(volume_percent);
    }

    public void setVolumePercent (double volume_percent)
    {
        this.volume_percent = String.valueOf(volume_percent);
    }

    public double getVolumeBTC ()
    {
        return Double.parseDouble(volume_btc);
    }

    public void setVolumeBTC (int volume_btc)
    {
        this.volume_btc = String.valueOf(volume_btc);
    }

    public double getAsk ()
    {
        return Double.parseDouble(ask);
    }

    public void setAsk (double ask)
    {
        this.ask = String.valueOf(ask);
    }

    public double getBid ()
    {
        return Double.parseDouble(bid);
    }

    public void setBid (double bid)
    {
        this.bid = String.valueOf(bid);
    }
}