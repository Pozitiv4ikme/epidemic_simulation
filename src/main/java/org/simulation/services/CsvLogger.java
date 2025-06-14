package org.simulation.services;

import org.simulation.Epoch;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CsvLogger {
    private final String fileName;
    private final String header;
    private boolean fileInitialized;

    public CsvLogger(String baseName, String header) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        this.fileName = baseName + "_" + timestamp + ".csv";
        this.header = header;
        this.fileInitialized = false; // Файл точно новий, тому false
    }

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

    public String getFileName() {
        return fileName;
    }
}
