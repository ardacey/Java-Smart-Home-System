import java.time.Duration;
import java.time.LocalDateTime;

public class SmartPlug extends SmartHomeDevice {
    private float ampere;
    private float totalConsumption;
    private boolean plugged;
    private LocalDateTime onTime, offTime;



    /**
     * Smart Plug constructor.
     * @param name name of the device.
     * @param status status of the device.
     * @param ampere ampere value of the device.
     * @param totalConsumption total consumption value before off and on time of the device.
     * @param plugged boolean variable, if plug is plugged something, true; if not, false.
     */

    public SmartPlug(String name, String status, float ampere, long totalConsumption, boolean plugged){
        super(name, status);
        if (status.equals("On")) setOnTime(Time.getCurrentTime());
        this.ampere = ampere;
        this.totalConsumption = totalConsumption;
        this.plugged = plugged;
    }


    /**
     *
     * @param ampere set ampere value of the plug.
     */

    public void setAmpere(int ampere) {
        this.ampere = ampere;
    }


    /**
     *
     * @param onTime set on time of the plug.
     */

    public void setOnTime(LocalDateTime onTime) {
        this.onTime = onTime;
    }


    /**
     *
     * @param offTime set off time of the plug.
     */

    public void setOffTime(LocalDateTime offTime) {
        this.offTime = offTime;
    }


    /**
     *
     * @param plugged set whether the device is plugged or not.
     */

    public void setPlugged(boolean plugged) {
        this.plugged = plugged;
    }


    /**
     *
     * @param totalConsumption adds the energy consumed by the device.
     */

    public void setTotalConsumption(float totalConsumption) {
        this.totalConsumption += totalConsumption;
    }


    /**
     *
     * @return whether the device is plugged or not.
     */

    public boolean isPlugged() {
        return plugged;
    }

    /**
     *
     * @return total consumption of the plug.
     */

    public float getTotalConsumption() {
        return totalConsumption;
    }


    /**
     *
     * @return date the device was turned on.
     */

    public LocalDateTime getOnTime() {
        return onTime;
    }


    /**
     *
     * @return date the device was turned off.
     */

    public LocalDateTime getOffTime() {
        return offTime;
    }


    /**
     *
     * @return ampere value of the plug.
     */

    public float getAmpere() {
        return ampere;
    }


    /**
     *
     * @return voltage value (constant 220).
     */

    public int getVoltage() {
        return 220;
    }



    /**
     * connects the plug to a device.
     * @param deviceName name of the device.
     * @param ampere ampere value of the device.
     */

    public static void plugIn(String deviceName, int ampere) {
        SmartHomeDevice device = SmartHomeDevice.findDevice(deviceName);

        if (!(device instanceof SmartPlug)) {
            Io.writeToFile("ERROR: This device is not a smart plug!", true);
        }

        else if (ampere <= 0) {
            Io.writeToFile("ERROR: Ampere value must be a positive number!", true);
        }

        else if (((SmartPlug) device).isPlugged()) {
            Io.writeToFile("ERROR: There is already an item plugged in to that plug!" ,true);
        }

        else{
            ((SmartPlug) device).setPlugged(true);
            ((SmartPlug) device).setAmpere(ampere);
            if(device.getStatus().equals("On")) ((SmartPlug) device).setOnTime(Time.getCurrentTime());
        }
    }



    /**
     * removes the plug from the device.
     * @param deviceName name of the device
     */

    public static void plugOut(String deviceName) {
        SmartHomeDevice device = SmartHomeDevice.findDevice(deviceName);

        if (!(device instanceof SmartPlug)) {
            Io.writeToFile("ERROR: This device is not a smart plug!", true);
            return;
        }

        if (!(((SmartPlug) device).isPlugged())) {
            Io.writeToFile("ERROR: This plug has no item to plug out from that plug!", true);
            return;
        }

        ((SmartPlug) device).setPlugged(false);
        ((SmartPlug) device).setOffTime(Time.getCurrentTime());
        SmartPlug.findTotalConsumption((SmartPlug) device);
    }



    /**
     * Finds the total consumption consumed by the plug.
     * @param device device.
     */

    public static void findTotalConsumption(SmartPlug device){
        if (device.getOnTime() == null || device.getOffTime() == null) ;

        else {
            long consumptionTime = Duration.between(device.getOnTime(), device.getOffTime()).toMinutes();
            float consumption = (device.getAmpere() * device.getVoltage() * consumptionTime) /60;
            device.setTotalConsumption(consumption);
            device.setOffTime(null);
            device.setOnTime(null);
        }
    }
}
