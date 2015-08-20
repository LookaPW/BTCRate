package pw.looka.btcrate.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pw.looka.btcrate.BTCRate;
import pw.looka.btcrate.util.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Our controller for the options ui.
 *
 * @author Luca
 */
public class OptionsController {

    @FXML
    private TextField askExceedsTextField;

    @FXML
    private ChoiceBox<String> currencyChoiceBox;

    @FXML
    private CheckBox displayNotificationCheckBox;

    @FXML
    private TextField bidExceedsTextField;

    @FXML
    private TextField dailyAverageDropsBelowTextField;

    @FXML
    private Button applyAlertButton;

    @FXML
    private TextField bidDropsBelowTextField;

    @FXML
    private TextField minuteDelayTextField;

    @FXML
    private TextField dailyAverageExceedsTextField;

    @FXML
    private Button applySettingsButton;

    @FXML
    private TextField askDropsBelowTextField;

    @FXML
    private Button forceFetchButton;

    @FXML
    public void initialize() {
        assert askExceedsTextField != null : "Could not load component into ui";
        assert bidExceedsTextField != null : "Could not load component into ui";
        assert dailyAverageExceedsTextField != null : "Could not load component into ui";
        assert askDropsBelowTextField != null : "Could not load component into ui";
        assert bidDropsBelowTextField != null : "Could not load component into ui";
        assert dailyAverageDropsBelowTextField != null : "Could not load component into ui";
        assert applyAlertButton != null : "Could not load component into ui";
        assert applySettingsButton != null : "Could not load component into ui";
        assert minuteDelayTextField != null : "Could not load component into ui";
        assert forceFetchButton != null : "Could not load component into ui";
        assert currencyChoiceBox != null : "Could not load component into ui";
        assert displayNotificationCheckBox != null : "Could not load component into ui";

        minuteDelayTextField.setText(String.valueOf(BTCRate.getInstance().getFetchDelay()));

        currencyChoiceBox.getItems().addAll(BTCRate.getInstance().getCurrencies());
        currencyChoiceBox.setValue(BTCRate.getInstance().getCurrency());

        displayNotificationCheckBox.setSelected(BTCRate.getInstance().showNotifications());

        askDropsBelowTextField.setText(BTCRate.getInstance().getConfig().getString("alert-below-ask"));
        bidDropsBelowTextField.setText(BTCRate.getInstance().getConfig().getString("alert-below-bid"));
        dailyAverageDropsBelowTextField.setText(BTCRate.getInstance().getConfig().getString("alert-below-daily-average"));

        askExceedsTextField.setText(BTCRate.getInstance().getConfig().getString("alert-exceed-ask"));
        bidExceedsTextField.setText(BTCRate.getInstance().getConfig().getString("alert-exceed-bid"));
        dailyAverageExceedsTextField.setText(BTCRate.getInstance().getConfig().getString("alert-exceed-daily-average"));

        applyAlertButton.setOnAction(event -> saveSettings());
        applySettingsButton.setOnAction(event->saveSettings());
        forceFetchButton.setOnAction(event -> BTCRate.getInstance().handleFetch());

    }

    private void saveSettings() {
        try {
            Configuration config = BTCRate.getInstance().getConfig();
            config.setInt("fetch-delay", Integer.parseInt(minuteDelayTextField.getText()));
            config.setInt("alert-below-ask", Integer.parseInt(askDropsBelowTextField.getText()));
            config.setInt("alert-below-bid", Integer.parseInt(bidDropsBelowTextField.getText()));
            config.setInt("alert-below-daily-average", Integer.parseInt(dailyAverageDropsBelowTextField.getText()));
            config.setInt("alert-exceed-ask", Integer.parseInt(askExceedsTextField.getText()));
            config.setInt("alert-exceed-bid", Integer.parseInt(bidExceedsTextField.getText()));
            config.setInt("alert-exceed-daily-average", Integer.parseInt(dailyAverageExceedsTextField.getText()));
            config.setBoolean("show-notification", displayNotificationCheckBox.isSelected());
            config.setString("currency-used", currencyChoiceBox.getValue());
            try {
                config.store(new FileOutputStream(BTCRate.getInstance().getConfigFile()), "The configuration for the BTCRate application by Looka.");
                BTCRate.getInstance().getConfig().load(new FileInputStream(BTCRate.getInstance().getConfigFile()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Saved configuration.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("We completed the action with success");
            alert.setContentText("We successfully saved the settings!");
            alert.showAndWait();
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("We could not save the options.");
            alert.setContentText("Please check your numbers and check if they are plain numbers such as \"10000\"");
            alert.showAndWait();
        }
    }
}
