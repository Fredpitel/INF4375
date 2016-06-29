package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by deasel on 2016-05-19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeaturesCollectionSchema {
    private FoodTruckCoordSchema geometry;
    private FoodTruckPropertiesSchema properties;

    public FeaturesCollectionSchema(){
    }

    public FeaturesCollectionSchema(FoodTruckPropertiesSchema properties, FoodTruckCoordSchema geometry){
        this.geometry = geometry;
        this.properties = properties;
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
