package c.cmpt276.childapp.model.config;

/**
 * Struct for every config for each child
 */
public class IndividualConfig {
    private String name;
    private boolean flipCoin;
    private String base64Img;
    //  private boolean timeoutTimer;

    public IndividualConfig(String name, boolean flipCoin, String base64Img) {
        set(name, flipCoin, base64Img);
    }

    public String getBase64Img() {
        return base64Img;
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

    public void set(String name, boolean flipCoin, String base64Img) {
        this.name = name;
        this.flipCoin = flipCoin;
        this.base64Img = base64Img;
    }
}
