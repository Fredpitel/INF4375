package ca.uqam.projet.schema;

import java.util.ArrayList;

/**
 * Created by deasel on 2016-05-19.
 */
public class CuisineDeRueSchema {
    private String type;
    private ArrayList<FeaturesFoodTruckSchema> features;

    public CuisineDeRueSchema() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<FeaturesFoodTruckSchema> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<FeaturesFoodTruckSchema> features) {
        this.features = features;
    }
}
