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
    public static final String TEST1 = "properties/GameFolder/2map.xml";
    public static final String TESTTELEPORT = "properties/GameFolder/3TeleportTestMap.xml";
    public static final String DEFAULT_PROPERTIES_PATH = "properties/test1.properties";
    public static final String GameFolderPath = "properties/GameFolder";

    /**
     * Starting point
     *
     * @param args the command line arguments
     */

    public static void main(String args[]) {

//        no arguments, open up editor with empty map (done)
//        if (args.length == 0) {
//            new Controller(false);
//        }
//
//        if argument is a map file, load up map into editor (done)
//        else if (args[0].endsWith(".xml")) {
//            Controller mapEditor = new Controller(false);
//            mapEditor.loadFile(ARGUMENT);
//
            //testing code for a single game map
//        else {
//        Controller mapEditor = new Controller(true);
//        mapEditor.loadFile(TESTTELEPORT);
//
//        String propertiesPath = DEFAULT_PROPERTIES_PATH;
//        if (args.length > 0) {
//            propertiesPath = args[0];
//        }
//        final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);
//        GameCallback gameCallback = new GameCallback();
//        new Game(gameCallback, properties, mapEditor.getModel().getMapAsString());

//        }
        //if folder loaded in, instantiate an arraylist of all the xml map files and instantiate game maps accordingly
        File folderPath = new File(GameFolderPath);
        File[] fileList = folderPath.listFiles(new FilenameFilter() {
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
        //convert fileList array to arraylist
        List<File> fileArrayList = new ArrayList<>(Arrays.asList(fileList));
        ArrayList<String> gameFolderMapStrings = new ArrayList<String>();

        Controller mapEditor = new Controller(true);

        while (fileArrayList.size() != 0) {
            mapEditor.loadFile(fileList[0].getPath());
            gameFolderMapStrings.add(mapEditor.getModel().getMapAsString());
            fileArrayList.remove(0);
        }

        String propertiesPath = DEFAULT_PROPERTIES_PATH;
        final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);
        GameCallback gameCallback = new GameCallback();

        new Game(gameCallback, properties, gameFolderMapStrings);


    }

}



