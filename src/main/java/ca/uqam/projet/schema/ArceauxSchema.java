package ca.uqam.projet.schema;

/**
 * Created by deasel on 2016-06-13.
 */

public class ArceauxSchema {
    private double lo;
    private double la;

    public ArceauxSchema(double longi, double lati){
        lo=longi;
        la=lati;
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

    @Override
    public String toString() {
        return "Longitude:"+lo+" Latitude:"+la;
    }
}
