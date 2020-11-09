package c.cmpt276.childapp.model.FlipCoinHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of flip coin history
 */
public class HistoryCollection {
    List<FlipCoinRecord> his;

    public HistoryCollection() {
        his = new ArrayList<>();
    }

    public int size() {
        return his.size();
    }

    public void add(FlipCoinRecord record) {
        his.add(record);
    }

    public FlipCoinRecord get(int i) {
        return his.get(i);
    }

    public void clear() {
        his.clear();
    }

    public List<FlipCoinRecord> getArray() {
        return his;
    }

    public HistoryCollection filter(String name) {
        HistoryCollection newhis = new HistoryCollection();

        for (FlipCoinRecord record : his) {
            if (record.getChooser().equals(name)) {
                newhis.add(record);
            }
        }

        return newhis;
    }
}
