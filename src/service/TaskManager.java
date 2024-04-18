package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getTasksList();

    List<Epic> getEpicList();

    List<SubTask> getSubtaskList();

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubtasks();

    void removeTaskId(int id);

    void removeEpicId(int id);

    void removeSubTaskId(int id);

    Task createTask(Task task);

    Epic createEpic(Epic epic);

    SubTask createSubTask(SubTask subTask);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubTask(SubTask subTask);

    List<SubTask> getSubTasksEpicsIds(int epicId);

    Task get(int id);

    Epic getEpicById(int id);

    List<Task> getHistory();
}
