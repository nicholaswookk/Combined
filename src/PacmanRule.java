package src;
import ch.aplu.jgamegrid.*;

import java.util.ArrayList;

public class PacmanRule {

    private String gameGrid;
    private int pacmanCount;
    private String fileName;
    private static final int nbVertCells = 11;
    private static final int nbHorzCells = 20;
    char[][] mazeArray;

    public PacmanRule(String gameGrid, String fileName) {
        this.gameGrid = gameGrid;
        this.fileName = fileName;
        this.mazeArray = initializeMazeArray(gameGrid);
    }

    public char[][] initializeMazeArray(String gameGrid){
        mazeArray = new char[nbVertCells][nbHorzCells];
        for (int i = 0; i < nbVertCells; i++)
        {
            for (int k = 0; k < nbHorzCells; k++) {
                mazeArray[i][k] = gameGrid.charAt(nbHorzCells * i + k);
            }
        }
        return mazeArray;
    }

    public boolean isValid() {
        pacmanCount = 0;
        char Pacman = 'f';
        for (char identifier : gameGrid.toCharArray()) {
            if (identifier == Pacman) {
                pacmanCount += 1;
            }
        }
        if (pacmanCount == 0 || pacmanCount > 1) {
            return false;
        }
        else {
            return true;
        }
    }

    public String toString() {
        if (pacmanCount == 0) {
            //get file name print out ("Level" + file name + " - no start for Pacman")
            return ("Level" + fileName + " - no start for Pacman");
        } else {

            StringBuilder pacmanLocations = new StringBuilder();
            int pacmanCounter = 0;
            for (int i = 0; i < nbVertCells; i++) {
                for (int k = 0; k < nbHorzCells; k++) {
                    if (mazeArray[i][k] == 'f'){
                        pacmanCounter++;
                        if (pacmanCounter > 1){
                            pacmanLocations.append("; ");
                        }
                        pacmanLocations.append("(").append(i).append(",").append(k).append(")");
                    }
                }
            }

            //get file name print out ("Level" + file name + " – more than one start for Pacman: " + get location for all pacmans : (3,7); (8, 1); (5, 2)]
            return ("[Level " + fileName + " - more than one start for Pacman: " + pacmanLocations + "]") ;
        }
    }
}
