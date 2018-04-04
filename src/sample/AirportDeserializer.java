package sample;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class AirportDeserializer implements JsonDeserializer<Airport> {

    @Override
    public Airport deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        //quick notation notam: des.. : deserialized variable, ser.. : serialized variable
        JsonObject root = jsonElement.getAsJsonObject();

        Airport.ICAOCode desICAOCode = Airport.ICAOCode.valueOf(root.get("icao").getAsString()); //the valueOf method takes a string and pushes it to the enum value.
        String desName = root.get("name").getAsString();

        Set<Map.Entry<String, JsonElement>> serCharts = root.get("charts").getAsJsonObject().entrySet(); //charts in json
        ArrayList<Chart> desCharts = new ArrayList<>();    //charts in code

        serCharts.forEach(serChart -> {

            JsonObject chartFields = serChart.getValue().getAsJsonObject();
            String chartName = chartFields.get("name").getAsString();
            String chartLoc = chartFields.get("loc").getAsString();
            Chart.Types chartType = Chart.Types.valueOf(chartFields.get("type").getAsString().toUpperCase());

            desCharts.add(new Chart(chartType, chartName, chartLoc));
        });

        Airport airport = new Airport(desICAOCode, desName, desCharts);

        System.out.println("Deserialized airport " + desICAOCode);
        return airport;
    }
}
