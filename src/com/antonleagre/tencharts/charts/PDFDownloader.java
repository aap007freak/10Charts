package com.antonleagre.tencharts.charts;

import org.apache.commons.io.FileUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.security.Security;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * This class hosts two methods to get paths to charts.
 * Method 1: download them from the location found in the locs.json
 * Method 2: set them according to a folder
 */
public class PDFDownloader {
    /**
     * Obsolete but might need it yaknow
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
                chart.setLocalLocation(outputFile.getPath());
            } catch (IOException e) {
                e.printStackTrace();
                // TODO: 4/21/2018 make the error handling a bit more elaborate 
            }
        });
        System.out.println("Downloaded all charts for airport: " + airport.getName());
    }

    public static Chart downloadSingleChart(Chart chart){
        try {
            ReadableByteChannel rbc = Channels.newChannel(new URL(chart.getLocation()).openStream());
            File outputFile = new File(System.getProperty("java.io.tmpdir") + chart.getIdentifier() + ".pdf");
            outputFile.getParentFile().mkdirs(); //make sure the parent dirs are made before we start streaming files
            FileOutputStream fos = new FileOutputStream(outputFile); // so a chart would be saved as DDIR/ICAO/CHART.pdf
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            chart.setLocalLocation(outputFile.getPath());
            return chart;
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 4/21/2018 make the error handling a bit more elaborate
            return null;
        }
    }
    public static Chart downloadSingleChartCommons(Chart chart){
        try {
            Security.setProperty( "ssl.SocketFactory.provider", "com.ibm.jsse2.SSLSocketFactoryImpl");
            Security.setProperty( "ssl.ServerSocketFactory.provider", "com.ibm.jsse2.SSLServerSocketFactoryImpl");
            File outputFile = new File(System.getProperty("java.io.tmpdir") + chart.getIdentifier() + ".pdf");
            outputFile.getParentFile().mkdirs(); //make sure the parent dirs are made before we start streaming files
            System.out.println("downloading file: " + chart.getName() + " from url: " + chart.getLocation());
            FileUtils.copyURLToFile(new URL(chart.getLocation()), outputFile, 10000, 10000);
            chart.setLocalLocation(outputFile.getPath());
            return chart;
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 4/21/2018 make the error handling a bit more elaborate
            return null;
        }
    }

    public static Chart downloadSingleChartHTTPS(Chart chart){
        // Create a new trust manager that trust all certificates
                TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
            };
            // Activate the new trust manager
            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            } catch (Exception e) {

            }
            // And as before now you can use URL and URLConnection
            URL url = null;
            try {
                System.out.println("url: " + chart.getLocation());
                url = new URL(chart.getLocation());
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();

                File outputFile = new File(System.getProperty("java.io.tmpdir") + chart.getIdentifier() + ".pdf");
                outputFile.getParentFile().mkdirs(); //make sure the parent dirs are made before we start streaming files

                System.out.println("now copping the stream to: " + outputFile);
                FileUtils.copyInputStreamToFile(is, outputFile);
                chart.setLocalLocation(outputFile.getPath());
                return chart;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        return chart;
    }



    public static void setAirportChartsFromDir(Airport airport, String path){
        airport.getCharts().forEach(chart -> chart.setLocalLocation(path + "\\" + chart.getIdentifier() + ".pdf"));
    }


}
