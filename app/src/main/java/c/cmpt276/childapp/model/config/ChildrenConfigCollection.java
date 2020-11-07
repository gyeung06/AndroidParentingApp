package c.cmpt276.childapp.model.config;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c.cmpt276.childapp.model.FlipCoinHistory.HistoryCollection;

/**
 * singleton class for storing all children's config
 * and flip coin history information
 */
public class ChildrenConfigCollection implements Iterable<IndividualConfig> {
    private static List<IndividualConfig> children;
    private static ChildrenConfigCollection collection = new ChildrenConfigCollection();
    private static Gson gson = new Gson();
    private HistoryCollection historyCollection;
    private String lastWinner = "";
    private String lastLoser = "";

    private ChildrenConfigCollection() {
        children = new ArrayList<>();
        historyCollection = new HistoryCollection();
    }

    public static ChildrenConfigCollection getInstance() {
        return collection;
    }

    public static ChildrenConfigCollection loadWithJSONObject(String json) {
        collection = gson.fromJson(json, ChildrenConfigCollection.class);
        return collection;
    }

    public String getJSON() {
        return gson.toJson(collection);
    }

    public HistoryCollection getFlipCoinHistory() {
        return historyCollection;
    }

    public int[] getFlipCoinEnabledChildrenPositions() {
        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if (children.get(i).getFlipCoin()) {
                l.add(i);
            }
        }
        int[] toReturn = new int[l.size()]; //doesn't seem like there is a better way
        for (int i = 0; i < l.size(); i++) {
            toReturn[i] = l.get(i);
        }
        return toReturn;
    }

    public int[] getTimerEnabledChildrenPositions() {
        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if (children.get(i).getTimeoutTimer()) {
                l.add(i);
            }
        }
        int[] toReturn = new int[l.size()];
        for (int i = 0; i < l.size(); i++) {
            toReturn[i] = l.get(i);
        }
        return toReturn;
    }

    public void add(IndividualConfig config) {
        children.add(config);
    }

    public IndividualConfig get(int i) {
        return children.get(i);
    }

    public int size() {
        return children.size();
    }

    public Iterator<IndividualConfig> iterator() {
        return children.iterator();
    }

    public void delete(int i) {
        children.remove(i);
    }

    public List<IndividualConfig> getArray() {
        return children;
    }

    public String getLastWinner() {
        return lastWinner;
    }

    public void setLastResultCandidate(String lastWinner, String lastLoser) {
        this.lastWinner = lastWinner;
        this.lastLoser = lastLoser;
    }

    public String getLastLoser() {
        return lastLoser;
    }
}
