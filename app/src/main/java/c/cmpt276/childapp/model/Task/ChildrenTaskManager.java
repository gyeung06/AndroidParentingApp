package c.cmpt276.childapp.model.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChildrenTaskManager implements Iterable<ChildrenTask> {
    private List<ChildrenTask> tasks = new ArrayList<>();
    private static ChildrenTaskManager instance;
    public static ChildrenTaskManager getInstance() {
        if (instance == null) {
            instance = new ChildrenTaskManager();
        }
        return instance;
    }
    private ChildrenTaskManager(){}


    public void addTask(ChildrenTask task){
        tasks.add(task);
    }

    public void removeTask(ChildrenTask task){
        tasks.remove(task);
    }

    public void editTask(int index, ChildrenTask task){
        tasks.set(index,task);
    }

    @Override
    public Iterator<ChildrenTask> iterator() {
        return tasks.iterator();
    }
}
