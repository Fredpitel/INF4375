package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by deasel on 2016-05-19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodTruckPropertiesSchema {
    @JsonProperty("Date")
    private String date;
    @JsonProperty("Heure_debut")
    private String heure_debut;
    @JsonProperty("Heure_fin")
    private String heure_fin;
    @JsonProperty("Lieu")
    private String lieu;
    @JsonProperty("Camion")
    private String camion;

    public String getDate() {
        return date;
    }

    public void setDate(String Date) {
        date = Date;
    }

    public String getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(String Heure_debut) { heure_debut = Heure_debut;
    }

    public String getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(String Heure_fin) {
        heure_fin = Heure_fin;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String Lieu) {
        lieu = Lieu;
    }

    public String getCamion() {
        return camion;
    }

    public void setCamion(String Camion) { camion = Camion; }

    @Override
    public String toString() {
        return "De "+heure_debut+" a "+heure_fin+", le " +date+ ". " +camion;
    }
}
