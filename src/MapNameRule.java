package src;

import java.io.File;

public class MapNameRule extends GameRule {

    public MapNameRule(File[] fileList, String folderName) { super(fileList, folderName); }

    public boolean isValid() {
        if (getFileList().length == 0) {
            return false;
        }
        else {
            return true;
        }
    }
    public String errorString() {
        String error = "[Game " + getFolderName() + " - no maps found]";
        return error;
    }
}
