package src;

import java.io.File;

public abstract class GameRule {

    private File[] fileList;
    private String folderName;

    public GameRule(File[] fileList, String folderName) {
        this.fileList = fileList;
        this.folderName = folderName;
    }
    public File[] getFileList() { return fileList; }
    public String getFolderName() { return folderName; }

    public abstract boolean isValid();
    public abstract String errorString();
}
