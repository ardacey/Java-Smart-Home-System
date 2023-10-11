public class SmartColorLamp extends SmartLamp {
    private int colorValue;
    private boolean isHex;

    /**
     * Smart Color Lamp constructor.
     * @param name name of the lamp.
     * @param status status of the lamp.
     * @param colorValue color value of the lamp.
     * @param brightness brightness of the lamp.
     * @param isHex if color of the lamp is hexadecimal value, true; If kelvin, false.
     */

    public SmartColorLamp(String name, String status, int colorValue, int brightness, boolean isHex) {
        super(name, status);
        this.brightness = brightness;
        this.isHex = isHex;
        this.colorValue = colorValue;
    }



    /**
     * Sets the kelvin value of the lamp.
     * @param name name of the device.
     * @param kelvin kelvin value to be set.
     */

    public static void setKelvin(String name, int kelvin) {
        SmartHomeDevice device = SmartHomeDevice.findDevice(name);
        if (!(device instanceof SmartLamp)) {
            Io.writeToFile("ERROR: This device is not a smart lamp!", true);

        } else {
            if (!(2000 <= kelvin && kelvin <= 6500))
                Io.writeToFile("ERROR: Kelvin value must be in range of 2000K-6500K!", true);

            else if (device instanceof SmartColorLamp) {
                SmartColorLamp lamp = (SmartColorLamp) device;
                lamp.setColorValue(kelvin);
            }

            else if (device instanceof SmartLamp) {
                SmartLamp lamp = (SmartLamp) device;
                lamp.setKelvin(kelvin);
            }
        }
    }



    /**
     * Sets the brightness value of the lamp.
     * @param name name of the device.
     * @param brightness brightness value to be set.
     */

    public static void setBrightness(String name, int brightness) {
        SmartHomeDevice device = SmartHomeDevice.findDevice(name);
        if (!(device instanceof SmartLamp)) {
            Io.writeToFile("ERROR: This device is not a smart lamp!", true);

        } else {
            SmartLamp lamp = (SmartLamp) device;
            if (!(0 <= brightness && brightness <= 100))
                Io.writeToFile("ERROR: Brightness must be in range of 0%-100%!", true);

            else lamp.setBrightness(brightness);
        }
    }



    /**
     * Sets the color code value of the lamp.
     * @param name name of the device.
     * @param colorCode color code value to be set.
     */

    public static void setColorCode(String name, String colorCode) {
        SmartHomeDevice device = SmartHomeDevice.findDevice(name);
        if (!(device instanceof SmartColorLamp)) {
            Io.writeToFile("ERROR: This device is not a smart color lamp!", true);

        } else {
            SmartColorLamp lamp = (SmartColorLamp) device;
            int color = Integer.parseInt(colorCode.replace("0x", ""), 16);

            if (!(0 <= color && color <= 16777215))
                Io.writeToFile("ERROR: Color code value must be in range of 0x0-0xFFFFFF!", true);

            else lamp.setColorValue(color);
            lamp.setHex(true);
        }
    }



    /**
     * Sets the kelvin and the brightness value of the lamp.
     * @param name name of the device.
     * @param kelvin kelvin value to be set.
     * @param brightness brightness value to be set.
     */

    public static void setWhite(String name, int kelvin, int brightness) {
        SmartHomeDevice device = SmartHomeDevice.findDevice(name);
        if (!(device instanceof SmartLamp)) {
            Io.writeToFile("ERROR: This device is not a smart lamp!", true);
        } else {

            if (!(2000 <= kelvin && kelvin <= 6500))
                Io.writeToFile("ERROR: Kelvin value must be in range of 2000K-6500K!", true);

            else if (!(0 <= brightness && brightness <= 100))
                Io.writeToFile("ERROR: Brightness must be in range of 0%-100%!", true);

            else if (device instanceof SmartColorLamp) {
                SmartColorLamp lamp = (SmartColorLamp) device;
                lamp.setColorValue(kelvin);
                lamp.setBrightness(brightness);
            }

            else if (device instanceof SmartLamp) {
                SmartLamp lamp = (SmartLamp) device;
                lamp.setKelvin(kelvin);
                lamp.setBrightness(brightness);
            }
        }
    }



    /**
     * Sets the color code and the brightness value of the lamp.
     * @param name name of the device.
     * @param colorCode color code value to be set.
     * @param brightness brightness value to be set.
     */

    public static void setColor(String name, String colorCode, int brightness) {
        SmartHomeDevice device = SmartHomeDevice.findDevice(name);
        if (!(device instanceof SmartColorLamp)) {
            Io.writeToFile("ERROR: This device is not a smart color lamp!", true);

        } else {
            SmartColorLamp lamp = (SmartColorLamp) device;
            int color = Integer.parseInt(colorCode.replace("0x", ""), 16);
            if (!(0 <= color && color <= 16777215))
                Io.writeToFile("ERROR: Color code value must be in range of 0x0-0xFFFFFF!", true);

            else if (!(0 <= brightness && brightness <= 100))
                Io.writeToFile("ERROR: Brightness must be in range of 0%-100%!", true);

            else {
                lamp.setColorValue(color);
                lamp.setBrightness(brightness);
                lamp.setHex(true);
            }
        }
    }


    /**
     *
     * @return color value of the lamp.
     */

    public int getColorValue() {
        return colorValue;
    }


    /**
     *
     * @param colorValue set the color value of the lamp.
     */

    public void setColorValue(int colorValue) {
        this.colorValue = colorValue;
    }


    /**
     *
     * @return whether the device's color value is hexadecimal.
     */

    public boolean isHex() {
        return isHex;
    }


    /**
     *
     * @param hex set whether the device's color value is hexadecimal.
     */

    public void setHex(boolean hex) {
        isHex = hex;
    }
}
