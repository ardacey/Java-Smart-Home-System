import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ExecuteCommand {
    static boolean setTimeExecuted = false, finish = false, shouldExit = false;
    static Scanner ExecutionInput;
    static Scanner CommandInput;
    static String command, commands;


    /**
     * Executes the commands from the input.
     * @throws FileNotFoundException when file is not found throws FileNotFoundException. It's not necessary actually,
     * because I already catch FileNotFoundException in IO class.
     * CommandInput prints the lines to the output file, ExecutionInput executes the commands.
     */

    public static void executeCommand() throws FileNotFoundException {
        CommandInput = new Scanner(new File(Main.input));
        ExecutionInput = new Scanner(new File(Main.input));
        try {
            inputloop:
            while (CommandInput.hasNextLine()) {
                commands = CommandInput.nextLine();
                if (commands.equals("")) continue;
                Io.writeToFile("COMMAND: " + commands, true);

                if (!setTimeExecuted) {
                    if (ExecutionInput.next().equals("SetInitialTime")) {
                        try {
                            Time.setInitialTime(ExecutionInput.next());
                            setTimeExecuted = true;
                            continue;

                        } catch (NoSuchElementException e) {
                            Io.writeToFile("ERROR: First command must be set " +
                                    "initial time! Program is going to terminate!", false);
                            finish = true;
                            break;

                        } catch (Exception e) {
                            Io.writeToFile("ERROR: Format of the initial date is wrong! " +
                                    "Program is going to terminate!", false);
                            finish = true;
                            break;
                        }

                    } else {
                        Io.writeToFile("ERROR: First command must be set " +
                                "initial time! Program is going to terminate!", false);
                        finish = true;
                        break;
                    }
                }


                command = ExecutionInput.next();
                if (command.equals("Add")) {
                    String[] params = ExecutionInput.nextLine().split("\t");
                    String deviceName = params[2];
                    if (params[1].equals("SmartCamera") && params.length < 4) {
                        Io.writeToFile("ERROR: Erroneous command!", true);
                        continue;
                    }

                    else if (params.length < 3) {
                        Io.writeToFile("ERROR: Erroneous command!", true);
                        continue;
                    }

                    for (SmartHomeDevice device: SmartHomeDevice.getDevices()){
                        if (deviceName.equals(device.getName())){
                            Io.writeToFile("ERROR: There is already a smart device with same name!", true);
                            continue inputloop;
                        }
                    }

                    switch (params[1]) {
                        case "SmartPlug": {
                            String status = "Off";
                            if (params.length > 3) {
                                if (params[3].equals("On")) status = "On";

                                else if (params[3].equals("Off")) status = "Off";

                                else {
                                    Io.writeToFile("ERROR: Erroneous command!", true);
                                    continue;
                                }
                            }

                            float ampere = 0;
                            boolean plugged = false;
                            if (params.length > 4) {
                                if (params.length > 5) throw new Exception();

                                if (Float.parseFloat(params[4]) <= 0) {
                                    Io.writeToFile("ERROR: Ampere value must be a positive number!", true);
                                    continue;

                                } else {
                                    ampere = Float.parseFloat(params[4]);
                                    plugged = true;
                                }
                            }

                            SmartPlug smartPlug = new SmartPlug(deviceName, status, ampere, 0, plugged);
                            SmartHomeDevice.addDevice(smartPlug);
                            if (status.equals("On") && plugged) smartPlug.setOnTime(Time.getCurrentTime());
                            break;
                        }

                        case "SmartCamera": {
                            String status = "Off";
                            if (params.length > 4) {
                                if (params[4].equals("On")) status = "On";

                                else if (params[4].equals("Off")) status = "Off";

                                else {
                                    Io.writeToFile("ERROR: Erroneous command!", true);
                                    continue;
                                }
                            }

                            float mbperrec = 0;
                            if (params.length > 3) {
                                if (Float.parseFloat(params[3]) <= 0) {
                                    Io.writeToFile("ERROR: Megabyte value must be a positive number!", true);
                                    continue;
                                } else {
                                    mbperrec = Float.parseFloat(params[3]);
                                }
                            }

                            SmartCamera smartCamera = new SmartCamera(deviceName, mbperrec, status, 0);
                            SmartHomeDevice.addDevice(smartCamera);
                            break;
                        }

                        case "SmartLamp": {
                            String status = "Off";
                            if (params.length > 3) {
                                if (params[3].equals("On")) status = "On";

                                else if (params[3].equals("Off")) status = "Off";

                                else {
                                    Io.writeToFile("ERROR: Erroneous command!", true);
                                    continue;
                                }
                            }

                            int kelvinValue = params.length > 4 ? Integer.parseInt(params[4]) : 4000;
                            int brightness = params.length > 5 ? Integer.parseInt(params[5]) : 100;
                            if (!(2000 <= kelvinValue && kelvinValue <= 6500)) {
                                Io.writeToFile("ERROR: Kelvin value must be in range of 2000K-6500K!", true);
                                continue;
                            }

                            if (!(0 <= brightness && brightness <= 100)) {
                                Io.writeToFile("ERROR: Brightness must be in range of 0%-100%!", true);
                                continue;
                            }

                            SmartLamp smartLamp = new SmartLamp(deviceName, status, kelvinValue, brightness);
                            SmartHomeDevice.addDevice(smartLamp);
                            break;
                        }

                        case "SmartColorLamp": {
                            String status = "Off";
                            if (params.length > 3) {
                                if (params[3].equals("On")) status = "On";

                                else if (params[3].equals("Off")) status = "Off";

                                else {
                                    Io.writeToFile("ERROR: Erroneous command!", true);
                                    continue;
                                }
                            }

                            int kelvinOrColor = 4000;
                            boolean hex = false;
                            try {
                                if (params.length > 4) {
                                    if (params[4].startsWith("0x")) {
                                        hex = true;
                                        kelvinOrColor = Integer.parseInt(params[4].replace("0x", ""), 16);
                                        if (!(0 <= kelvinOrColor && kelvinOrColor <= 16777215)) {
                                            Io.writeToFile("ERROR: Color code value must be in range of 0x0-0xFFFFFF!", true);
                                            continue;
                                        }

                                    } else {
                                        kelvinOrColor = Integer.parseInt(params[4]);
                                        if (!(2000 <= kelvinOrColor && kelvinOrColor <= 6500)) {
                                            Io.writeToFile("ERROR: Kelvin value must be in range of 2000K-6500K!", true);
                                            continue;
                                        }
                                    }
                                }

                            } catch (Exception e) {
                                Io.writeToFile("ERROR: Erroneous command!", true);
                                continue;
                            }

                            int brightness = params.length > 5 ? Integer.parseInt(params[5]) : 100;
                            if (!(0 <= brightness && brightness <= 100)) {
                                Io.writeToFile("ERROR: Brightness must be in range of 0%-100%!", true);
                                continue;
                            }

                            SmartColorLamp smartColorLamp = new SmartColorLamp(deviceName, status, kelvinOrColor, brightness, hex);
                            SmartHomeDevice.addDevice(smartColorLamp);
                            break;
                        }
                    }
                    continue;
                }
                try {
                    String[] commandparam = new String[3];
                    try{
                        commandparam = ExecutionInput.nextLine().split("\t");
                    } catch (NoSuchElementException ignored) {
                    }


                    switch (command) {
                        case "SetTime":
                            if (commandparam.length != 2) throw new Exception();
                            else Time.setTime(commandparam[1]);
                            break;

                        case "SkipMinutes":
                            if (commandparam.length != 2) throw new Exception();
                            else Time.skipMinutes(Integer.parseInt(commandparam[1]));
                            break;

                        case "Nop":
                            SwitchEvents.Nop();
                            break;

                        case "Remove":
                            if (commandparam.length != 2) throw new Exception();
                            else CommandHandler.removeDevice(commandparam[1]);
                            break;

                        case "SetSwitchTime":
                            if (commandparam.length != 3) throw new Exception();
                            else Time.setSwitchTime(commandparam[1], commandparam[2]);
                            break;

                        case "Switch":
                            if (commandparam.length != 3) throw new Exception();
                            else CommandHandler.switchDevice(commandparam[1], commandparam[2]);
                            break;

                        case "ChangeName":
                            if (commandparam.length != 3) throw new Exception();
                            else CommandHandler.changeName(commandparam[1], commandparam[2]);
                            break;

                        case "PlugIn":
                            if (commandparam.length != 3) throw new Exception();
                            else SmartPlug.plugIn(commandparam[1], Integer.parseInt(commandparam[2]));
                            break;

                        case "PlugOut":
                            if (commandparam.length != 2) throw new Exception();
                            else SmartPlug.plugOut(commandparam[1]);
                            break;

                        case "SetKelvin":
                            if (commandparam.length != 3) throw new Exception();
                            else SmartColorLamp.setKelvin(commandparam[1], Integer.parseInt(commandparam[2]));
                            break;

                        case "SetBrightness":
                            if (commandparam.length != 3) throw new Exception();
                            else SmartColorLamp.setBrightness(commandparam[1], Integer.parseInt(commandparam[2]));
                            break;

                        case "SetColorCode":
                            if (commandparam.length != 3) throw new Exception();
                            else SmartColorLamp.setColorCode(commandparam[1], commandparam[2]);
                            break;

                        case "SetWhite":
                            if (commandparam.length != 4) throw new Exception();
                            else SmartColorLamp.setWhite(commandparam[1], Integer.parseInt(commandparam[2]), Integer.parseInt(commandparam[3]));
                            break;

                        case "SetColor":
                            if (commandparam.length != 4) throw new Exception();
                            else SmartColorLamp.setColor(commandparam[1], commandparam[2], Integer.parseInt(commandparam[3]));
                            break;

                        case "ZReport":
                            ZReport.zReport();
                            break;

                        default:
                            throw new Exception();
                    }

                    if (shouldExit) {
                        shouldExit = false;
                        continue;
                    }

                    Collections.sort(SmartHomeDevice.getDevices());
                } catch (Exception e) {
                    Io.writeToFile("ERROR: Erroneous command!", true);
                }
            }

        } catch (Exception e) {
            Io.writeToFile("ERROR: Erroneous command!", true);
        } finally {
            if (!finish && !(command.equals("ZReport"))){
                Io.writeToFile("ZReport:", true);
                ZReport.zReport();
            }
        }
    }
}