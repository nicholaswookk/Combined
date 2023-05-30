// PacGrid.java
package src;

import ch.aplu.jgamegrid.*;

public class PacManGameGrid
{
  private int nbHorzCells;
  private int nbVertCells;
  private int[][] mazeArray;

  public PacManGameGrid(int nbHorzCells, int nbVertCells, String mazeString)
  {
    this.nbHorzCells = nbHorzCells;
    this.nbVertCells = nbVertCells;
    mazeArray = new int[nbVertCells][nbHorzCells];
    String maze = mazeString;

    // Copy structure into integer array
    for (int i = 0; i < nbVertCells; i++)
    {
      for (int k = 0; k < nbHorzCells; k++) {
        int value = toInt(maze.charAt(nbHorzCells * i + k));
        mazeArray[i][k] = value;
      }
    }
  }

  public int getCell(Location location)
  {
    return mazeArray[location.y][location.x];
  }
  private int toInt(char c)
  {
    if (c == 'b') // Wall
      return 0;
    if (c == 'c') // Pill
      return 1;
    if (c == 'a') // Path
      return 2;
    if (c == 'd') // Gold
      return 3;
    if (c == 'e') // Ice
      return 4;
    if (c == 'f') // Pacman
      return 5;
    if (c == 'g') // Troll
      return 6;
    if (c == 'h') // TX5
      return 7;
    if (c == 'i') // PortalWhite
      return 8;
    if (c == 'j') // PortalYellow
      return 9;
    if (c == 'k') // PortalDarkGold
      return 10;
    if (c == 'l') // PortalDarkGray
      return 11;
    return 0;
  }

}
