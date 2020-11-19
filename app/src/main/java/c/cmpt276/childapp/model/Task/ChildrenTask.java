package c.cmpt276.childapp.model.Task;

public class ChildrenTask {
    private String taskName;
    private String childName;


    public ChildrenTask(String taskName, String childName) {
        this.taskName = taskName;
        this.childName = childName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }
}
