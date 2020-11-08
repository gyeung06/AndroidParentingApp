package c.cmpt276.childapp.model.config;

/**
 * Struct for every config for each child
 */
public class IndividualConfig {
    private String name;
    private boolean flipCoin;
  //  private boolean timeoutTimer;

    public IndividualConfig(String name, boolean flipCoin) {
        set(name, flipCoin);
    }

    public String getName() {
        return name;
    }

    public boolean getFlipCoin() {
        return flipCoin;
    }

//    public boolean getTimeoutTimer() {
//        return timeoutTimer;
//    }

    public void set(String name, boolean flipCoin) {
        this.name = name;
        this.flipCoin = flipCoin;

    }
}
