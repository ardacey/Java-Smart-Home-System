import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Time {
    private static LocalDateTime currentTime;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");



    /**
     * setting the time for the first time.
     * @param timestamp time value.
     */

    public static void setInitialTime(String timestamp) {
        LocalDateTime datetime = LocalDateTime.parse(timestamp, formatter);
        currentTime = datetime;
        Io.writeToFile("SUCCESS: Time has been set to " + datetime.format(formatter)+"!", true);
    }



    /**
     * sets the time to specified date.
     * @param inputTime date to set.
     */

    public static void setTime(String inputTime) {
        try {

            LocalDateTime newTime = LocalDateTime.parse(inputTime, formatter);
            if (newTime.isBefore(currentTime)) Io.writeToFile("ERROR: Time cannot be reversed!", true);
            else if(newTime.equals(currentTime)) Io.writeToFile("ERROR: There is nothing to change!", true);
            else {
                currentTime = newTime;
                SwitchEvents.setEvents();
                SwitchEvents.EventExecuter();
            }

        } catch (DateTimeParseException e) {
            Io.writeToFile("ERROR: Time format is not correct!", true);
        }
    }



    /**
     * skips the time as much as the entered time.
     * @param minutes minutes to skip.
     */

    public static void skipMinutes(int minutes) {

        if (minutes < 0) Io.writeToFile("ERROR: Time cannot be reversed!", true);

        else if (minutes == 0) Io.writeToFile("ERROR: There is nothing to skip!", true);

        else {
            currentTime = currentTime.plusMinutes(minutes);
            SwitchEvents.setEvents();
            SwitchEvents.EventExecuter();
        }
    }



    /**
     * sets the switch time of a device.
     * @param deviceName name of the device.
     * @param time switch time.
     */

    public static void setSwitchTime(String deviceName, String time) {
        LocalDateTime switchTime = LocalDateTime.parse(time, formatter);
        SmartHomeDevice device = SmartHomeDevice.findDevice(deviceName);

        if (switchTime.isBefore(currentTime)) {
            Io.writeToFile("ERROR: Switch time cannot be in the past!", true);
            return;
        }

        device.setSwitchTime(switchTime);
        SwitchEvents.setEvents();
        SwitchEvents.EventExecuter();
    }


    /**
     *
     * @return current time.
     */

    public static LocalDateTime getCurrentTime() {
        return currentTime;
    }


    /**
     *
     * @return time format.
     */

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }


    /**
     *
     * @param time1 current time(probably).
     * @param time2 device switch time(probably).
     * @return time is after or not.
     */

    public static boolean isAfter(LocalDateTime time1, LocalDateTime time2) {
        return time1.isAfter(time2) || time1.equals(time2);
    }
}
