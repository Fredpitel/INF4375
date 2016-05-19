package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by deasel on 2016-05-16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BixiSchema {
    private int id;
    private String name;
    private String terminalId;
    private int stationState;
    private boolean isBlocked;
    private boolean isSuspended;
    private boolean isOutOfOrder;
    private int msSincelastUpdate;
    private int msLastCommunitacion;
    private String bk;
    private String bl;
    private float latitude;
    private float longitude;
    private int availablePlaces;
    private int unavailablePlaces;
    private int availableBikes;
    private int unavailableBikes;

    public BixiSchema(){

    }

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }
}
