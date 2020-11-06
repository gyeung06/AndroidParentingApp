package c.cmpt276.childapp.model.FlipCoinHistory;

import java.util.ArrayList;
import java.util.List;

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

    public HistoryCollection filter(String name) {
        HistoryCollection newhis = new HistoryCollection();
        for (int i = 0; i < size(); i++) {
            if (his.get(i).getHeadChild().equalsIgnoreCase(name) || his.get(i).getTailChild().equalsIgnoreCase(name)) {
                newhis.add(his.get(i));
            }
        }
        return newhis;
    }
}
