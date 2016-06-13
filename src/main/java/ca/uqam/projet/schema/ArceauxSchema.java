package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by deasel on 2016-06-13.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArceauxSchema {
    //URL: http://donnees.ville.montreal.qc.ca/dataset/c4dfdeb1-cdb7-44f4-8068-247755a56cc6/resource/78dd2f91-2e68-4b8b-bb4a-44c1ab5b79b6/download/supportvelosigs.csv
    @JsonProperty("EMPL_ID")
    private double empl_id;
    @JsonProperty("LONG")
    private double lo;
    @JsonProperty("LAT")
    private double la;

    public double getEmpl_id() {
        return empl_id;
    }

    public void setEmpl_id(double empl_id) {
        this.empl_id = empl_id;
    }

    public double getLo() {
        return lo;
    }

    public void setLo(double lo) {
        this.lo = lo;
    }

    public double getLa() {
        return la;
    }

    public void setLa(double la) {
        this.la = la;
    }
}
