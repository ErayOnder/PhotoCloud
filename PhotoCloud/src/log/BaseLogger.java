package log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

public class BaseLogger {
	
    public static String infoLogFile = "application_info.txt";
    public static String errorLogFile = "application_error.txt";
    private static SimpleDateFormat logDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");

    private final String logLevel;
    private final String logFile;

    /**
     * @param logLevel -> the log level that will be added to log messages.
     * @param logFile -> the file which log messages will be written.
     */
    private BaseLogger(String logLevel, String logFile) {
        this.logLevel = logLevel;
        this.logFile = logFile;
    }

    /**
     * Creates a new instance of BaseLogger with the log level set to "[INFO]" and the log file set to infoLogFile.
     * @return -> A BaseLogger instance for logging info messages.
     */
    public static BaseLogger info() {
        return new BaseLogger("[INFO]", infoLogFile);
    }

    /**
     * Creates a new instance of BaseLogger with the log level set to "[ERROR]" and the log file set to errorLogFile.
     * @return -> A BaseLogger instance for logging error messages.
     */
    public static BaseLogger error() {
        return new BaseLogger("[ERROR]", errorLogFile);
    }

    /**
     * Logs the given message to the log file.
     * @param message -> the message to be logged.
     */
    public void log(String message) {
        String logEntry = buildLogEntry(message);
        writeLog(logEntry);
    }

    /**
     * Builds the log entry by adding a timestamp, log level, and the given message.
     * @param message -> the message to be logged.
     * @return -> the formatted log entry.
     */
    private String buildLogEntry(String message) {
        String timestamp = logDateFormat.format(new Date());
        return String.format("[%s] %s %s", timestamp, logLevel, message);
    }

    /**
     * Writes the log entry to the log file.
     * @param logEntry -> the log entry to be written.
     */
    private void writeLog(String logEntry) {
        try (Formatter output = new Formatter(new FileOutputStream(logFile, true))) {
            output.format("%s%n", logEntry);
        } catch (IOException e) {
            System.out.println("Failed to write log entry: " + logEntry);
            e.printStackTrace();
        }
    }
}