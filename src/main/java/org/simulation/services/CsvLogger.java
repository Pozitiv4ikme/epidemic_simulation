package org.simulation.services;

import org.simulation.Epoch;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class responsible for logging simulation statistics to a CSV file.
 * Adds a timestamp to the filename to keep logs unique.
 */

public class CsvLogger {
    private final String fileName;
    private final String header;
    private boolean fileInitialized;

    /**
     * Constructor initializes the CSV logger with a base name and header.
     * Timestamp is appended to the filename to prevent overwriting.
     * @param baseName base name for the CSV file
     * @param header   header row to be written once
     */

    public CsvLogger(String baseName, String header) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        this.fileName = baseName + "_" + timestamp + ".csv";
        this.header = header;
        this.fileInitialized = false;
    }

    /**
     * Logs a single epoch of simulation data into the CSV file.
     * Writes header if it's the first time.
     * @param epoch the epoch data to log
     */

    public void log(Epoch epoch) {
        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            if (!fileInitialized) {
                out.println(header);
                fileInitialized = true;
            }

            out.printf("%d,%d,%d,%d,%d\n",
                    epoch.dayNumber(),
                    epoch.newInfected(),
                    epoch.totalInfected(),
                    epoch.newDeaths(),
                    epoch.totalDeaths());

        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    /**
     * Returns the full name of the CSV file (with timestamp).
     * @return file name
     */

    public String getFileName() {
        return fileName;
    }
}
