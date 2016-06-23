package ca.uqam.projet.schema;

import java.util.ArrayList;

/**
 * Created by Frederic.Pitel on 23/6/16.
 */

public class ListeFavorisSchema {
    private ArrayList<String> favoris;

    public ListeFavorisSchema(ArrayList<String> favoris) {
        this.favoris = favoris;
    }

    public ListeFavorisSchema() {
    }

    public ArrayList<String> getFavoris() {
        return favoris;
    }

    public void setFavoris(ArrayList<String> favoris) {
        this.favoris = favoris;
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < favoris.size(); i++) {
            res += "\n" + favoris.get(i);
        }
        return res;
    }
}
