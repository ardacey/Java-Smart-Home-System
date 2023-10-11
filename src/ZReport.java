import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class ZReport {

    /**
     * Prepares the necessary parameters for the Z Report and then prints it to the output file.
     */

    public static void zReport(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        Io.writeToFile("Time is:\t" + Time.getCurrentTime().format(formatter), true);

        Collections.sort(SmartHomeDevice.getDevices());

        for (SmartHomeDevice device: SmartHomeDevice.getDevices()){
            String name;
            if (device instanceof SmartColorLamp) name = device.getClass().getSimpleName()
                    .replace("Smart", "Smart ").replace("Color", "Color ");


            else name = device.getClass().getSimpleName()
                    .replace("Smart", "Smart ");


            Io.writeToFile(name + " " + device.getName() + " is " +
                    device.getStatus().toLowerCase() + " and ", false);


            if (device instanceof SmartPlug){
                SmartPlug smartPlug = (SmartPlug) device;
                Io.writeToFile("consumed "+ String.format("%.2f",smartPlug.getTotalConsumption())
                        .replace(".", ",") + "W so far (excluding current device), ", false);
            }


            else if (device instanceof SmartCamera){
                SmartCamera smartCamera = (SmartCamera) device;
                Io.writeToFile("used " + String.format("%.2f",smartCamera.getTotalStorage())
                        .replace(".", ",") + " MB of storage so far (excluding current status), ", false);
            }


            else if (device instanceof SmartColorLamp){
                SmartColorLamp smartColorLamp = (SmartColorLamp) device;
                String colorValue;
                if (smartColorLamp.isHex()) colorValue = "0x" + String.format("%06X", smartColorLamp.getColorValue());
                else colorValue = smartColorLamp.getColorValue() + "K";
                Io.writeToFile("its color value is " + colorValue + " with " + smartColorLamp.getBrightness()
                        + "% brightness, ", false);
            }


            else if (device instanceof SmartLamp){
                SmartLamp smartLamp = (SmartLamp) device;
                Io.writeToFile("its kelvin value is " + smartLamp.getKelvin() + "K with "
                        + smartLamp.getBrightness() + "% brightness, ", false);
            }


            if (device.getSwitchTime() == null) Io.writeToFile("and its time to switch its status is null.", true);
            else if (device.getSwitchTime() != null)
                Io.writeToFile("and its time to switch its status is "
                        + device.getSwitchTime().format(Time.getFormatter()) + ".", true);

        }
    }
}
