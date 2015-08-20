package tray.notification;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luca
 */
public class TrayManager {

    private static List<TrayNotification> notificationList = new ArrayList<>();

    private TrayManager() {
        throw new IllegalAccessError();
    }

    public static void addNotification(final TrayNotification notification) {
        System.out.println("Adding notification");
        notificationList.add(notification);
    }

    public static void removeNotification(final TrayNotification trayNotification) {
        System.out.println("Removing notification..");
        notificationList.remove(trayNotification);
    }

    public static int getSize() {
        System.out.println("Size: " + notificationList.size());
        return notificationList.size();
    }
}
