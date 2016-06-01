package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by deasel on 2016-05-19.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class StationsSchema {

    private ArrayList<BixiSchema> stations;

    public StationsSchema(){
    }

    public ArrayList<BixiSchema> getStations(){
        return stations;
    }

    public void setStations(ArrayList<BixiSchema> stations) {
        this.stations = stations;
    }

}
