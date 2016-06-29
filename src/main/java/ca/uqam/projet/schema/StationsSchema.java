package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deasel on 2016-05-19.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class StationsSchema {

    private List<BixiSchema> stations;

    public StationsSchema(){
    }

    public StationsSchema(List<BixiSchema> stations){
        this.stations = stations;
    }

    public List<BixiSchema> getStations(){
        return stations;
    }

    public void setStations(List<BixiSchema> stations) {
        this.stations = stations;
    }

    @Override
    public String toString() {
        String res="";
        for (BixiSchema bixi:stations) {
            res+=bixi.toString();
        }
        return res;
    }
}
