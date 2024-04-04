package model;


import java.util.ArrayList;
import java.util.List;


public class Epic extends Task {

    private final List<SubTask> subTasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
    }

    public List<SubTask> getSubTask() {
        return subTasks;
    }

    public void addTask(SubTask subTasks) {
        this.subTasks.add(subTasks);
    }

    public void updateStatus() {
        status = Status.NEW;
    }

    public String toString() {
        return "Subtask{" +
                ", name='" + super.getName() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                ", status=" + super.getStatus() +
                "id=" + super.getId() +
                '}';
    }

}



