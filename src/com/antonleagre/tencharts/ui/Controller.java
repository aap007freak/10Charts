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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sun.reflect.generics.tree.Tree;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
        System.out.println("NOW INITIALIZING AIRPORTS AND SUCH");
        //first were checking for updates and new files from the web
        update();
        //secondly we want to deserialize the new/old locs.json file into an arraylist of airports, 
        //so that's what were doing here (using Gson)
        GsonBuilder gsonFactory = new GsonBuilder();
        AirportDeserializer deserializer = new AirportDeserializer();
        gsonFactory.registerTypeAdapter(ArrayList.class, deserializer); // TODO: 4/14/2018 elaborate error checking pls 
        Gson desGson = gsonFactory.create();

        try {
            woohoo = desGson.fromJson(new JsonReader(new FileReader("locs.json")), ArrayList.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Were setting up the treeview with the airports we have deserialized.
        TreeItem<Chart> rootItem = new TreeItem<>(null); //it takes a chart as input but we just make it null bc it's a container for other charts.
        rootItem.setExpanded(true);
        for (Airport ap : woohoo){ //for all airports we make another root container item
            TreeItem<Chart> airportRootItem = new TreeItem<Chart>(new Chart(null, ap.getCode().toString(), null, null)); //this time we do want to create a placeholder chart just to get the airports name displaying in the treeview.
            for (Chart chart : ap.getCharts()){
                TreeItem<Chart> item = new TreeItem<>(chart); //adding the charts.
                airportRootItem.getChildren().add(item);
            }
            rootItem.getChildren().add(airportRootItem);
        }
        treeView.setRoot(rootItem);

        //now we are adding a listener to the treeview to detect when a user clicks on a certain chart.
        treeView.getSelectionModel().selectedItemProperty().addListener((obv, old, neww) ->{
        //neww is the chart that is clicked on btw.
            if(neww.isLeaf()){ //checking if the item you've clicked on is a chart and not a placeholder
                PDFViewer viewer = new PDFViewer(); //make a tab/ viewer
                Tab chartTab = new Tab();
                chartTab.setContent(viewer);
                chartTab.setText(neww.getValue().getName());
                chartTab.setClosable(true);
                tabPane.getTabs().add(chartTab);
                viewer.openChart(neww.getValue());
                tabPane.getSelectionModel().select(chartTab); //set it as the selected tab.
            }
        });
        System.out.println("DONE!");

    }

    @FXML
    private void downloadClicked(){
        //open a new window, the controller of that window will do the rest of the logic.
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("downloadprompt.fxml"));
            Parent root = loader.load();
            // Get the Controller from the FXMLLoader, we do this because we want to send our airport list so it knows what to download.
            DownloadPromptController controller = loader.getController();
            Scene scene = new Scene(root, 400, 200);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            // Set data in the controller (see last comment)
            controller.setData(woohoo);
        }
        catch (IOException e) {
            e.printStackTrace();
        }


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
        //1. dwonlaod a new locs.json file from the web if there is one
        //2. periodically ask if you want to update the new charts to the new json file.
        //2. check the previous download location for charts and update the charts. This should be done after the locs.json file is read so this is redundant now.

    }
}
