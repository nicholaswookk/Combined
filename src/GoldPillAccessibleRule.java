package src;

import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GoldPillAccessibleRule extends MapRule {

    private Location pacmanLocation;
    private ArrayList<Location> visitedList = new ArrayList<Location>();
    private ArrayList<Location> unaccessiblePills = new ArrayList<Location>();
    private ArrayList<Location> unaccessibleGolds = new ArrayList<Location>();
    private ArrayList<Portal> portals = new ArrayList<Portal>();
    private int dRow[] = {-1, 0, 1, 0};
    private int dCol[] = {0, -1, 0, 1};


    public GoldPillAccessibleRule(String gameGrid, String fileName, ArrayList<Portal> portals) {
        super(gameGrid, fileName);
        this.portals = portals;
    }

    public boolean isValid() {

        for (int j = 0; j < getNbVertCells(); j++) {
            for (int k = 0; k < getNbHorzCells(); k++) {
                if (getMazeArray()[j][k] == 'f'){
                    pacmanLocation = new Location(j,k);
                }
            }
        }

        Queue<Location> q = new LinkedList<>();
        q.add(pacmanLocation);
        visitedList.add(pacmanLocation);

        while(!q.isEmpty()){
            Location cell = q.peek();
            q.remove();

            // find neighbor location
            for (int i = 0; i < 360; i += 90){
                String portalColour = "";
                boolean portalFound = false;

                Location nextCell = new Location();
                nextCell.x = cell.getX() + dRow[i/90];
                nextCell.y = cell.getY() + dCol[i/90];

                // portal implementation here
                for (Portal portal : portals) {
                    if (portal.getLocation().equals(nextCell)) {
                        portalColour = portal.getColour();
                        break;
                    }
                }
                for (Portal portal : portals) {
                    if (portal.getColour().equals(portalColour) && !(portal.getLocation().equals(nextCell))) {
                        visitedList.add(nextCell);
                        nextCell = portal.getLocation();
                        portalFound = true;
                        break;
                    }
                }

                // add valid moves to visited list
                if ((canMove(nextCell) || portalFound) && !isVisited(nextCell)){
                    q.add(nextCell);
                    visitedList.add(nextCell);
                }
            }
        }

        for (int j = 0; j < getNbVertCells(); j++) {
            for (int k = 0; k < getNbHorzCells(); k++) {
                if (mazeArray[j][k] == 'c' && !visitedList.contains(new Location(k, j))){
                    unaccessiblePills.add(new Location(k, j));
                }
                if (mazeArray[j][k] == 'd' && !visitedList.contains(new Location(k, j))){
                    unaccessibleGolds.add(new Location(k, j));
                }
            }
        }

        if (unaccessiblePills.size() > 0 || unaccessibleGolds.size() > 0){
            return false;
        }
        return true;
    }

    public String errorString() {
        StringBuilder pillLocations = new StringBuilder();
        int pillCount = 0;
        for (Location pillLocation: unaccessiblePills){
            pillCount++;
            if (pillCount > 1){
                pillLocations.append("; ");
            }
            pillLocations.append("(" + (pillLocation.getX()+1) + "," + (pillLocation.getY()+1) + ")");
        }
        String pillsErrorString = "[Level " + getFileName() + " - Pill not accessible: " + pillLocations + "]";

        StringBuilder goldLocations = new StringBuilder();
        int goldCount = 0;
        for (Location goldLocation: unaccessibleGolds){
            goldCount++;
            if (goldCount > 1){
                goldLocations.append("; ");
            }
            goldLocations.append("(" + (goldLocation.getX()+1) + "," + (goldLocation.getY()+1) + ")");
        }
        String goldsErrorString = "[Level " + getFileName() + " - Gold not accessible: " + goldLocations + "]";

        StringBuilder finalErrorString = new StringBuilder();

        if (pillCount > 0) {
            finalErrorString.append(pillsErrorString);
            if (goldCount > 0) {
                finalErrorString.append("\n");
                finalErrorString.append(goldsErrorString);
            }
        }
        else if (goldCount > 0) {
            finalErrorString.append(goldsErrorString);
        }

        return String.valueOf(finalErrorString);
    }
    
    private boolean canMove(Location location)
    {
        if (location.getX() >= getNbHorzCells() || location.getX() < 0 || location.getY() >= getNbVertCells() ||
                location.getY() < 0 || getMazeArray()[location.getY()][location.getX()] == 'b')
            return false;
        else {
            return true;
        }
    }

    private boolean isVisited(Location location)
    {
        for (Location loc : visitedList)
            if (loc.equals(location))
                return true;
        return false;
    }

}
