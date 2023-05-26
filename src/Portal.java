package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;

public class Portal extends Actor {
    protected Game game;
    //may need to change to protected
    protected ArrayList<Actor> portals = new ArrayList<Actor>();
    private String colour;

    // value of a from toInt in pacManGameGrid
    private int identifierIndex;
    public Portal (Game game, String colour, int identifierIndex) {
        this.game = game;
        this.colour = colour;
        this.identifierIndex = identifierIndex;
    }
     public void putPortal(GGBackground bg, Location location) {
        System.out.println("portal has no colour yet!");
     }

    public String getColour() {
        return colour;
    }
    public int getIdentifierIndex(){
        return identifierIndex;
    }







}
