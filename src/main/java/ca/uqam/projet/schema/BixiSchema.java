package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;

/**
 * Created by deasel on 2016-05-16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BixiSchema {
    private double la;
    private double lo;
    private int ba;
    private int da;

    public BixiSchema(){
    }

    public void setLa(double la) {
        this.la = la;
    }

    public void setLo(double lo) {
        this.lo = lo;
    }

    public void setBa(int ba) {
        this.ba = ba;
    }

    public void setDa(int da) {
        this.da = da;
    }

    public double getLa() {

        return la;
    }

    public double getLo() {
        return lo;
    }

    public int getBa() {
        return ba;
    }

    public int getDa() {
        return da;
    }

/*@Override
    public String toString() {
        return ""+s+" has "+ba+" available";
    }
    */
}
