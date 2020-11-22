package c.cmpt276.childapp.model.config;

/**
 * Struct for every config for each child
 */
public class IndividualConfig {
    private String name;
    private boolean flipCoin;
    private long lastChose;
    //  private boolean timeoutTimer;

    public IndividualConfig(String name, boolean flipCoin) {
        this.name = name;
        this.flipCoin = flipCoin;
    }

    public String getName() {
        return name;
    }

    public boolean getFlipCoin() {
        return flipCoin;
    }

    public long getLastChose() {
        return lastChose;
    }

    public void chose() {
        lastChose = System.currentTimeMillis();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFlipCoin(boolean flipCoin) {
        this.flipCoin = flipCoin;
    }
}
