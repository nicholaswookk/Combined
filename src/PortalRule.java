package src;

public class PortalRule extends MapRule {

    private String[] errorColourString = {null, null, null, null};
    private char[] portalIndex = {'i', 'j', 'k', 'l'};
    private int whitePortalCount = 0; //identifier 'i'
    private int yellowPortalCount = 0; //identifier 'j'
    private int darkGoldPortalCount = 0; //identifier 'k'
    private int darkGrayPortalCount = 0; //identifier 'l'
    public PortalRule(String gameGrid, String fileName) {
        super(gameGrid, fileName);
    }
    public boolean isValid() {
        for (char identifier : getGameGrid().toCharArray()) {
            if (identifier == 'i') {
                whitePortalCount += 1;
            }
            if (identifier == 'j') {
                yellowPortalCount += 1;
            }
            if (identifier == 'k') {
                darkGoldPortalCount += 1;
            }
            if (identifier == 'l') {
                darkGrayPortalCount += 1;
            }
        }
        //if there are portals and they are not in a pair then it is not valid
        if (whitePortalCount != 0 && whitePortalCount != 2){
            errorColourString[0] = " - portal White count is not 2: ";
        }
        if (yellowPortalCount != 0 && yellowPortalCount != 2){
            errorColourString[1] = " - portal Yellow count is not 2: ";
        }
        if (darkGoldPortalCount != 0 && darkGoldPortalCount != 2){
            errorColourString[2] = " - portal DarkGold count is not 2: ";
        }
        if (darkGrayPortalCount != 0 && darkGrayPortalCount != 2){
            errorColourString[3] = " - portal DarkGray count is not 2: ";
        }
        for (int i = 0; i < 4; i++){
            if (errorColourString[i] != null){
                return false;
            }
        }
        return true;
    }

    public String errorString() {

        StringBuilder finalErrorString = new StringBuilder();
        int errorCounter = 0;
        for (int i = 0; i < 4; i ++){
            if (errorColourString[i] != null){
                errorCounter++;
                StringBuilder portalLocations = new StringBuilder();
                int portalCounter = 0;
                for (int j = 0; j < getNbVertCells(); j++) {
                    for (int k = 0; k < getNbHorzCells(); k++) {
                        if (getMazeArray()[j][k] == portalIndex[i]){
                            portalCounter++;
                            if (portalCounter > 1){
                                portalLocations.append("; ");
                            }
                            portalLocations.append("(").append(k + 1).append(",").append(j + 1).append(")");
                        }
                    }
                }
                String errorMessage = "[Level " + getFileName() + errorColourString[i] + portalLocations + "]";
                if (errorCounter > 1) {
                    finalErrorString.append("\n");
                }
                finalErrorString.append(errorMessage);
            }
        }
        return String.valueOf(finalErrorString);
    }
}
