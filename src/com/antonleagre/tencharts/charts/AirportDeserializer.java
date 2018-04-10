package com.antonleagre.tencharts.charts;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class AirportDeserializer implements JsonDeserializer<ArrayList<Airport>> {

    @Override
    public ArrayList<Airport> deserialize(JsonElement file, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        //quick notation notam: des.. : deserialized variable, ser.. : serialized variable
        ArrayList<Airport> desAirports = new ArrayList<>();
        JsonObject desFile = file.getAsJsonObject();

        Set<Map.Entry<String, JsonElement>> allAirports = desFile.entrySet();
        allAirports.forEach(jsonElement -> {
            JsonObject root = jsonElement.getValue().getAsJsonObject();
            Airport.ICAOCode desICAOCode = Airport.ICAOCode.valueOf(root.get("icao").getAsString()); //the valueOf method takes a string and pushes it to the enum value.
            String desName = root.get("name").getAsString();

            Set<Map.Entry<String, JsonElement>> serCharts = root.get("charts").getAsJsonObject().entrySet(); //charts in json
            ArrayList<Chart> desCharts = new ArrayList<>();    //charts in code

            serCharts.forEach(serChart -> { //loop through the serCharts and make desCharts
                String chartIdentifier = serChart.getKey();
                JsonObject chartFields = serChart.getValue().getAsJsonObject();
                String chartName = chartFields.get("name").getAsString();
                String chartLoc = chartFields.get("loc").getAsString();
                //Chart.Types chartType = Chart.Types.valueOf(chartFields.get("type").getAsString().toUpperCase());
                // TODO: 4/10/2018 figure out why this isn't working
                desCharts.add(new Chart(null, chartName, chartIdentifier, chartLoc));
            });

            Airport airport = new Airport(desICAOCode, desName, desCharts);
            desAirports.add(airport);
            System.out.println("Deserialized airport " + desICAOCode);
        });

        return desAirports;
    }
}
