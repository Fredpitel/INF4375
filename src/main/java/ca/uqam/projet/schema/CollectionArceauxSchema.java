package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by deasel on 2016-06-13.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionArceauxSchema {

    public CollectionArceauxSchema(){
        arceaux=new ArrayList<>();
    }

    private ArrayList<ArceauxSchema> arceaux;  
    
    public ArrayList<ArceauxSchema> getArceaux() {
        return arceaux;
    }

    public void addArceau(ArceauxSchema arceau){
        arceaux.add(arceau);
    }

    public void setArceaux(ArrayList<ArceauxSchema> arceaux) {
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
