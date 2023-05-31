package src;

import java.io.File;
import java.util.ArrayList;

public class RuleEngineFacade {
    private PacmanRule pacmanRule;
    private ItemCountRule itemCountRule;
    private PortalRule portalRule;
    private DuplicateLevelRule duplicateLevelRule;
    private MapNameRule mapNameRule;
    private GoldPillAccessibleRule goldPillAccessibleRule;
    private static RuleEngineFacade instance;
    private RuleEngineFacade(){}

    //lazy initialisation of singleton class
    public static synchronized RuleEngineFacade getInstance() {
        if (instance == null) {
            instance = new RuleEngineFacade();
        }
        return instance;
    }

    public String getPacmanRuleError(String gamegrid, String filename) {
        pacmanRule = new PacmanRule(gamegrid, filename);
        if (!pacmanRule.isValid()) {
            return pacmanRule.errorString();
        }
        return null;
    }

    public String getItemCountRuleError(String gamegrid, String filename) {
        itemCountRule = new ItemCountRule(gamegrid, filename);
        if (!itemCountRule.isValid()) {
            return itemCountRule.errorString();
        }
        return null;
    }

    public String getPortalRuleError(String gamegrid, String filename) {
        portalRule = new PortalRule(gamegrid, filename);
        if (!portalRule.isValid()) {
            return portalRule.errorString();
        }
        return null;
    }

    public String getGoldPillAccessibleRuleError(String gameGrid, String fileName, ArrayList<Portal> portals){
        goldPillAccessibleRule = new GoldPillAccessibleRule(gameGrid, fileName, portals);
        if (!goldPillAccessibleRule.isValid()) {
            return goldPillAccessibleRule.errorString();
        }
        return null;
    }

    public String getDuplicateLevelRuleError(File[] fileList, String folderName){
        duplicateLevelRule = new DuplicateLevelRule(fileList, folderName);
        if (!duplicateLevelRule.isValid()) {
            return duplicateLevelRule.errorString();
        }
        return null;
    }

    public String getMapNameRuleError(File[] fileList, String folderName){
        mapNameRule = new MapNameRule(fileList, folderName);
        if (!mapNameRule.isValid()) {
            return mapNameRule.errorString();
        }
        return null;
    }


}