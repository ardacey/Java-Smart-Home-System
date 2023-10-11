import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class SmartHomeDevice implements Comparable<SmartHomeDevice> {
    private static ArrayList<SmartHomeDevice> devices;
    private String status;
    private String name;
    private LocalDateTime switchTime;

    /**
     * All devices' constructor.
     * @param name name of the device.
     * @param status status of the device.
     */

    public SmartHomeDevice(String name, String status) {
        this.name = name;
        this.status = status;
        this.switchTime = null;
    }


    /**
     *
     * @return status of the device.
     */

    public String getStatus() {
        return status;
    }


    /**
     *
     * @return name of the device.
     */

    public String getName() {
        return name;
    }


    /**
     *
     * @return switch time of the device.
     */

    public LocalDateTime getSwitchTime() {
        return switchTime;
    }


    /**
     *
     * @return all devices.
     */

    public static ArrayList<SmartHomeDevice> getDevices() {
        return devices;
    }


    /**
     *
     * @param status set status of the device.
     */

    public void setStatus(String status) {
        this.status = status;
    }


    /**
     *
     * @param name set name of the device.
     */

    public void setName(String name) {
        this.name = name;
    }


    /**
     *
     * @param switchTime set switch time of the device.
     */

    public void setSwitchTime(LocalDateTime switchTime) {
        this.switchTime = switchTime;
    }


    /**
     * create new array for the devices.
     */

    public static void SmartHomeSystem() {
        devices = new ArrayList<>();
    }


    /**
     *
     * @param device add device to the "devices" arraylist.
     */

    public static void addDevice(SmartHomeDevice device) {
        devices.add(device);
    }


    /**
     *
     * @param device remove device from the "devices" arraylist.
     */

    public static void removeDevice(SmartHomeDevice device) {
        devices.remove(device);
    }



    /**
     * Finds device from device list.
     * @param name device name
     * @return return the device
     */

    public static SmartHomeDevice findDevice(String name) {
        for (SmartHomeDevice device : devices) if (device.getName().equals(name)) return device;
        return null;
    }



    /**
     * Comparing method (override).
     * @param d the object to be compared.
     * @return if none of the devices are null compare with each other.
     */

    @Override
    public int compareTo(SmartHomeDevice d) {
        if (this.switchTime == null && d.getSwitchTime() == null) return 0;

        else if (this.switchTime == null) return 1;

        else if (d.getSwitchTime() == null) return -1;

        else return this.switchTime.compareTo(d.getSwitchTime());
    }

}
