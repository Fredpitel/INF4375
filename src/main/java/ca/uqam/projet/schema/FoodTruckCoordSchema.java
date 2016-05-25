package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by deasel on 2016-05-19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodTruckCoordSchema {
    private String type;
    private double[] coordinates;

    public FoodTruckCoordSchema(){
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public String getType() {
        return type;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ""+coordinates[0]+" "+coordinates[1];
    }
}
