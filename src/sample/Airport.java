package sample;

import java.util.ArrayList;

public class Airport {

    public enum ICAOCode {
        EBBR,
        ELLX
    }

    private ICAOCode code;
    private String name;

    private ArrayList<Chart> charts;

    public Airport(ICAOCode code, String name, ArrayList<Chart> charts) {
        this.code = code;
        this.name = name;
        this.charts = charts;
    }

    public ICAOCode getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Chart> getCharts() {
        return charts;
    }
}
