package src;

import src.utility.GameCallback;
import src.utility.PropertiesLoader;

import java.util.Properties;
import src.matachi.mapeditor.editor.Controller;

import java.util.Arrays;
import java.util.List;
import java.io.*;


import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;



public class Driver {
    public static final String TEST1 = "properties/2map.xml";
    public static final String TESTTELEPORT = "properties/3TeleportTestMap.xml";
    public static final String DEFAULT_PROPERTIES_PATH = "properties/test1.properties";

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
            //if argument is a folder, play 1map.xml all maps in the folder in ascending order
//        else {
        Controller mapEditor = new Controller(true);
        mapEditor.loadFile(TESTTELEPORT);

        String propertiesPath = DEFAULT_PROPERTIES_PATH;
        if (args.length > 0) {
            propertiesPath = args[0];
        }
        final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);
        GameCallback gameCallback = new GameCallback();
        new Game(gameCallback, properties, mapEditor.getModel().getMapAsString());

//        }
    }

    public void loadFiles(){
        File folderPath = new File("saveFiles");
        File[] fileList = folderPath.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                //checks if file is an xml file and if it starts with a number
                return name.endsWith(".xml") && Character.isDigit(name.charAt(0));
            }
        });

        //sort file list based on the number at the start of the file name
        Arrays.sort(fileList,
                (s1, s2) ->
                        Integer.compare(
                                Integer.parseInt(s1.getName().split("\\D")[0]),
                                Integer.parseInt(s2.getName().split("\\D")[0])
                        )
        );

        for (File file : fileList) {
            System.out.println("File name: " + file.getName());
            System.out.println("File path: " + file.getPath());
        }
    }

}



