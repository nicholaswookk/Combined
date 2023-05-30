package src;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class DuplicateLevelRule extends GameRule {

    private ArrayList<Integer> gameLevels = new ArrayList<>();
    private boolean duplicateLevelsFound = false;
    private ArrayList<Integer> duplicateLevels = new ArrayList<>();
    public DuplicateLevelRule(File[] fileList, String folderName) { super(fileList, folderName); }

    public boolean isValid() {
        for (File file : getFileList()) {
            int level = Integer.parseInt(file.getName().split("\\D")[0]);
            gameLevels.add(level);
        }
        for (int i = 0; i < gameLevels.size() - 1; i++) {
            if (gameLevels.get(i) == gameLevels.get(i+1)){
                duplicateLevelsFound = true;
                if (!duplicateLevels.contains(gameLevels.get(i))) {
                    duplicateLevels.add(gameLevels.get(i));
                }
            }
        }
        return !duplicateLevelsFound;
    }
    //[Game foldername – multiple maps at same level: 6level.xml; 6_map.xml; 6also.xml]
    public String errorString() {
        StringBuilder finalErrorString = new StringBuilder();
        int errorCounter = 0;
        for (Integer level : duplicateLevels) {
            int levelCount = 0;
            errorCounter++;
            StringBuilder duplicateLevelNames = new StringBuilder();
            for (File file : getFileList()) {
                if (Integer.parseInt(file.getName().split("\\D")[0]) == level){
                    levelCount++;
                    if (levelCount > 1) {
                        duplicateLevelNames.append("; ");
                    }
                    duplicateLevelNames.append(file.getName());
                }
            }
            String errorMessage = "[Game " + getFolderName() + " - multiple maps at same level: " + duplicateLevelNames + "]";
            if (errorCounter > 1){
                finalErrorString.append("\n");
            }
            finalErrorString.append(errorMessage);
        }
        return String.valueOf(finalErrorString);
    }
}
