package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by deasel on 2016-05-19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeaturesCollectionSchema {
    private String type;
    private FoodTruckCoordSchema geometry;
    private FoodTruckPropertiesSchema properties;

    public FeaturesCollectionSchema(){
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FoodTruckCoordSchema getGeometry() {
        return geometry;
    }

    public void setGeometry(FoodTruckCoordSchema geometry) {
        this.geometry = geometry;
    }

    public FoodTruckPropertiesSchema getProperties() {
        return properties;
    }

    public void setProperties(FoodTruckPropertiesSchema properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return ""+properties+" is at "+geometry;
    }
}
