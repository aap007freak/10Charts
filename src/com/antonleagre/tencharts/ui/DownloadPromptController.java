package com.antonleagre.tencharts.ui;

import com.antonleagre.tencharts.charts.Airport;
import com.antonleagre.tencharts.charts.PDFDownloader;
import com.jfoenix.controls.JFXProgressBar;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

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
        System.out.println("Initialized window controller");
    }
    public void setData(ArrayList<Airport> data) {
        this.airports = data;
        
        
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File outputDir = directoryChooser.showDialog(null); // TODO: 4/10/2018 work out why this gives nullpointers elsewhere 

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateProgress(0, airports.size()); //set the progress to 0 to start
                for (int i = 0; i < airports.size(); i++) {
                    updateMessage(airports.get(i).getName()); //Set the current task message to the airport its downloading the charts for
                    PDFDownloader.downloadAirportCharts(airports.get(i), outputDir);
                    updateProgress(i + 1, airports.size());
                }
                return null;
            }

        };
        task.setOnSucceeded(e -> {
            Stage stage = (Stage) progressBar.getScene().getWindow();
            stage.close();
        });
        progressBar.progressProperty().bind(task.progressProperty());
        label.textProperty().bind(task.messageProperty());

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

}

