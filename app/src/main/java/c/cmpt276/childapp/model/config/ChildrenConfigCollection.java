package c.cmpt276.childapp.model.config;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * singleton class for storing all children's config
 */
public class ChildrenConfigCollection implements Iterable<IndividualConfig> {
    private List<IndividualConfig> children;
    private static ChildrenConfigCollection collection = new ChildrenConfigCollection();
    public static ChildrenConfigCollection getInstance(){
        return collection;
    }
    public static ChildrenConfigCollection loadWithJSONObject(String json){
        Gson gson = new Gson();
        collection = gson.fromJson(json,ChildrenConfigCollection.class);
        return collection;
    }
    public String getJSON(){
        Gson gson = new Gson();
        return gson.toJson(collection);
    }
    public void add(IndividualConfig config){
        children.add(config);
    }
    public IndividualConfig get(int i){
        return children.get(i);
    }
    public int size(){
        return children.size();
    }
    private ChildrenConfigCollection(){
        children = new ArrayList<>();
    }
    public Iterator<IndividualConfig> iterator() {
        return children.iterator();
    }
}
