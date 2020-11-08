package c.cmpt276.childapp.model.config;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c.cmpt276.childapp.model.FlipCoinHistory.HistoryCollection;

import static android.content.Context.MODE_PRIVATE;

/**
 * singleton class for storing all children's config
 * and flip coin history information
 */
public class ChildrenConfigCollection implements Iterable<IndividualConfig> {
    private static ChildrenConfigCollection collection;
    private static Gson gson = new Gson();
    private HistoryCollection historyCollection;
    private List<IndividualConfig> children;
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
        if (json.isEmpty()) {
            collection = new ChildrenConfigCollection();
        } else {
            collection = gson.fromJson(json, ChildrenConfigCollection.class);
        }
        return collection;
    }

    public String getJSON() {
        return gson.toJson(collection);
    }

    public HistoryCollection getFlipCoinHistory() {
        return historyCollection;
    }

    public List<Integer> getFlipCoinEnabledChildrenPositions() {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if (children.get(i).getFlipCoin()) {
                positions.add(i);
            }
        }
        return positions;
    }
//unused
//    public int[] getTimerEnabledChildrenPositions() {
//        List<Integer> l = new ArrayList<>();
//        for (int i = 0; i < size(); i++) {
//            if (children.get(i).getTimeoutTimer()) {
//                l.add(i);
//            }
//        }
//        int[] toReturn = new int[l.size()];
//        for (int i = 0; i < l.size(); i++) {
//            toReturn[i] = l.get(i);
//        }
//        return toReturn;
//    }

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

    public void save(Activity activity) {
        SharedPreferences sp = activity.getSharedPreferences("USER_CHILDREN", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.clear();
        ed.putString("CHILDREN_INFO", getJSON());
        ed.apply();
    }
}
