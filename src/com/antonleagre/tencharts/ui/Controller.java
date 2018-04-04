package com.antonleagre.tencharts.ui;

import com.antonleagre.tencharts.charts.Airport;
import com.antonleagre.tencharts.charts.AirportDeserializer;
import com.antonleagre.tencharts.charts.PDFDownloader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
public class Controller implements Initializable {


    @FXML
    private Tab tab1;

    @FXML
    private Tab tab2;

    private Airport brussels;

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("NOW INITIALIZING PDF COMPONENTS");

        PDFViewer viewer1 = new PDFViewer();
        tab1.setContent(viewer1);

        PDFViewer viewer2 = new PDFViewer();
        tab2.setContent(viewer2);

        GsonBuilder gsonFactory = new GsonBuilder();
        AirportDeserializer deserializer = new AirportDeserializer();
        gsonFactory.registerTypeAdapter(Airport.class, deserializer);
        Gson desGson = gsonFactory.create();

        try {
            brussels = desGson.fromJson(new JsonParser().parse(new JsonReader(new FileReader("locs.json"))).getAsJsonObject().get("EBBR").toString(), Airport.class);
            //PDFDownloader.downloadAirportCharts(brussels);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("DONE!");

    }

    @FXML
    private void downloadClicked(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File outputDir = directoryChooser.showDialog(null);
        PDFDownloader.downloadAirportCharts(brussels, outputDir);
    }

}
