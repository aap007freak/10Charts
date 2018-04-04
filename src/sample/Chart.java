package sample;

public class Chart {


    public enum Types{
        AERODROME,
        DEPARTURE,
        ARRIVAL,
        APPROACH
    }

    private Types chartType;
    private String name;
    private String location;

    public Chart(Types chartType, String name, String location) {
        this.chartType = chartType;
        this.name = name;
        this.location = location;
    }


    public Types getChartType() {
        return chartType;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
