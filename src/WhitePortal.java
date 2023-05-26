package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;
public class WhitePortal extends Portal {
    WhitePortal(Game game) {
        super(game, "WHITE", 8);
    }

    public void putPortal(GGBackground bg, Location location, Portal portal) {
        Actor whitePortal = new Actor("sprites/portalWhiteTile.png");
        portal.portals.add(whitePortal);
        game.addActor(whitePortal, location);
    }
}
