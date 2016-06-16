
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
        task.execute();
    }
}
