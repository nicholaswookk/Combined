package src;

public class GameRuleEngineFacade {
    private PacmanRule pacmanRule;
    private static final GameRuleEngineFacade instance = new GameRuleEngineFacade();
    private GameRuleEngineFacade(){
    }
    public static GameRuleEngineFacade getInstance(){
        return instance;
    }

    public String getPacmanRuleError(String gamegrid, String filename) {
        pacmanRule = new PacmanRule(gamegrid, filename);
        if (!pacmanRule.isValid()) {
            return pacmanRule.toString();
        }
        return null;
    }

    public String getPortalRuleError(String gamegrid, String filename) {
        return "";
    }
}

//GameRuleEngineFacade.getInstance().getPacmanRuleisValid(gamegrid, filename);