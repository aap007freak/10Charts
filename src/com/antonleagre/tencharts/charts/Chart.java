package com.antonleagre.tencharts.charts;

public class Chart {


    public enum Types{
        AERODROME,
        DEPARTURE,
        ARRIVAL,
        APPROACH
    }

    private Types chartType;
    private String name;
    private String identifier;
    private String location;
    private String local;

    public Chart(Types chartType, String name, String identifier, String location) {
        this.chartType = chartType;
        this.name = name;
        this.location = location;
        this.identifier = identifier;

        //check if we have the chart saved locally

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

    public String getLocalLocation(){
        return local;
    }

    public String getIdentifier() {
        return identifier;
    }
}
