package src;

public class PacmanRule extends MapRule {

    private int pacmanCount = 0;

    public PacmanRule(String gameGrid, String fileName) {
        super(gameGrid, fileName);
    }

    public boolean isValid() {
        for (char identifier : getGameGrid().toCharArray()) {
            if (identifier == 'f') {
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

    public String errorString() {
        if (pacmanCount == 0) {
            //get file name print out ("Level" + file name + " - no start for Pacman")
            return ("[Level " + getFileName() + " - no start for Pacman]");
        } else {

            StringBuilder pacmanLocations = new StringBuilder();
            int pacmanCounter = 0;
            for (int i = 0; i < getNbVertCells(); i++) {
                for (int k = 0; k < getNbHorzCells(); k++) {
                    if (getMazeArray()[i][k] == 'f'){
                        pacmanCounter++;
                        if (pacmanCounter > 1){
                            pacmanLocations.append("; ");
                        }
                        pacmanLocations.append("(").append(k+1).append(",").append(i+1).append(")");
                    }
                }
            }

            //get file name print out ("Level" + file name + " – more than one start for Pacman: " + get location for all pacmans : (3,7); (8, 1); (5, 2)]
            return ("[Level " + getFileName() + " - more than one start for Pacman: " + pacmanLocations + "]") ;
        }
    }
}
