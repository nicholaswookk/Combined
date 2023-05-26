package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;
public class DarkGoldPortal extends Portal {
    DarkGoldPortal(Game game) {
        super(game, "DARKGOLD", 10);
    }

    public void putPortal(GGBackground bg, Location location, Portal portal) {
        Actor darkGoldPortal = new Actor("sprites/portalDarkGoldTile.png");
        portal.portals.add(darkGoldPortal);
        game.addActor(darkGoldPortal, location);
    }
}
