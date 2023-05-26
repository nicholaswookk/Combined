// PacMan.java
// Simple PacMan implementation
package src;

import ch.aplu.jgamegrid.*;
import src.utility.GameCallback;

import javax.sound.sampled.Port;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class Game extends GameGrid
{
  private final static int nbHorzCells = 20;
  private final static int nbVertCells = 11;
  private ArrayList<String> loadedMazeStrings;
  private boolean gameContinues = true;

  protected PacManGameGrid grid;
  protected PacActor pacActor = new PacActor(this);
  private Monster troll = new Monster(this, MonsterType.Troll);
  private Monster tx5 = new Monster(this, MonsterType.TX5);


  private ArrayList<Location> pillAndItemLocations = new ArrayList<Location>();
  private ArrayList<Actor> iceCubes = new ArrayList<Actor>();
  private ArrayList<Actor> goldPieces = new ArrayList<Actor>();
  private ArrayList<Portal> portals = new ArrayList<Portal>();
  private GameCallback gameCallback;
  private Properties properties;
  private int seed = 30006;
  private ArrayList<Location> pillLocations = new ArrayList<>();
  private ArrayList<Location> goldLocations = new ArrayList<>();



  public Game(GameCallback gameCallback, Properties properties, ArrayList<String> loadedMazeStrings)
  {
    //Setup game
    super(nbHorzCells, nbVertCells, 20, false);
    this.loadedMazeStrings = loadedMazeStrings;
    this.gameCallback = gameCallback;
    this.properties = properties;
    setSimulationPeriod(100);
    setTitle("[PacMan in the Multiverse]");
    pacActor.setAuto(Boolean.parseBoolean(properties.getProperty("PacMan.isAuto")));
    String title = "";

    //check if there are any more maps that have been loaded into game
    while (loadedMazeStrings.size() != 0) {
      System.out.println(loadedMazeStrings);
      String mazeString = loadedMazeStrings.get(0);
      this.grid = new PacManGameGrid(nbHorzCells, nbVertCells, mazeString);
      loadPillAndItemsLocations();

      GGBackground bg = getBg();
      drawGrid(bg);

      //Setup Random seeds
      seed = Integer.parseInt(properties.getProperty("seed"));
      pacActor.setSeed(seed);
      troll.setSeed(seed);
      tx5.setSeed(seed);
      addKeyRepeatListener(pacActor);
      setKeyRepeatPeriod(150);
      troll.setSlowDown(3);
      tx5.setSlowDown(3);
      pacActor.setSlowDown(3);
      tx5.stopMoving(5);
      setupActorLocations();


      //Run the game
      doRun();
      show();
      // Loop to look for collision in the application thread
      // This makes it improbable that we miss a hit
      boolean hasPacmanBeenHit;
      boolean hasPacmanEatAllPills;
      setupPillAndItemsLocations();
      int maxPillsAndItems = countPillsAndItems();

      do {
        hasPacmanBeenHit = troll.getLocation().equals(pacActor.getLocation()) ||
                tx5.getLocation().equals(pacActor.getLocation());
        hasPacmanEatAllPills = pacActor.getNbPills() >= maxPillsAndItems;
        delay(10);
      } while (!hasPacmanBeenHit && !hasPacmanEatAllPills);
      delay(120);

      Location loc = pacActor.getLocation();
      troll.setStopMoving(true);
      tx5.setStopMoving(true);
      pacActor.removeSelf();

      if (hasPacmanBeenHit) {
        bg.setPaintColor(Color.red);
        title = "GAME OVER";
        addActor(new Actor("sprites/explosion3.gif"), loc);
        break;
      } else if (hasPacmanEatAllPills) {
        loadedMazeStrings.remove(0);

        if (loadedMazeStrings.size() == 0) {
          bg.setPaintColor(Color.yellow);
          title = "YOU WIN";
          break;
        }
      }
    }
    setTitle(title);
    gameCallback.endOfGame(title);
    doPause();
  }

  public GameCallback getGameCallback() {
    return gameCallback;
  }

  private void setupActorLocations() {

    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++) {

        Location location = new Location(x, y);
        int a = grid.getCell(location);
        if (a == 5) { // Pacman
          addActor(pacActor, location);
        } else if (a == 6) { // Troll
          addActor(troll, location, Location.NORTH);
        }
        else if (a == 7) { // TX5
          addActor(tx5, location, Location.NORTH);
        }
      }
    }
  }

  private int countPillsAndItems() {
    int pillsAndItemsCount = 0;
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        Location location = new Location(x, y);
        int a = grid.getCell(location);
        if (a == 1 && pillLocations.size() == 0) { // Pill
          pillsAndItemsCount++;
        } else if (a == 3 && goldLocations.size() == 0) { // Gold
          pillsAndItemsCount++;
        }
      }
    }
    if (pillLocations.size() != 0) {
      pillsAndItemsCount += pillLocations.size();
    }

    if (goldLocations.size() != 0) {
      pillsAndItemsCount += goldLocations.size();
    }

    return pillsAndItemsCount;
  }

  public ArrayList<Location> getPillAndItemLocations() {
    return pillAndItemLocations;
  }


  private void loadPillAndItemsLocations() {

    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++) {

        Location location = new Location(x, y);
        int a = grid.getCell(location);
        if (a == 1) { // Pill
          pillLocations.add(location);
        } else if (a == 3) { // Gold
          goldLocations.add(location);
        }
      }
    }
  }

  private void setupPillAndItemsLocations() {
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        Location location = new Location(x, y);
        int a = grid.getCell(location);
        if (a == 1 && pillLocations.size() == 0) {
          pillAndItemLocations.add(location);
        }
        if (a == 3 && goldLocations.size() == 0) {
          pillAndItemLocations.add(location);
        }
        if (a == 4) {
          pillAndItemLocations.add(location);
        }
      }
    }

    if (pillLocations.size() > 0) {
      for (Location location : pillLocations) {
        pillAndItemLocations.add(location);
      }
    }
    if (goldLocations.size() > 0) {
      for (Location location : goldLocations) {
        pillAndItemLocations.add(location);
      }
    }
  }

  private void drawGrid(GGBackground bg)
  {
    bg.clear(Color.gray);
    bg.setPaintColor(Color.white);
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        bg.setPaintColor(Color.white);
        Location location = new Location(x, y);
        int a = grid.getCell(location);
        if (a > 0)
          bg.fillCell(location, Color.lightGray);
        if (a == 1 && pillLocations.size() == 0) { // Pill
          putPill(bg, location);
        } else if (a == 3 && goldLocations.size() == 0) { // Gold
          putGold(bg, location);
        } else if (a == 4) {
          putIce(bg, location);
        } else if (a > 7) {
          putPortal(bg, location, a);
        }
      }
    }

    for (Location location : pillLocations) {
      putPill(bg, location);
    }

    for (Location location : goldLocations) {
      putGold(bg, location);
    }
  }



  private void putPill(GGBackground bg, Location location){
    bg.fillCircle(toPoint(location), 5);
  }

  private void putGold(GGBackground bg, Location location){
    bg.setPaintColor(Color.yellow);
    bg.fillCircle(toPoint(location), 5);
    Actor gold = new Actor("sprites/gold.png");
    this.goldPieces.add(gold);
    addActor(gold, location);
  }

  private void putIce(GGBackground bg, Location location){
    bg.setPaintColor(Color.blue);
    bg.fillCircle(toPoint(location), 5);
    Actor ice = new Actor("sprites/ice.png");
    this.iceCubes.add(ice);
    addActor(ice, location);
  }

  //create portal with colours based on toInt
  private void putPortal(GGBackground bg, Location location, int a) {
    if (a == 8) {
      Portal portal = new Portal(this, "sprites/portalWhiteTile.png", "WHITE");
      portals.add(portal);
      addActor(portal, location);
    }
    if (a == 9) {
      Portal portal = new Portal(this, "sprites/portalYellowTile.png", "YELLOW");
      portals.add(portal);
      addActor(portal, location);
    }
    if (a == 10) {
      Portal portal = new Portal(this, "sprites/portalDarkGoldTile.png", "DARK GOLD");
      portals.add(portal);
      addActor(portal, location);
    }
    if (a == 11) {
      Portal portal = new Portal(this, "sprites/portalDarkGrayTile.png", "DARK GRAY");
      portals.add(portal);
      addActor(portal, location);
    }
  }

  public void removeItem(String type,Location location){
    if(type.equals("gold")){
      for (Actor item : this.goldPieces){
        if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
          item.hide();
        }
      }
    }else if(type.equals("ice")){
      for (Actor item : this.iceCubes){
        if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
          item.hide();
        }
      }
    }
  }

  public ArrayList<Portal> getPortals() {
    return portals;
  }
  public String getPortalColour(Portal portal) {
    return portal.getColour();
  }

  public int getNumHorzCells(){
    return this.nbHorzCells;
  }
  public int getNumVertCells(){
    return this.nbVertCells;
  }
}
