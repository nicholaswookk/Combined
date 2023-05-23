package src;

import src.utility.GameCallback;
import src.utility.PropertiesLoader;

import java.util.Properties;
import src.matachi.mapeditor.editor.Controller;

public class Driver {
    public static final String ARGUMENT = "properties/1map.xml";
    public static final String DEFAULT_PROPERTIES_PATH = "properties/test2.properties";

    /**
     * Starting point
     *
     * @param args the command line arguments
     */

    public static void main(String args[]) {

        //no arguments, open up editor with empty map (done)
//        if (args.length == 0) {
//            new Controller();
//        }


        //if argument is a map file, load up map into editor (done)
//        else if (args[0].endsWith(".xml")) {
//        Controller mapEditor = new Controller();
//        mapEditor.loadFileFromName(ARGUMENT);

        //if argument is a folder, play test all maps in the folder in ascending order
//        else {
//            //loadFiles(args[1])
//            return;
//        }


        //may use for testing portion
        String propertiesPath = DEFAULT_PROPERTIES_PATH;
        if (args.length > 0) {
            propertiesPath = args[0];
        }
        final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);
        GameCallback gameCallback = new GameCallback();
        new Game(gameCallback, properties);

    }
}
