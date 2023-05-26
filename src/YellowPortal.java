package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;
public class YellowPortal extends Portal {
    YellowPortal(Game game) {
        super(game, "YELLOW", 9);
    }

    public void putPortal(GGBackground bg, Location location, Portal portal) {
        Actor yellowPortal = new Actor("sprites/portalYellowTile.png");
        portal.portals.add(yellowPortal);
        game.addActor(yellowPortal, location);
    }
}
