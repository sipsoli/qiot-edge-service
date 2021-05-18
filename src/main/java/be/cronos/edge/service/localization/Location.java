package be.cronos.edge.service.localization;

import lombok.Value;

@Value
public class Location {

    String lat;
    String lon;

    public double getLongitude(){
        return Double.parseDouble(this.lon);
    }

    public double getLatitude(){
        return Double.parseDouble(this.lat);
    }
}
