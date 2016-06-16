
package ca.uqam.projet;

import org.springframework.stereotype.Component;
import ca.uqam.projet.tasks.*;

/**
 * Created by deasel on 2016-05-19.
 */

@Component
public class test {

    public static void main(String args[]){
        FetchArceauxTask task = new FetchArceauxTask();
        try {
            System.out.println(task.getCollectionArceaux("http://donnees.ville.montreal.qc.ca/dataset/c4dfdeb1-cdb7-44f4-8068-247755a56cc6/resource/78dd2f91-2e68-4b8b-bb4a-44c1ab5b79b6/download/supportvelosigs.csv").getArceaux().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
