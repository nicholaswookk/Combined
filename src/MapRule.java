package src;

public abstract class MapRule {

    private String gameGrid;
    private String fileName;
    private static final int nbVertCells = 11;
    private static final int nbHorzCells = 20;
    char[][] mazeArray;

    public MapRule(String gameGrid, String fileName) {
        this.gameGrid = gameGrid;
        this.fileName = fileName;
        this.mazeArray = initializeMazeArray(gameGrid);
    }
    public char[][] initializeMazeArray(String gameGrid) {
        mazeArray = new char[nbVertCells][nbHorzCells];
        for (int i = 0; i < nbVertCells; i++)
        {
            for (int k = 0; k < nbHorzCells; k++) {
                mazeArray[i][k] = gameGrid.charAt(nbHorzCells * i + k);
            }
        }
        return mazeArray;
    }

    public String getGameGrid() {
        return gameGrid;
    }
    public String getFileName() {
        return fileName;
    }
    public char[][] getMazeArray() {
        return mazeArray;
    }
    public int getNbVertCells() {
        return nbVertCells;
    }
    public int getNbHorzCells() {
        return nbHorzCells;
    }

    public abstract boolean isValid();
    public abstract String errorString();

}
