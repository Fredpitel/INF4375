package ca.uqam.projet.schema;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frederic.Pitel on 23/6/16.
 */

public class ListeFavorisSchema {
    private List<String> favoris;

    public ListeFavorisSchema(List<String> favoris) {
        this.favoris = favoris;
    }

    public ListeFavorisSchema() {
    }

    public List<String> getFavoris() {
        return favoris;
    }

    public void setFavoris(List<String> favoris) {
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
