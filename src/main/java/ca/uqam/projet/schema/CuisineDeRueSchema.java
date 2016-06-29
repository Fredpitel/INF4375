package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deasel on 2016-05-19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CuisineDeRueSchema {
    private List<FeaturesCollectionSchema> features;

    public CuisineDeRueSchema(){
    }

    public CuisineDeRueSchema(List<FeaturesCollectionSchema> features) {
        this.features = features;
    }

    public List<FeaturesCollectionSchema> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeaturesCollectionSchema> features) {
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
