package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
public class Controller implements Initializable {


    @FXML
    private BorderPane bPane;

    @FXML
    private Tab tab1;

    @FXML
    private Tab tab2;

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("NOW INITIALIZING PDF COMPONENTS");

        //sample.PDFViewer viewer1 = new sample.PDFViewer();
       // tab1.setContent(viewer1);

        //sample.PDFViewer viewer2 = new sample.PDFViewer();
       // tab2.setContent(viewer2);
        GsonBuilder gsonFactory = new GsonBuilder();
        AirportDeserializer deserializer = new AirportDeserializer();
        gsonFactory.registerTypeAdapter(Airport.class, deserializer);
        Gson desGson = gsonFactory.create();

        try {
            Airport brussels = desGson.fromJson(new JsonParser().parse(new JsonReader(new FileReader("locs.json"))).getAsJsonObject().get("EBBR").toString(), Airport.class);
            PDFDownloader.downloadAirportCharts(brussels);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("DONE!");

    }

}
