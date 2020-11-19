package c.cmpt276.childapp.model.config;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
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
    private HashMap<String, IndividualConfig> children;
    private String lastWinner = "";
    private String lastLoser = "";

    private ChildrenConfigCollection() {
        children = new HashMap<>();
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
        Log.d("gson", gson.toJson(collection));
        return gson.toJson(collection);
    }

    public HistoryCollection getFlipCoinHistory() {
        return historyCollection;
    }

    public List<String> getFlipCoinEnabledChildrenNames() {
        List<String> names = new ArrayList<>();

        for (IndividualConfig child : children.values()) {
            if (child.getFlipCoin()) names.add(child.getName());
        }

        return names;
    }

    public boolean contains(String name) {
        return children.containsKey(name);
    }

    public void add(IndividualConfig config) {
        children.put(config.getName(), config);
    }

    public IndividualConfig get(String name) {
        return children.get(name);
    }

    public IndividualConfig get(int i) {
        return new ArrayList<>(children.values()).get(i);
    }

    public int size() {
        return children.size();
    }

    public Iterator<IndividualConfig> iterator() {
        return children.values().iterator();
    }

    public void delete(String i) {
        children.remove(i);
    }

    public List<IndividualConfig> getArray() {
        return new ArrayList<>(children.values());
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
