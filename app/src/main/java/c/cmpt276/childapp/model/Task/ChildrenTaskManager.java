package c.cmpt276.childapp.model.Task;

import java.util.ArrayList;
import java.util.List;

public class ChildrenTaskManager {
    List<ChildrenTask> tasks;

    public ChildrenTaskManager() {
        tasks = new ArrayList<>();
    }

    public ChildrenTask getTask(int i) {
        return tasks.get(i);
    }

    public void addTask(ChildrenTask task) {
        tasks.add(task);
    }

    public void removeTask(int i) {
        tasks.remove(i);
    }

    public int size() {
        return tasks.size();
    }

    /**
     * @param taskTitle
     * @param toIgnoreLocation can be set to -1 to ignore nothing
     * @return
     */
    public boolean contains(String taskTitle, int toIgnoreLocation) {
        for (int i = 0; i < size(); i++) {
            if (tasks.get(i).getTaskDescription().equalsIgnoreCase(taskTitle)) {
                if (i == toIgnoreLocation) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    public List<ChildrenTask> getList() {
        return tasks;
    }
}
