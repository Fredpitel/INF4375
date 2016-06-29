package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by deasel on 2016-05-19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodTruckCoordSchema {
    private double[] coordinates;

    public FoodTruckCoordSchema(){
    }

    public FoodTruckCoordSchema(double lat, double lon){
        this.coordinates = new double[]{lon, lat};
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return ""+coordinates[0]+" "+coordinates[1];
    }
}
