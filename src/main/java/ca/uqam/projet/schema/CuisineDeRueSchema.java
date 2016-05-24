package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by deasel on 2016-05-19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CuisineDeRueSchema {
    private String type;
    private ArrayList<FeaturesCollectionSchema> features;

    public  CuisineDeRueSchema(){
    }

    public CuisineDeRueSchema(String type, ArrayList<FeaturesCollectionSchema> features) {
        this.type = type;
        this.features = features;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<FeaturesCollectionSchema> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<FeaturesCollectionSchema> features) {
        this.features = features;
    }

    @Override
    public String toString() {
        String res="";
        for(int i=0;i<features.size();i++){
            res+="\n"+features.get(i);
        }
        return res;
    }
}
