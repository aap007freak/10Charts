package com.antonleagre.tencharts.charts;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class PDFDownloader {

    public static void downloadAirportCharts(Airport airport, File outputDir){
        airport.getCharts().forEach(chart -> {
            try {
                ReadableByteChannel rbc = Channels.newChannel(new URL(chart.getLocation()).openStream());
                File outputFile = new File(outputDir.getPath() + "\\" + airport.getCode() + "\\" + chart.getIdentifier() + ".pdf");
                outputFile.getParentFile().mkdirs();
                FileOutputStream fos = new FileOutputStream(outputFile); // so a chart would be saved as DDIR/ICAO/CHART.pdf
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Downloaded all charts for airport: " + airport.getName());
    }


}
