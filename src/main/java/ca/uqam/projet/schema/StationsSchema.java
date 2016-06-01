package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.*;
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

    /*public boolean getSchemeSusended(){
        return schemeSusended;
    }

    public long getTimestamp(){
        return timestamp;
    }
    */
    public void setStations(ArrayList<BixiSchema> stations) {
        this.stations = stations;
    }

    /*public void setschemeSusended(boolean schemeSusended) {
        this.schemeSusended = schemeSusended;
    }

    public void setTimestamp(long timeStamp) {
        this.timestamp = timeStamp;
    }

    @Override
    public String toString(){
        String res="Stations:";
        res+=timestamp;
        for(int i=0;i<stations.size();i++){
            res+="\n"+stations.get(i);
        }
        return res;
    }
    */
}
