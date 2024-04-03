package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, SubTask> subTasks;
    private int seq = 0;

    public TaskManager() {
        this.tasks = new HashMap<>();
    }

    public Task create(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    private int generateId() {
        return seq++;
    }

    public Task get (int id){
        return tasks.get(id);
    }
    public void update (Task task) {
        tasks.put(task.getId(), task);
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
    public void removeAllTasks() {

        tasks.clear();
    }
    public void removeTask(int id) {
        tasks.remove(id);
    }

    public Epic createEpic(Epic epic) {
        epic.setId(generateId());
        tasks.put(epic.getId(), epic);
        return epic;
    }

    public void updateEpic (Epic epic) {
      Epic saved = epics.get(epic.getId());
      if (saved == null){
          return;
      }
      saved.setName(epic.getName());
      saved.setDescription(epic.getDescription());
     }
    private void updateStatusEpic(int epicId) {
        Epic epic = epicMap.get(epicId);
        if (epic != null) {
            boolean allSubtasksDone = true;
            for (int subtaskId : epic.getIdsSubtask()) {
                Subtask subtaskInEpic = subtaskMap.get(subtaskId);
                if (subtaskInEpic.getStatus() != StatusTask.DONE) {
                    allSubtasksDone = false;
                    break;
                }
            }
            if (allSubtasksDone) {
                epic.setStatus(StatusTask.DONE);
            } else {
                epic.setStatus(StatusTask.IN_PROGRESS);
            }
            updateEpic(epic);
        }
    }

    private void caculateStatus(Epic epic) {
        Status status = Status.NEW;
        epic.setStatus(status);
    }

}
