package c.cmpt276.childapp.model.FlipCoinHistory;

import c.cmpt276.childapp.model.config.ChildrenConfigCollection;

/**
 * A single record for saving flip coin history data
 */
public class FlipCoinRecord {
    private boolean head;
    private String headChild;
    private String tailChild;
    private String date;

    public FlipCoinRecord(String headChild, String tailChild) {
        this.headChild = headChild;
        this.tailChild = tailChild;
    }

    public void setResult(boolean head, String date) {
        this.head = head;
        this.date = date;

        if (!headChild.isEmpty() && !tailChild.isEmpty()) {

            if (head) {
                ChildrenConfigCollection.getInstance().setLastResultCandidate(headChild, tailChild);
            } else {
                ChildrenConfigCollection.getInstance().setLastResultCandidate(tailChild, headChild);
            }
        }


    }

    public boolean isHead() {
        return head;
    }

    public String getHeadChild() {
        return headChild;
    }

    public String getTailChild() {
        return tailChild;
    }

    public String getDate() {
        return date;
    }
}
