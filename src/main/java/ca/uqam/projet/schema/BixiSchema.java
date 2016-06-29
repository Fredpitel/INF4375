package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    public  BixiSchema(int nbBikes, int nbEmptyDocks, double lat, double lon){
        ba = nbBikes;
        da = nbEmptyDocks;
        la = lat;
        lo = lon;
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

    @Override
    public String toString() {
        return "Latitude:"+la+" Longitude:"+lo;
    }
}
