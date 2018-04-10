package com.antonleagre.tencharts.ui;

import com.antonleagre.tencharts.charts.Airport;
import com.antonleagre.tencharts.charts.PDFDownloader;
import com.jfoenix.controls.JFXProgressBar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.DirectoryChooser;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DownloadPromptController implements Initializable{

    @FXML
    private JFXProgressBar progressBar;
    @FXML
    private Label label;

    private ArrayList<Airport> airports;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initialized window controller, starting downloading now");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File outputDir = directoryChooser.showDialog(progressBar.getScene().getWindow()); //convoluted way cause javafx is dumb sometimes
        for (int i = 0; i < airports.size(); i++) {
            PDFDownloader.downloadAirportCharts(airports.get(i), outputDir);
            progressBar.setProgress(i / airports.size());
        }
    }
    public void setData(ArrayList<Airport> data) {
        this.airports = data;
    }

}
