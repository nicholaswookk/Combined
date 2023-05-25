package src;

import ch.aplu.jgamegrid.Location;

public class Portal {

    private Location location;

    private String colour;

    String image;


    public Portal (String fileName, Location location) {

        this.location = location;
        this.image = fileName;

    }

    public Location getLocation () {
        return location;
    }







}
