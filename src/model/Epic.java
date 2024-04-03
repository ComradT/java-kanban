package model;


import java.util.HashSet;
import java.util.Set;

public class Epic extends Task {

    private final Set <Integer> subTasks;

    public Epic(String name, String description, Status status) {
        super(name,description, status);
        this.subTasks = new HashSet<>();
    }

    public Set<Integer> getSubTasks() {
        return subTasks;
    }

    public void addTask(int subTasksId) {
        subTasks.add(subTasksId);
        }

    public void updateStatus(){
        status = Status.NEW;
    }


}
