package c.cmpt276.childapp.model.config;

/**
 * Struct for every config for each child
 */
public class IndividualConfig {
    private String name;
    private boolean flipCoin;
    private boolean timeoutTimer;

    public IndividualConfig(String name, boolean flipCoin, boolean timeoutTimer) {
        set(name, flipCoin, timeoutTimer);
    }

    public String getName() {
        return name;
    }

    public boolean getFlipCoin() {
        return flipCoin;
    }

    public boolean getTimeoutTimer() {
        return timeoutTimer;
    }

    public void set(String name, boolean flipCoin, boolean timeoutTimer) {
        this.name = name;
        this.flipCoin = flipCoin;
        this.timeoutTimer = timeoutTimer;
    }
}
