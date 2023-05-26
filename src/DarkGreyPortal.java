package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;
public class DarkGreyPortal extends Portal {

    DarkGreyPortal(Game game) { super(game, "DARKGREY", 11); }

    public void putPortal(GGBackground bg, Location location, Portal portal) {
        Actor darkGreyPortal = new Actor("sprites/portalDarkGreyTile.png");
        portal.portals.add(darkGreyPortal);
        game.addActor(darkGreyPortal, location);
    }
}
