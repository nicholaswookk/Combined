package src;

public class ItemCountRule extends MapRule {

    private int itemCount = 0;

    public ItemCountRule(String gameGrid, String fileName) {
        super(gameGrid, fileName);
    }

    public boolean isValid() {
        for (char identifier : getGameGrid().toCharArray()) {
            if (identifier == 'c' || identifier == 'd') {
                    itemCount++;
                }
            }
        return (itemCount >= 2);
    }

    public String errorString() {
        String error = "[Level " + getFileName() + " - less than 2 Gold and Pill]";
        return error;
    }
}
