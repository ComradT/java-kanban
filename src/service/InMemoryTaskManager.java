package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.List;
import java.util.Map;


public class InMemoryTaskManager implements TaskManager{
    private final Map<Integer, Task> tasksMap; // интерфейс Map
    private final Map<Integer, SubTask> subTasksMap; // интерфейс Map
    private final Map<Integer, Epic> epicsMap; // интерфейс Map
    private int counter = 0;
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.tasksMap = new HashMap<>();
        this.epicsMap = new HashMap<>();
        this.subTasksMap = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
    }

    @Override
    public List<Task> getTasksList() {
        return new ArrayList<>(tasksMap.values());
    }

    @Override
    public List<Epic> getEpicList() {
        return new ArrayList<>(epicsMap.values());
    }

    @Override
    public List<SubTask> getSubtaskList() {
        return new ArrayList<>(subTasksMap.values());
    }

    @Override
    public void removeAllTasks() {
        tasksMap.clear();
    }


    @Override
    public void removeAllEpics() {
        epicsMap.clear();
        subTasksMap.clear();
    }

    @Override
    public void removeAllSubtasks() {
        subTasksMap.clear();
        for (Epic epic : epicsMap.values()) {
            updateStatus(epic.getId());
        }
    }

    @Override
    public void removeTaskId(int id) {
        tasksMap.remove(id);
    }

    @Override
    public void removeEpicId(int id) {
        Epic removeEpic = epicsMap.remove(id);
        if (removeEpic != null) {
            for (int subtaskId : removeEpic.getSubTasksIds()) {
                subTasksMap.remove(subtaskId);
            }
        }
    }

    @Override
    public void removeSubTaskId(int id) { // исправил логику перерасчета статуса соответствующего эпика
        SubTask removeSubTask = subTasksMap.remove(id);
        if (removeSubTask != null) {
            Epic epic = getEpicById(removeSubTask.getEpicId());
            if (epic != null) {
                epic.getSubTasksIds().remove(Integer.valueOf(id));
                updateStatus(epic.getId());
            }
        }
    }

    @Override
    public Task createTask(Task task) { // унифицировал все методы создания
        task.setId(generateCounter());
        tasksMap.put(task.getId(), task);
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(generateCounter());
        epicsMap.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        Epic epic = getEpicById(subTask.getEpicId());
        if (epic != null) {
            subTask.setId(generateCounter());
            subTasksMap.put(subTask.getId(), subTask);
            epic.addSubTasksIds(subTask.getId());
            if (epic.getStatus() == Status.DONE) {
                epic.setStatus(Status.IN_PROGRESS);
                updateEpic(epic);
            }
            return subTask;
        }
        return null;
    }

    @Override
    public void updateTask(Task task) {
        if (!tasksMap.containsKey(task.getId())) {
            return;
        }
        tasksMap.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic saved = epicsMap.get(epic.getId());
        if (saved == null) {
            return;
        }
        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());
        epicsMap.put(epic.getId(), saved);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if (subTasksMap.containsKey(subTask.getId())) {
            subTasksMap.put(subTask.getId(), subTask);
            updateStatus(subTask.getEpicId());
        }
    }

    @Override
    public List<SubTask> getSubTasksEpicsIds(int epicId) {
        Epic epic = epicsMap.get(epicId);
        if (epic != null) {
            List<SubTask> subtasks = new ArrayList<>();
            for (int subtaskId : epic.getSubTasksIds()) {
                SubTask subtask = subTasksMap.get(subtaskId);
                if (subtask != null) {
                    subtasks.add(subtask);
                }
            }
            return subtasks;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Task get(int id) {
        return tasksMap.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        return epicsMap.get(id);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
    private int generateCounter() {
        return ++counter;
    }

    private void updateStatus(int epicId) {
        Epic epic = epicsMap.get(epicId);
        if (epic != null) {
            int tasksSize = epicsMap.size();
            int StatusNEW = 0;
            int StatusDONE = 0;

            for (int subTaskId : epic.getSubTasksIds()) {
                SubTask subTaskInEpic = subTasksMap.get(subTaskId);
                if (subTaskInEpic.getStatus() == Status.NEW) {
                    StatusNEW++;
                } else if (subTaskInEpic.getStatus() == Status.DONE) {
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
}
