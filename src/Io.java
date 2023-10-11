import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
public class Io {

    /**
     * Prints the desired commands to the output file.
     * @param content content to write to output file.
     * @param newLine boolean variable, if I want to skip to the newline.
     */

    public static void writeToFile(String content, boolean newLine) {
        PrintStream ps = null;
        try {
            ps = new PrintStream(new FileOutputStream(Main.output, true));
            ps.print(content + (newLine ? "\n" : ""));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.flush();
                ps.close();
            }
        }
    }


    /**
     * Cleans the output file if it exists, creates it if it doesn't.
     */

    public static void fileCleaner() {
        try {
            PrintWriter writer = new PrintWriter(Main.output);
            writer.print("");
            writer.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}