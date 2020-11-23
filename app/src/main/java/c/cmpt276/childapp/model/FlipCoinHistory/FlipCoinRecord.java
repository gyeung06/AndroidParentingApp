package c.cmpt276.childapp.model.FlipCoinHistory;

/**
 * A single record for saving flip coin history data
 */
public class FlipCoinRecord {
    private boolean choseHead;
    private boolean resultHead;

    private String chooser;
    private String rival;
    private String date;

    public FlipCoinRecord(String chooser, String rival, boolean choseHead) {
        this.chooser = chooser;
        this.rival = rival;
        this.choseHead = choseHead;
    }

    public void setResult(boolean head, String date) {
        this.resultHead = head;
        this.date = date;
    }

    public boolean getChoseHead() {
        return choseHead;
    }

    public String getChooser() {
        return chooser;
    }

    public String getRival() {
        return rival;
    }

    public String getDate() {
        return date;
    }

    public boolean getResult() {
        return resultHead;
    }
}
