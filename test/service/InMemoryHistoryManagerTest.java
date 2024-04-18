package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    static TaskManager taskManager;
    static Epic epic1;
    static SubTask subtask1;
    static Task task1;



    @BeforeEach
    void BeforeEach() {
        taskManager = Managers.getDefault();

        taskManager.removeEpicId(epic1.getId());
        taskManager.removeTaskId(task1.getId());
        epic1 = new Epic("Test1", "Test1", Status.NEW, 0);
        taskManager.createEpic(epic1);
        subtask1 = new SubTask("Test2", "Test2", subtask1.getStatus(), epic1.getId());
        task1 = new Task("Test3", "Test3");
        taskManager.createSubTask(subtask1);
        taskManager.createTask(task1);
    }


    @Test
    void taskFieldsAreUnchanged() {
        Task taskUnchanged = new Task("TestUnchanged", "TestUnchanged");

        String nameUnchanged = taskUnchanged.getName();
        String descriptionUnchanged = taskUnchanged.getDescription();
        Status statusUnchanged = taskUnchanged.getStatus();

        assertEquals(nameUnchanged, taskUnchanged.getName(), "Имя не совпадает");
        assertEquals(descriptionUnchanged, taskUnchanged.getDescription(), "Описание не совпадает");
        assertEquals(statusUnchanged, taskUnchanged.getStatus(), "Статус не совпадает");
    }

    @Test
    void subtaskFieldsAreUnchanged() {
        SubTask subtaskUnchanged = new SubTask("TestUnchanged", "TestUnchanged",  subtask1.getStatus(), epic1.getId());

        String nameUnchanged = subtaskUnchanged.getName();
        String descriptionUnchanged = subtaskUnchanged.getDescription();
        Status statusUnchanged = subtaskUnchanged.getStatus();

        assertEquals(nameUnchanged, subtaskUnchanged.getName(), "Имя не совпадает");
        assertEquals(descriptionUnchanged, subtaskUnchanged.getDescription(), "Описание не совпадает");
        assertEquals(statusUnchanged, subtaskUnchanged.getStatus(), "Статус не совпадает");
    }

    @Test
    void epicFieldsAreUnchanged() {
        Epic epicUnchanged = new Epic("TestUnchanged", "TestUnchanged", Status.NEW, epic1.getId(),epic1.getSubTasksIds());

        String nameUnchanged = epicUnchanged.getName();
        String descriptionUnchanged = epicUnchanged.getDescription();
        Status statusUnchanged = epicUnchanged.getStatus();

        assertEquals(nameUnchanged, epicUnchanged.getName(), "Имя не совпадает");
        assertEquals(descriptionUnchanged, epicUnchanged.getDescription(), "Описание не совпадает");
        assertEquals(statusUnchanged, epicUnchanged.getStatus(), "Статус не совпадает");
    }

    @Test
    void EpicDoesNotConflict() {
        Epic newEpic = new Epic(epic1.getName(), epic1.getDescription(),Status.NEW, epic1.getId(), epic1.getSubTasksIds());
        taskManager.updateEpic(newEpic);
        assertEquals(epic1, taskManager.get(newEpic.getId()), "Эпики конфликтуют");
    }

    @Test
    void SubtaskDoesNotConflict() {
        SubTask newSubtask = new SubTask(subtask1.getName(), subtask1.getDescription(), subtask1.getStatus(), subtask1.getEpicId());
        taskManager.updateSubTask(newSubtask);
        assertEquals(subtask1, taskManager.getSubTasksEpicsIds(newSubtask.getId()), "Сабтаски конфликтуют");
    }

    @Test
    void TaskDoesNotConflict() {
        Task newTask = new Task(task1.getName(), task1.getDescription());
        taskManager.updateTask(newTask);
        assertEquals(task1, taskManager.get(newTask.getId()), "Задачи конфликтуют");
    }

    @Test
    void historymanagerSavesPreviousVersionOfTask() {
        Task historyTask = taskManager.get(task1.getId());

        Task newTask = new Task("New name", "New description");
        taskManager.updateTask(newTask);

        String name = historyTask.getName();
        String description = historyTask.getDescription();
        Status status = historyTask.getStatus();

        assertNotEquals(name, taskManager.get(task1.getId()).getName());
        assertNotEquals(description, taskManager.get(task1.getId()).getDescription());
        assertNotEquals(status, taskManager.get(task1.getId()).getStatus());
    }

    @Test
    void historymanagerSavesPreviousVersionOfSubtask() {
        SubTask historySubtask = taskManager.getSubtaskList().get(subtask1.getId());

        SubTask newSubtask = new SubTask("New name", "New description",  Status.DONE, epic1.getId());
        taskManager.updateSubTask(newSubtask);

        String name = historySubtask.getName();
        String description = historySubtask.getDescription();
        Status status = historySubtask.getStatus();

        assertNotEquals(name, taskManager.get(subtask1.getId()).getName(), "Старая версия имени");
        assertNotEquals(description, taskManager.get(subtask1.getId()).getDescription(), "Старая версия описания");
        assertNotEquals(status, taskManager.get(subtask1.getId()).getStatus(), "Старая версия статуса");
    }

    @Test
    void historymanagerSavesPreviousVersionOfEpic() {

        Epic historyEpic = taskManager.getEpicById(epic1.getId());

        Epic newEpic = new Epic("New name", "New description", Status.NEW, taskManager.getEpicById(epic1.getId()).getSubTasksIds().size());
        taskManager.updateEpic(newEpic);

        SubTask newSubtask = new SubTask("Some name", "Some description", Status.NEW, epic1.getId());

        taskManager.createSubTask(newSubtask);

        SubTask updatedSubtask = new SubTask("Some name", "Some description", Status.DONE, epic1.getId());
        taskManager.updateSubTask(updatedSubtask);

        String name = historyEpic.getName();
        String description = historyEpic.getDescription();
        Status status = historyEpic.getStatus();
        List<Integer> subtasksId = historyEpic.getSubTasksIds();

        assertNotEquals(name, taskManager.get(epic1.getId()).getName(), "Старая версия имени");
        assertNotEquals(description, taskManager.get(epic1.getId()).getDescription(), "Старая версия описания");
        assertNotEquals(status, taskManager.get(epic1.getId()).getStatus(), "Старая версия статуса");
       }

    @Test
    void addNewTask() {

        int numberOfTasks = taskManager.getTasksList().size();

        Task task = new Task("Test addNewTask", "Test addNewTask description");
        final int taskId = taskManager.createTask(task).getId();
        final Task savedTask = taskManager.get(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasksList();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(numberOfTasks + 1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getLast(), "Задачи не совпадают.");
    }

    @Test
    void addNewEpic() {

        int numberOfEpics = taskManager.getEpicList().size();

        Epic epic = new Epic("Test addNewTask", "Test addNewTask description", Status.NEW, epic1.getId(), epic1.getSubTasksIds());
        final int epicId = taskManager.createEpic(epic).getId();
        final Epic savedEpic = taskManager.getEpicById(epicId);

        assertNotNull(savedEpic, "Эпик не найден.");
        assertEquals(epic, savedEpic, "Эпики не совпадают.");

        final List<Epic> epics = taskManager.getEpicList();

        assertNotNull(epics, "Эпики не возвращаются.");
        assertEquals(numberOfEpics + 1, epics.size(), "Неверное количество эпиков.");
        assertEquals(epic, epics.getLast(), "Эпики не совпадают.");
    }

    @Test
    void addNewSubtask() {

        int numberOfSubtasks = taskManager.getSubtaskList().size();

        SubTask subTask = new SubTask("Test addNewTask", "Test addNewTask description", Status.NEW, epic1.getId());
        final SubTask subTaskId = taskManager.createSubTask(subTask);
        final int savedSubtask = taskManager.getSubtaskList().size();

        assertNotNull(savedSubtask, "Сабтаска не найдена.");
        assertEquals(subTask, savedSubtask, "Сабтаски не совпадают.");

        final List<SubTask> subtasks = taskManager.getSubtaskList();

        assertNotNull(subtasks, "Сабтаски не возвращаются.");
        assertEquals(numberOfSubtasks + 1, subtasks.size(), "Неверное количество сабтасок.");
        assertEquals(subTask, subtasks.getLast(), "сабтаски не совпадают.");
    }

    @Test
    void removeTasks() {
        taskManager.removeTaskId(task1.getId());
        assertEquals(0, taskManager.getTasksList(), "Задачи не удалены");
    }

    @Test
    void removeEpics() {
        taskManager.removeEpicId(epic1.getId());
        assertEquals(0, taskManager.getEpicList().size(), "Эпики не удалены");
        assertEquals(0, taskManager.getSubtaskList().size(), "Сабтаски не удалены");
    }

    @Test
    void removeSubtasks() {
        taskManager.removeSubTaskId(subtask1.getId());
        assertEquals(0, taskManager.getSubtaskList().size(), "Эпики не удалены");
    }

    @Test
    void updateEpicStatus() {

        Epic epic = new Epic("Test addNewTask", "Test addNewTask description",Status.NEW, epic1.getId(),epic1.getSubTasksIds());
        final Epic epicId = taskManager.createEpic(epic);

        SubTask subTask = new SubTask("Test new subtask", "Test new subtask", Status.NEW, epicId.getId());

        final SubTask subTaskId = taskManager.createSubTask(subTask);
        final List<Epic> savedEpic = taskManager.getEpicList();

        assertEquals(Status.NEW, savedEpic.getClass(), "Статус не соответствует ожидаемому");

        subTask = new SubTask("Test new subtask", "Test new subtask", Status.IN_PROGRESS, epicId.getId());

        taskManager.updateSubTask(subTask);

        assertEquals(Status.IN_PROGRESS, savedEpic.getClass(), "Статус не соответствует ожидаемому");

        subTask = new SubTask("Test new subtask", "Test new subtask", Status.DONE, epicId.getId());

        taskManager.updateSubTask(subTask);

        assertEquals(Status.DONE, savedEpic.getClass(), "Статус не соответствует ожидаемому");
    }

    @Test
    void getSubtasksOfEpic() {
        SubTask[] subtasks = {subtask1};
        assertArrayEquals(subtasks, taskManager.getSubTasksEpicsIds(epic1.getId()).toArray(), "Сабтаски эпика не совпадают");
    }
}