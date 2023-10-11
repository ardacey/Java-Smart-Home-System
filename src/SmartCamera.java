import java.time.Duration;
import java.time.LocalDateTime;

public class SmartCamera extends SmartHomeDevice{
    private final float mbperrec;
    private float totalStorage;
    private LocalDateTime onTime, offTime;

    /**
     * Smart Camera constructor.
     * @param name Smart Camera name.
     * @param mbperrec megabytes consumed per minute by the camera.
     * @param status device status.
     * @param totalStorage total storage of the camera.
     */

    public SmartCamera(String name, float mbperrec, String status, long totalStorage){
        super(name,status);
        if (status.equals("On")) setOnTime(Time.getCurrentTime());
        this.mbperrec = mbperrec;
        this.totalStorage = totalStorage;
    }


    /**
     *
     * @return megabytes consumed per minute by the camera.
     */

    public float getMbperrec() {
        return mbperrec;
    }


    /**
     *
     * @returntotal storage of the camera.
     */

    public float getTotalStorage() {
        return totalStorage;
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
     * @param totalStorage adds the space consumed by the device.
     */

    public void setTotalStorage(float totalStorage) {
        this.totalStorage += totalStorage;
    }


    /**
     *
     * @param onTime set on time.
     */

    public void setOnTime(LocalDateTime onTime) {
        this.onTime = onTime;
    }


    /**
     *
     * @param offTime set off time.
     */

    public void setOffTime(LocalDateTime offTime) {
        this.offTime = offTime;
    }


    /**
     * Finds total storage consumed by Smart Camera.
     * @param device device.
     */

    public static void findTotalStorage(SmartCamera device){
        long consumptionTime = Duration.between(device.getOnTime(), device.getOffTime()).toMinutes();
        float storage = device.getMbperrec() * consumptionTime;
        device.setTotalStorage(storage);
        device.setOffTime(null);
        device.setOnTime(null);
    }
}
