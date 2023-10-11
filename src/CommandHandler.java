public class CommandHandler {

    /**
     * Changes the name of the device.
     * @param oldName old name of the device.
     * @param newName new name of the device.
     */

    public static void changeName(String oldName, String newName) {
        SmartHomeDevice device = SmartHomeDevice.findDevice(oldName);

        if (oldName.equals(newName)) {
            Io.writeToFile("ERROR: Both of the names are the same, nothing changed!", true);

        } else if (SmartHomeDevice.findDevice(newName) != null) {
            Io.writeToFile("ERROR: There is already a smart device with same name!", true);

        } else device.setName(newName);
    }



    /**
     * Switches the status of the device(without exception message)
     * @param name device name.
     * @param status device status.
     */

    public static void switchDevice(String name, String status) {
        try {
            SmartHomeDevice device = SmartHomeDevice.findDevice(name);
            if (device.getName().equals(name)) {
                if (status.equals("On")) {
                    if (device.getStatus().equals("On")) {
                        Io.writeToFile("ERROR: This device is already switched on!", true);
                    } else {

                        device.setStatus("On");
                        if (device instanceof SmartCamera) ((SmartCamera) device).setOnTime(Time.getCurrentTime());
                        else if (device instanceof SmartPlug) {
                            if (((SmartPlug) device).isPlugged()) ((SmartPlug) device).setOnTime(Time.getCurrentTime());
                        }
                    }

                } else if (status.equals("Off")) {
                    if (device.getStatus().equals("Off")) {
                        Io.writeToFile("ERROR: This device is already switched off!", true);

                    } else {
                        device.setStatus("Off");

                        if (device instanceof SmartCamera) {
                            ((SmartCamera) device).setOffTime(Time.getCurrentTime());
                            SmartCamera.findTotalStorage((SmartCamera) device);
                            ((SmartCamera) device).setOffTime(null);
                            ((SmartCamera) device).setOnTime(null);

                        } else if (device instanceof SmartPlug) {
                            ((SmartPlug) device).setOffTime(Time.getCurrentTime());
                            SmartPlug.findTotalConsumption((SmartPlug) device);
                            ((SmartPlug) device).setOffTime(null);
                            ((SmartPlug) device).setOnTime(null);
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            Io.writeToFile("ERROR: There is not such a device!", true);
        }
    }



    /**
     * Switches the status of the device. (without exception message)
     * @param device device.
     */

    public static void Switch(SmartHomeDevice device) {
        if (device.getStatus().equals("Off")) {
            device.setStatus("On");
            if (device instanceof SmartCamera) ((SmartCamera) device).setOnTime(Time.getCurrentTime());

            else if (device instanceof SmartPlug) {
                if (((SmartPlug) device).isPlugged()) ((SmartPlug) device).setOnTime(Time.getCurrentTime());

            }

        } else if (device.getStatus().equals("On")) {
            device.setStatus("Off");

            if (device instanceof SmartCamera) {
                ((SmartCamera) device).setOffTime(Time.getCurrentTime());
                SmartCamera.findTotalStorage((SmartCamera) device);
                ((SmartCamera) device).setOffTime(null);
                ((SmartCamera) device).setOnTime(null);

            } else if (device instanceof SmartPlug) {
                ((SmartPlug) device).setOffTime(Time.getCurrentTime());
                SmartPlug.findTotalConsumption((SmartPlug) device);
                ((SmartPlug) device).setOffTime(null);
                ((SmartPlug) device).setOnTime(null);
            }
        }
    }



    /**
     * Removes device from SmartHomeDevice array.
     * @param deviceName device name.
     */

    public static void removeDevice(String deviceName) {
        SmartHomeDevice device = SmartHomeDevice.findDevice(deviceName);
        if (device == null) {
            Io.writeToFile("ERROR: Device not found.", true);

        } else {
            device.setStatus("Off");
            Io.writeToFile("SUCCESS: Information about removed smart device is as follows:", true);
            String name;

            if (device instanceof SmartColorLamp) name = device.getClass().getSimpleName()
                    .replace("Smart", "Smart ").replace("Color", "Color ");

            else name = device.getClass().getSimpleName().replace("Smart", "Smart ");

            Io.writeToFile(name + " " + device.getName() + " is "
                    + device.getStatus().toLowerCase() + " and ", false);

            if (device instanceof SmartPlug) {
                SmartPlug smartPlug = (SmartPlug) device;
                smartPlug.setOffTime(Time.getCurrentTime());
                SmartPlug.findTotalConsumption(smartPlug);
                Io.writeToFile("consumed " + String.format("%.2f", smartPlug.getTotalConsumption())
                        .replace(".", ",") + "W so far (excluding current device), ", false);

            } else if (device instanceof SmartCamera) {
                SmartCamera smartCamera = (SmartCamera) device;
                smartCamera.setOffTime(Time.getCurrentTime());
                SmartCamera.findTotalStorage(smartCamera);
                Io.writeToFile("used " + String.format("%.2f", smartCamera.getTotalStorage())
                        .replace("." , ",") + " MB of storage so far (excluding current status), ", false);

            } else if (device instanceof SmartColorLamp) {
                SmartColorLamp smartColorLamp = (SmartColorLamp) device;
                String colorValue;
                if (smartColorLamp.isHex()) {
                    colorValue ="0x" + String.format("%06X", smartColorLamp.getColorValue());
                }

                else {
                    colorValue = smartColorLamp.getColorValue() + "K";
                }

                Io.writeToFile("its color value is " + colorValue + " with " + smartColorLamp.getBrightness()
                        + "% brightness, ", false);

            } else if (device instanceof SmartLamp) {
                SmartLamp smartLamp = (SmartLamp) device;
                Io.writeToFile("its kelvin value is " + smartLamp.getKelvin()
                        + "K with " + smartLamp.getBrightness() + "% brightness, ", false);
            }

            if (device.getSwitchTime() == null) {
                Io.writeToFile("and its time to switch its status is null.", true);

            } else if (device.getSwitchTime() != null) {
                Io.writeToFile("and its time to switch its status is "
                        + device.getSwitchTime().format(Time.getFormatter()) + ".", true);
            }

            SmartHomeDevice.removeDevice(device);
        }
    }
}
