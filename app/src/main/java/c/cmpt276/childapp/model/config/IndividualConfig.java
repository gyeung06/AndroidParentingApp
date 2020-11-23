package c.cmpt276.childapp.model.config;

/**
 * Struct for every config for each child
 */
public class IndividualConfig {
    private String name;
    private boolean flipCoin;
    private boolean tasks;
    //  private boolean timeoutTimer;

    public IndividualConfig(String name, boolean flipCoin) {
        set(name, flipCoin);
        tasks = true; //TODO integrate
        //TODO make UI and also make changes here for tasks
    }

    public String getName() {
        return name;
    }

    public boolean getFlipCoin() {
        return flipCoin;
    }

    public boolean getTaskEnabled() {
        return tasks;
    }

//    public boolean getTimeoutTimer() {
//        return timeoutTimer;
//    }

    public void set(String name, boolean flipCoin) {
        this.name = name;
        this.flipCoin = flipCoin;

    }
}
