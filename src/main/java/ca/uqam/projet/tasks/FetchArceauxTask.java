package ca.uqam.projet.tasks;

import ca.uqam.projet.repositories.ArceauxRepository;
import ca.uqam.projet.schema.ArceauxSchema;
import ca.uqam.projet.schema.CollectionArceauxSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@Component
public class FetchArceauxTask {

    private static final Logger log = LoggerFactory.getLogger(FetchArceauxTask.class);
    private static final String csvUrl = "http://donnees.ville.montreal.qc.ca/dataset/c4dfdeb1-cdb7-44f4-8068-247755a56cc6/resource/78dd2f91-2e68-4b8b-bb4a-44c1ab5b79b6/download/supportvelosigs.csv";

    @Autowired
    private ArceauxRepository repository;

    @Scheduled(cron="0 0 0 1 * ?")
    public void execute() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            CollectionArceauxSchema arceauxSchema = getCollectionArceaux(csvUrl);
            repository.truncate();
            arceauxSchema.getArceaux().stream().forEach(repository::insert);
        } catch (Exception e){
            System.out.println(e.getStackTrace());
        }
    }

    public CollectionArceauxSchema getCollectionArceaux(String urlToRead) throws Exception{
        CollectionArceauxSchema res=new CollectionArceauxSchema();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"ISO_8859_1"));
        String line;
        boolean isFirstLine=true;
        while ((line = rd.readLine()) != null) {
            if(!isFirstLine) {
                int fieldCounter=0;
                String lineSplit[]=line.split(",");
                res.addArceau(new ArceauxSchema(Double.parseDouble(lineSplit[lineSplit.length-1]),Double.parseDouble(lineSplit[lineSplit.length-2])));
            } else {
                isFirstLine = false;
            }
        }
        rd.close();
        return res;
    }
}
