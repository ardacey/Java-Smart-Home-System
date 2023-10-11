public class SmartLamp extends SmartHomeDevice {

    private int kelvin;
    protected int brightness;

    /**
     * default Smart Lamp constructor.
     * @param name name of the device.
     * @param status status of the device.
     */

    public SmartLamp(String name, String status) {
        super(name, status);
        kelvin = 4000;
        brightness = 100;
    }



    /**
     * Smart Lamp constructor.
     * @param name name of the device.
     * @param status status of the device.
     * @param kelvin kelvin value of the device.
     * @param brightness brightness value of the device.
     */

    public SmartLamp(String name, String status, int kelvin, int brightness) {
        super(name, status);
        this.kelvin = kelvin;
        this.brightness = brightness;
    }


    /**
     *
     * @return kelvin value of the lamp.
     */

    public int getKelvin() {
        return kelvin;
    }


    /**
     *
     * @return brightness value of the lamp.
     */

    public int getBrightness() {
        return brightness;
    }


    /**
     *
     * @param kelvin set kelvin value of the lamp.
     */

    public void setKelvin(int kelvin) {
        this.kelvin = kelvin;
    }


    /**
     *
     * @param brightness set brightness value of the lamp.
     */

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }
}
