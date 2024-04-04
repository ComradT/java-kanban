package model;

public class SubTask extends Task {

    private Epic epic;

    public SubTask(String name, String description, Epic epic, Status status) {
        super(name, description, status);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
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
                ", epic=" + epic +
                '}';
    }

}

