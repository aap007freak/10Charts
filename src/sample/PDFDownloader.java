package sample;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class PDFDownloader {

    public static void downloadAirportCharts(Airport airport){
        airport.getCharts().forEach(chart -> {
            try {
                downloadChart(chart);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // TODO: 4/4/2018 make the user choose the output folder
    private static void downloadChart(Chart chart) throws IOException {
        ReadableByteChannel rbc = Channels.newChannel(new URL(chart.getLocation()).openStream());
        FileOutputStream fos = new FileOutputStream("downloaded/" + chart.getName() + ".pdf");
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }

}
