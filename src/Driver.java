package src;

import src.utility.GameCallback;
import src.utility.PropertiesLoader;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import src.matachi.mapeditor.editor.Controller;

import java.util.Arrays;
import java.io.*;


public class Driver {
    public static final String DEFAULT_PROPERTIES_PATH = "properties/test2.properties";

    /**
     * Starting point
     *
     * @param args the command line arguments
     */

    public static void main(String args[]) {

//        no arguments, open up editor with empty map (done)
        if (args.length == 0) {
            new Controller(false);
        }

//        if argument is a map file, load up map into editor (done)
        else if (args[0].endsWith(".xml")) {
            Controller mapEditor = new Controller(false);
            mapEditor.loadFile(args[0]);

        }

        else {
            //if folder loaded in, instantiate an arraylist of all the xml map files and instantiate game maps accordingly
            File folder = new File(args[0]);
            File[] fileList = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    //checks if file is an xml file and if it starts with a number
                    return name.endsWith(".xml") && Character.isDigit(name.charAt(0));
                }
            });

            //sort fileList based on number at start of file name
            Arrays.sort(fileList,
                    (s1, s2) ->
                            Integer.compare(
                                    Integer.parseInt(s1.getName().split("\\D")[0]),
                                    Integer.parseInt(s2.getName().split("\\D")[0])
                            )
            );

            GameCallback gameCallback = new GameCallback();

            if (!gameCheckSuccess(gameCallback, fileList, folder.getName())) {
                new Controller(false);
                return;
            }

            //convert fileList array to arraylist
            List<File> fileArrayList = new ArrayList<>(Arrays.asList(fileList));
            ArrayList<String> gameFolderMapStrings = new ArrayList<String>();
            ArrayList<String> mapFilePaths = new ArrayList<String>();

            Controller mapEditor = new Controller(true);

            int fileListIndex = 0;
            while (fileArrayList.size() != 0) {
                String currentFilePath = fileList[fileListIndex].getPath();
                mapEditor.loadFile(currentFilePath);
                mapFilePaths.add(currentFilePath);
                fileListIndex++;
                gameFolderMapStrings.add(mapEditor.getModel().getMapAsString());
                fileArrayList.remove(0);
            }

            final Properties properties = PropertiesLoader.loadPropertiesFile(DEFAULT_PROPERTIES_PATH);
            new Game(gameCallback, properties, gameFolderMapStrings, 0, fileList);


            //if testing single game map, return to edit mode on that map once pacMan is destroyed or level completed
            if (gameFolderMapStrings.size() == 1){
                mapEditor = new Controller(false);
                mapEditor.loadFile(fileList[0].getPath());
            }
        }
    }

    public static void errorFound(String filePath, boolean playTest){
        Controller mapEditor = new Controller(playTest);
        mapEditor.loadFile(filePath);
    }

    private static boolean gameCheckSuccess(GameCallback gameCallback, File[] fileList, String folderPathName){
        int errorCount = 0;
        String mapNameRuleError = RuleEngineFacade.getInstance().getMapNameRuleError(fileList, folderPathName);
        if (mapNameRuleError != null) {
            gameCallback.writeString(mapNameRuleError);
            errorCount++;
        }
        String duplicateLevelRuleError = RuleEngineFacade.getInstance().getDuplicateLevelRuleError(fileList, folderPathName);
        if (duplicateLevelRuleError != null) {
            gameCallback.writeString(duplicateLevelRuleError);
            errorCount++;
        }
        return errorCount == 0;
    }

    public static void openEmptyMapEditor(boolean playtest){
        new Controller(false);
    }

}



