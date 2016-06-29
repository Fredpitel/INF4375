package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by deasel on 2016-06-13.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionArceauxSchema {

    public CollectionArceauxSchema(){
    }

    public CollectionArceauxSchema(List<ArceauxSchema> arceaux){
        this.arceaux = arceaux;
    }

    private List<ArceauxSchema> arceaux;
    
    public List<ArceauxSchema> getArceaux() {
        return arceaux;
    }

    public void addArceau(ArceauxSchema arceau){
        arceaux.add(arceau);
    }

    public void setArceaux(List<ArceauxSchema> arceaux) {
        this.arceaux = arceaux;
    }

    @Override
    public String toString() {
        String res="";
        for (Iterator<ArceauxSchema> i = arceaux.iterator(); i.hasNext();) {
            res+=i.next().toString()+"\n";
        }
        return res;
    }
}
