package com.gregoryzuroff.bird;

/**
 * Created by gregoryzuroff on 12/2/17.
 */

public class Bird {
    public String birdName, obsName, zip, dateObs;

    public Bird(){}

    public Bird(String birdName, String obsName, String zip, String dateObs) {
        this.birdName = birdName;
        this.obsName = obsName;
        this.zip = zip;
        this.dateObs = dateObs;
    }
}
