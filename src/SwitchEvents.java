import java.util.ArrayList;
import java.util.List;

public class SwitchEvents<SwitchEvent> {
    private final List<SwitchEvent> events;
    public SwitchEvents() {
        events = new ArrayList<>();
    }


    /**
     *
     * @param event add switch event.
     */

    public void addEvent(SwitchEvent event) {
        events.add(event);
    }


    /**
     *
     * @return first event.
     */

    public SwitchEvent getNextEvent() {
        return events.get(0);
    }



    /**
     * It prepares the events that will happen in the future after the time adjustment.
     * @return returns all specified events.
     */

    public static SwitchEvents<String> setEvents(){
        SwitchEvents<String> event = new SwitchEvents<>();

        for (SmartHomeDevice device: SmartHomeDevice.getDevices())
            if (device.getSwitchTime() != null) event.addEvent(device.getSwitchTime().format(Time.getFormatter()));

        return event;
    }



    /**
     * jumps to the next event.
     */

    public static void Nop(){

        try {
            Time.setTime(setEvents().getNextEvent());
            SwitchEvents.setEvents();
            SwitchEvents.EventExecuter();

        } catch (Exception e){
            Io.writeToFile("ERROR: There is nothing to switch!", true);
            ExecuteCommand.shouldExit = true;
        }
    }



    /**
     * executes all events up to the current date.
     */

    public static void EventExecuter(){
        for (SmartHomeDevice device: SmartHomeDevice.getDevices())
            if (device.getSwitchTime() != null) {
                if (Time.isAfter(Time.getCurrentTime(), device.getSwitchTime())) {
                    CommandHandler.Switch(device);
                    device.setSwitchTime(null);
                }
            }
    }
}
