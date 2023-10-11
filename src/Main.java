import java.io.FileNotFoundException;

public class Main {

    /**
     * Cleans the output file if it exists, creates it if it doesn't.
     * Creates the SmartHomeDevice list.
     * Executes the commands from input.
     */

    static String input, output;
    public static void main(String[] args) throws FileNotFoundException {
        input = args[0];
        output = args[1];
        Io.fileCleaner();
        SmartHomeDevice.SmartHomeSystem();
        ExecuteCommand.executeCommand();
    }
}