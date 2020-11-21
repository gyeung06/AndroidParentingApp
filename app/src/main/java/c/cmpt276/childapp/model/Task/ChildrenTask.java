package c.cmpt276.childapp.model.Task;

import java.util.ArrayList;
import java.util.List;

import c.cmpt276.childapp.model.config.ChildrenConfigCollection;

public class ChildrenTask {
    private String taskDescription;
    private List<String> nextToDoChildList;
    private List<String> doneChildList;

    public ChildrenTask(String taskDescription) {
        this.taskDescription = taskDescription;
        nextToDoChildList = new ArrayList<>();
        doneChildList = new ArrayList<>();
        ChildrenConfigCollection configs = ChildrenConfigCollection.getInstance();
        for (int i = 0; i < configs.size(); i++) {
            if (configs.get(i).getTaskEnabled()) {
                nextToDoChildList.add(configs.get(i).getName());
            }
        }
    }

    /**
     * @return the next child in the queue
     */
    public String popNextChild() {
        if (nextToDoChildList.size() == 0) {
            return null;
        }
        doneChildList.add(nextToDoChildList.remove(0));
        if (nextToDoChildList.size() == 0) {
            return null;
        }
        return nextToDoChildList.get(0);
    }

    public String getNextChild() {
        if (nextToDoChildList.size() == 0) {
            return null;
        }
        return nextToDoChildList.get(0);
    }

    public List<String> getDoneChildren() {
        return doneChildList;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String str) {
        taskDescription = str;
    }
}
