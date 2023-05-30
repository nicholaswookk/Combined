// PacActor.java
// Used for PacMan
package src;

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.util.*;

public class PacActor extends Actor implements GGKeyRepeatListener
{
  private static final int nbSprites = 4;
  private int idSprite = 0;
  private int nbPills = 0;
  private int score = 0;
  private Game game;
  private ArrayList<Location> visitedList = new ArrayList<Location>();
  private ArrayList<Location> parentCellList = new ArrayList<Location>();
  private List<String> propertyMoves = new ArrayList<>();
  private int propertyMoveIndex = 0;
  private final int listLength = 10000;
  private int seed;

  private Random randomiser = new Random();
  public PacActor(Game game)
  {
    super(true, "sprites/pacpix.gif", nbSprites);  // Rotatable
    this.game = game;
  }
  private boolean isAuto = false;

  public void setAuto(boolean auto) {
    isAuto = auto;
  }


  public void setSeed(int seed) {
    this.seed = seed;
    randomiser.setSeed(seed);
  }

  public void keyRepeated(int keyCode)
  {
    if (isAuto) {
      return;
    }
    if (isRemoved())  // Already removed
      return;
    Location next = null;
    switch (keyCode)
    {
      case KeyEvent.VK_LEFT:
        next = getLocation().getNeighbourLocation(Location.WEST);
        setDirection(Location.WEST);
        break;
      case KeyEvent.VK_UP:
        next = getLocation().getNeighbourLocation(Location.NORTH);
        setDirection(Location.NORTH);
        break;
      case KeyEvent.VK_RIGHT:
        next = getLocation().getNeighbourLocation(Location.EAST);
        setDirection(Location.EAST);
        break;
      case KeyEvent.VK_DOWN:
        next = getLocation().getNeighbourLocation(Location.SOUTH);
        setDirection(Location.SOUTH);
        break;
    }

    if (next != null && canMove(next))
    {
      setActorLocation(next);
      eatPill(next);
    }
  }

  public void act()
  {
    show(idSprite);
    idSprite++;
    if (idSprite == nbSprites)
      idSprite = 0;

    if (isAuto) {
      moveInAutoMode();
    }
    this.game.getGameCallback().pacManLocationChanged(getLocation(), score, nbPills);
  }

  private void moveInAutoMode() {

    // Directional arrays left, up, right, down
    int dRow[] = {-1, 0, 1, 0};
    int dCol[] = {0, -1, 0, 1};

    ArrayList<Portal> portals = game.getPortals();
    Queue<Location> q = new LinkedList<>();
    q.add(getLocation());
    addVisitedList(getLocation());
    parentCellList.add(getLocation());

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
            addVisitedList(nextCell);
            parentCellList.add(cell);
            nextCell = portal.getLocation();
            portalFound = true;
            break;
          }
        }

        // add valid moves to visited list
        if ((canMove(nextCell) || portalFound) && !isVisited(nextCell)){
          q.add(nextCell);
          addVisitedList(nextCell);
          parentCellList.add(cell);
          Color c = getBackground().getColor(nextCell);
          // check to see if pill is found
          if (c.equals(Color.white) || c.equals(Color.yellow)){
            Location moveCell = findFirstMove(nextCell);

            for (Portal portal : portals) {
              if (moveCell.equals(portal.getLocation())) {
                setDirection(findFirstDirection(getLocation(), findCorrespondingPortalLocation(moveCell)));
                setLocation(moveCell);
                visitedList.clear();
                parentCellList.clear();
                return;
              }
            }
            setDirection(findFirstDirection(getLocation(), moveCell));
            setActorLocation(moveCell);
            eatPill(moveCell);
            visitedList.clear();
            parentCellList.clear();
            return;
          }
        }
      }
    }
  }

  private Location findFirstMove(Location location){
    int index = visitedList.indexOf(location);
    if (parentCellList.get(index).equals(getLocation())){
      return location;
    }
    else {
      return (findFirstMove(parentCellList.get(index)));
    }
  }

  private Location findCorrespondingPortalLocation(Location location){
    ArrayList<Portal> portals = game.getPortals();
    String portalColour = "";
    for (Portal portal : portals) {
      if (portal.getLocation().equals(location)) {
        portalColour = portal.getColour();
        break;
      }
    }
    for (Portal portal : portals) {
      if (portal.getColour().equals(portalColour) && !(portal.getLocation().equals(location))) {
        return portal.getLocation();
      }
    }
    return null;
  }

  private int findFirstDirection(Location cell, Location nextCell){
    if (nextCell.getX() > cell.getX()){
      return 0;
    }
    else if (nextCell.getY() > cell.getY()) {
      return 90;
    }
    else if (nextCell.getX() < cell.getX()) {
      return 180;
    }
    else return 270;
  }

  private void addVisitedList(Location location)
  {
    visitedList.add(location);
    if (visitedList.size() == listLength)
      visitedList.remove(0);
  }

  private boolean isVisited(Location location)
  {
    for (Location loc : visitedList)
      if (loc.equals(location))
        return true;
    return false;
  }

  private boolean canMove(Location location)
  {
    Color c = getBackground().getColor(location);
    if ( c.equals(Color.gray) || location.getX() >= game.getNumHorzCells()
            || location.getX() < 0 || location.getY() >= game.getNumVertCells() || location.getY() < 0)
      return false;
    else
      return true;
  }

  public int getNbPills() {
    return nbPills;
  }

  private void eatPill(Location location)
  {
    Color c = getBackground().getColor(location);
    if (c.equals(Color.white))
    {
      nbPills++;
      score++;
      getBackground().fillCell(location, Color.lightGray);
      game.getGameCallback().pacManEatPillsAndItems(location, "pills");
    } else if (c.equals(Color.yellow)) {
      nbPills++;
      score+= 5;
      getBackground().fillCell(location, Color.lightGray);
      game.getGameCallback().pacManEatPillsAndItems(location, "gold");
      game.removeItem("gold",location);
    } else if (c.equals(Color.blue)) {
      getBackground().fillCell(location, Color.lightGray);
      game.getGameCallback().pacManEatPillsAndItems(location, "ice");
      game.removeItem("ice",location);
    }
    String title = "[PacMan in the Multiverse] Current score: " + score;
    gameGrid.setTitle(title);
  }

  private void setActorLocation(Location next) {
    ArrayList<Portal> portals = game.getPortals();
    String portalColour = "";
    Location teleportLocation = null;

    for (Portal portal : portals) {
      if (portal.getLocation().equals(next)) {
        portalColour = portal.getColour();
        break;
      }
    }

    for (Portal portal : portals) {
      if (portal.getColour().equals(portalColour) && !(portal.getLocation().equals(next))) {
        teleportLocation = portal.getLocation();
        break;
      }
    }
    if (teleportLocation != null) {
      setLocation(teleportLocation);
    }
    else if (canMove(next)) {
      setLocation(next);
    }
  }
}

