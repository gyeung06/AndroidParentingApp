package c.cmpt276.childapp.model.config;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * singleton class for storing all children's config
 */
public class ChildrenConfigCollection implements Iterable<IndividualConfig> {
    private static ChildrenConfigCollection collection = new ChildrenConfigCollection();
    private static List<IndividualConfig> children;
    private static Gson gson = new Gson();

    private ChildrenConfigCollection() {
        children = new ArrayList<>();
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
}
