package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, SubTask> subTasks;
    private int counter = 0;


    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
    }

    public List<Task> getTasksList() {
        return new ArrayList<>(tasks.values());

    }

    public List<Epic> getEpicList() {
        return new ArrayList<>(epics.values());

    }

    public List<SubTask> getSubtaskList() {
        return new ArrayList<>(subTasks.values());

    }

    private int generateId() {
        return ++counter;
    }


    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    public void removeEpic(int id) {
        Epic removeEpic = epics.remove(id);
        List<SubTask> removeEpicsSubtasks = removeEpic.getSubTask();
        for (SubTask subtask : removeEpicsSubtasks) {
            epics.remove(subtask.getId());
        }
        removeEpicsSubtasks.clear();
    }

    public void removeSubTask(int id) {
        for (SubTask subtask : subTasks.values()) {
            Epic epic = subtask.getEpic();
            List<SubTask> EpicSubtasks = epic.getSubTask();
            EpicSubtasks.clear();
            updateStatus(epic);
        }
        subTasks.clear();
    }

    public Task create(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }


    public Epic createEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }


    public SubTask createSubtask(SubTask subtask) {
        subtask.setId(generateId());
        subtask.getEpic().getSubTask().add(subtask);
        subTasks.put(subtask.getId(), subtask);
        updateStatus(subtask.getEpic());
        return subtask;
    }


    public void update(Task task) {
        tasks.put(task.getId(), task);
    }


    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());
        if (epics == null) {
            return;
        }
        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());
    }

    public void updateSubTask(SubTask subTask) {
        Epic epic = subTask.getEpic();
        Epic savedEpic = epics.get(epic.getId());
        if (savedEpic == null) {
            return;
        }
        savedEpic.getSubTask().add(subTask);
        updateStatus(epic);
    }

    public Task get(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public List<SubTask> getSubtasksById(int id) {
        return subTasks.get(id).getEpic().getSubTask();

    }

    private void updateStatus(Epic epic) {
        List<SubTask> tasks = epic.getSubTask();
        int tasksSize = tasks.size();
        int StatusNEW = 0;
        int StatusDONE = 0;

        for (SubTask subTask : tasks) {
            if (subTask.getStatus() == Status.NEW) {
                StatusNEW++;
            } else if (subTask.getStatus() == Status.DONE) {
                StatusDONE++;
            }
        }
        if (tasksSize == 0 || tasksSize == StatusNEW) {
            epic.setStatus(Status.NEW);
        } else if (tasksSize == StatusDONE) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}


