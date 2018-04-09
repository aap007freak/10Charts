package com.antonleagre.tencharts.ui;

import com.antonleagre.tencharts.charts.Airport;
import com.antonleagre.tencharts.charts.AirportDeserializer;
import com.antonleagre.tencharts.charts.Chart;
import com.antonleagre.tencharts.charts.PDFDownloader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTreeView;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import sun.reflect.generics.tree.Tree;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class Controller implements Initializable {


    @FXML
    private JFXTabPane tabPane;

    @FXML
    private JFXTreeView<Chart> treeView;

    private ArrayList<Airport> woohoo;

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("NOW INITIALIZING PDF COMPONENTS");

        GsonBuilder gsonFactory = new GsonBuilder();
        AirportDeserializer deserializer = new AirportDeserializer();
        gsonFactory.registerTypeAdapter(ArrayList.class, deserializer);
        Gson desGson = gsonFactory.create();

        try {
            woohoo = desGson.fromJson(new JsonReader(new FileReader("locs.json")), ArrayList.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(woohoo);
        //setup treeview
        TreeItem<Chart> rootItem = new TreeItem<>(null);
        rootItem.setExpanded(true);
        for (Airport ap : woohoo){
            TreeItem<Chart> airportRootItem = new TreeItem<Chart>(new Chart(null, ap.getCode().toString(), null, null));
            for (Chart chart : ap.getCharts()){
                TreeItem<Chart> item = new TreeItem<>(chart);
                airportRootItem.getChildren().add(item);
            }
            rootItem.getChildren().add(airportRootItem);
        }
        treeView.setRoot(rootItem);
        treeView.getSelectionModel().selectedItemProperty().addListener((obv, old, neww) ->{

            if(neww.isLeaf()){ //checking if the item you've clicked on is a chart and not a placeholder
                PDFViewer viewer = new PDFViewer();
                Tab chartTab = new Tab();
                chartTab.setContent(viewer);
                chartTab.setText(neww.getValue().getName());
                chartTab.setClosable(true);
                tabPane.getTabs().add(chartTab);
                viewer.openChart(neww.getValue());
            }
        });
        System.out.println("DONE!");

    }

    @FXML
    private void downloadClicked(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File outputDir = directoryChooser.showDialog(tabPane.getScene().getWindow()); //convoluted way cause javafx is dumb sometimes
        woohoo.forEach(ap -> PDFDownloader.downloadAirportCharts(ap, outputDir));
    }

    @FXML
    private void updateClicked(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File outputDir = directoryChooser.showDialog(tabPane.getScene().getWindow()); //convoluted way cause javafx is dumb sometimes
        woohoo.forEach(airport -> PDFDownloader.setAirportChartsFromDir(airport, outputDir + "\\" + airport.getCode()));
    }

    @FXML
    private void checkButtonClicked(){
        woohoo.forEach(ap -> ap.getCharts().forEach(ch -> System.out.println(ch.getLocalLocation())));
    }

    private void update(){
        //1. check if theres a new json file on the web. and download if needed. then look for new /updated airports and download their charts
        //2. check if the local charts are the same as the new charts, and if they are set the local location. If they arent -> Download them

    }

}
