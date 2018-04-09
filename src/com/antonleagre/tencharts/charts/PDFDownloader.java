package com.antonleagre.tencharts.charts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * This class hosts two methods to get paths to charts.
 * Method 1: download them from the location found in the locs.json
 * Method 2: set them according to a folder
 */
public class PDFDownloader {
    /**
     *
     * @param airport
     * @param outputDir base directory for the charts. a new subdir will be made with the airport's icao code
     */
    public static void downloadAirportCharts(Airport airport, File outputDir){
        airport.getCharts().forEach(chart -> {
            try {
                ReadableByteChannel rbc = Channels.newChannel(new URL(chart.getLocation()).openStream());
                File outputFile = new File(outputDir.getPath() + "\\" + airport.getCode() + "\\" + chart.getIdentifier() + ".pdf");
                outputFile.getParentFile().mkdirs(); //make sure the parent dirs are made before we start streaming files
                FileOutputStream fos = new FileOutputStream(outputFile); // so a chart would be saved as DDIR/ICAO/CHART.pdf
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
                chart.setLocalLocation(outputFile.getPath()); // TODO: 4/9/2018 we need to set this variable just from a check and not if you download so you dont have to download everytime 
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Downloaded all charts for airport: " + airport.getName());
    }
    public static void setAirportChartsFromDir(Airport airport, String path){
        airport.getCharts().forEach(chart -> chart.setLocalLocation(path + "\\" + chart.getIdentifier() + ".pdf"));
    }


}
