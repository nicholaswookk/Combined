package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;

public class Portal extends Actor {
    private Game game;
    private String colour;
    // value of a from toInt in pacManGameGrid
    private String spriteFilePath;

    public Portal (Game game, String spriteFilePath, String colour) {
        super(spriteFilePath);
        this.spriteFilePath = spriteFilePath;
        this.game = game;
        this.colour = colour;
    }
    public String getColour() {
        return colour;
    }
}
