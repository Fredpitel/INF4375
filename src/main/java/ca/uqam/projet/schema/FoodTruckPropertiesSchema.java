package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by deasel on 2016-05-19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodTruckPropertiesSchema {
    private String name;
    private String description;
    private String Date;
    private String Heure_debut;
    private String Heure_fin;
    private String Lieu;
    private String Camion;
    private String Truckid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getHeure_debut() {
        return Heure_debut;
    }

    public void setHeure_debut(String Heure_debut) {
        Heure_debut = Heure_debut;
    }

    public String getHeure_fin() {
        return Heure_fin;
    }

    public void setHeure_fin(String Heure_fin) {
        Heure_fin = Heure_fin;
    }

    public String getLieu() {
        return Lieu;
    }

    public void setLieu(String lieu) {
        Lieu = lieu;
    }

    public String getCamion() {
        return Camion;
    }

    public void setCamion(String Camion) {
        Camion = Camion;
    }

    public String getTruckid() {
        return Truckid;
    }

    public void setTruckid(String Truckid) {
        Truckid = Truckid;
    }

    @Override
    public String toString() {
        return "De "+Heure_debut+" a "+Heure_fin+", "+Camion;
    }
}
