package service;

import enums.Status;
import interfaces.TaskManager;
import model.Epic;
import model.SubTask;

import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;


    @Test
    @DisplayName("проверка статуса и работы времени в задаче")
    void testStatusAndTimeEpics() {
        Epic epic = taskManager.createEpic(new Epic("epic", "descriptionEpic"));
        SubTask subTask = taskManager.createSubTask(new SubTask("subTask", "descSubTask",
                Status.NEW, LocalDateTime.now(), 30, 100));
        assertEquals(0, taskManager.getSubtaskList().size());
        subTask = taskManager.createSubTask(new SubTask("subTask", "descSubTask",
                Status.NEW, LocalDateTime.now(), 30, epic.getId()));
        assertEquals(1, taskManager.getSubtaskList().size());
        assertEquals(Status.NEW, taskManager.getEpicById(epic.getId()).getStatus());
        SubTask subTaskNew = taskManager.createSubTask(new SubTask("subTaskNew",
                "descriptionSubTaskNew",
                Status.DONE, LocalDateTime.now().plusDays(1), 30, epic.getId()));
        assertEquals(Status.IN_PROGRESS, taskManager.getEpicById(epic.getId()).getStatus());
        subTask.setStatus(Status.DONE);
        taskManager.updateSubTask(subTask);
        assertEquals(epic.getStatus(), taskManager.getEpicById(epic.getId()).getStatus());
        assertEquals(subTask.getStartTime(), taskManager.getEpicById(epic.getId()).getStartTime());
        assertEquals(subTaskNew.getEndTime(), taskManager.getEpicById(epic.getId()).getEndTime());
    }


    @Test
    @DisplayName("тест работы счетчика counter для Task")
    void testCreateTaskShouldAddTaskToTasksList() {
        int count = taskManager.getTasksList().size();
        taskManager.createTask(new Task("test", "desc", Status.NEW));
        int count2 = taskManager.getTasksList().size();
        assertEquals(1, (count + count2));
    }


    @Test
    @DisplayName("тест работы счетчика counter для Epic")
    void testCreateEpicShouldAddEpicToEpicsList() {
        int count = taskManager.getEpicList().size();
        taskManager.createEpic(new Epic("test", "desc", 1));
        int count2 = taskManager.getEpicList().size();
        assertEquals(1, (count + count2));
    }

    @Test
    @DisplayName("тест работы счетчика counter для SubTask")
    void testCreateSubTaskShouldAddSubTaskToSubTasksList() {
        int count = taskManager.getSubtaskList().size();
        Epic epic = new Epic("test", "desc", 1);
        taskManager.createEpic(epic);
        taskManager.createSubTask(new SubTask("test", "desc", Status.NEW, 1));
        int count2 = taskManager.getSubtaskList().size();
        assertEquals(1, (count + count2));
    }

    @Test
    @DisplayName("тест работы счетчика counter по ID для Task")
    void testCreateTaskShouldCreatedCounterId() {
        Task task = new Task("test", "desc", Status.NEW);
        taskManager.createTask(task);
        assertEquals(1, task.getId());
    }

    @Test
    @DisplayName("сравнение ID для Task")
    void testGetTaskByIdShouldReturnTaskById() {
        Task task = new Task("test", "desc", Status.NEW, 1);
        taskManager.createTask(task);
        Task task1 = new Task("test", "desc", Status.NEW, 2);
        taskManager.createTask(task1);
        assertEquals(task, taskManager.getTaskById(task.getId()));
        assertEquals(task1, taskManager.getTaskById(task1.getId()));
    }

    @Test
    @DisplayName("сравнение ID для Epic")
    void testGetEpicByIdShouldReturnEpicById() {
        Epic epic = new Epic("test", "desc", 1);
        taskManager.createEpic(epic);
        Epic epic1 = new Epic("test", "desc", 2);
        taskManager.createTask(epic1);
        assertEquals(epic, taskManager.getEpicById(epic.getId()));
        assertEquals(epic1, taskManager.getTaskById(epic1.getId()));
    }

    @Test
    @DisplayName("сравнение ID для SubTask")
    void testGetSubTaskByIdShouldReturnSubTaskById() {
        Epic epic = new Epic("test", "desc");
        Epic subTask = new Epic("test", "desc", Status.NEW, 1);
        Epic subTask1 = new Epic("test", "desc", Status.NEW, 1);
        taskManager.createEpic(epic);
        taskManager.createEpic(subTask);
        taskManager.createEpic(subTask1);
        taskManager.createTask(epic);
        assertEquals(subTask, taskManager.getEpicById(subTask.getId()));
        assertEquals(subTask1, taskManager.getEpicById(subTask1.getId()));
    }

    @Test
    @DisplayName("удаление всех Task")
    void testRemoveTasksShouldDeleteAllTasksFromTasksList() {
        Task task = new Task("test", "desc", Status.NEW);
        taskManager.createTask(task);
        taskManager.removeAllTasks();
        assertEquals(0, taskManager.getTasksList().size());
    }

    @Test
    @DisplayName("Task равен с Task в TaskManager")
    void testCreateTaskTaskWithIdEqualsWithTaskInTaskManager() {
        Task task1 = new Task("test", "desc", Status.NEW);
        Task task2 = new Task("test", "desc", Status.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        assertEquals(1, task1.getId());
        assertEquals(2, task2.getId());
    }

    @Test
    @DisplayName("Task должен быть равен с Task в TaskManager при обновлении")
    void testUpdateTaskTaskInTaskManagerShouldBeEqualsWithUpdatedTask() {
        Task task1 = new Task("test", "desc", Status.NEW);
        taskManager.createTask(task1);
        int id = task1.getId();
        Task task2 = new Task("test", "desc1", Status.IN_PROGRESS, id);
        taskManager.updateTask(task2);
        Task taskInManager = taskManager.getTaskById(id);
        assertEquals(task2.getName(), taskInManager.getName());
        assertEquals(task2.getDescription(), taskInManager.getDescription());
    }

    @Test
    @DisplayName("удаление Task по ID")
    void testCreateTaskNewTaskShouldBeEqualsWithTaskInManager() {
        Task task = new Task("test", "desc", Status.NEW);
        taskManager.createTask(task);
        Task taskInManager = taskManager.getTaskById(task.getId());
        assertEquals(task.getName(), taskInManager.getName());
        assertEquals(task.getDescription(), taskInManager.getDescription());
    }

    @Test
    @DisplayName("Epic должен быть равен с Epic в TaskManager при создании")
    void testCreateEpicNewEpicShouldBeEqualsWithEpicInManager() {
        Epic epic = new Epic("test", "desc");
        taskManager.createEpic(epic);
        Task EpicInManager = taskManager.getEpicById(epic.getId());
        assertEquals(epic.getName(), EpicInManager.getName());
        assertEquals(epic.getDescription(), EpicInManager.getDescription());
    }

    @Test
    @DisplayName("SubTask должен быть равен с SubTask в TaskManager при создании")
    void testCreateSubTaskNewSubtaskShouldBeEqualsWithSubTaskInManager() {
        Epic epic = new Epic("test", "desc");
        SubTask subtask = new SubTask("test", "desc", Status.NEW, 1);
        taskManager.createEpic(epic);
        taskManager.createSubTask(subtask);
        SubTask SubtaskInManager = taskManager.getSubTaskById(subtask.getId());
        assertEquals(subtask.getName(), SubtaskInManager.getName());
        assertEquals(subtask.getDescription(), SubtaskInManager.getDescription());
        assertEquals(subtask.getEpicId(), SubtaskInManager.getEpicId());
    }

    @Test
    @DisplayName("неизменность Task (по всем полям) при добавлении задачи в менеджер")
    public void testCreateTaskCheckEveryField() {
        Task task = taskManager.createTask(new Task("test", "desc", Status.NEW, 1,
                LocalDateTime.of(2024, 5, 25, 10, 0),
                30));
        assertEquals(1, task.getId());
        assertEquals("test", task.getName());
        assertEquals("desc", task.getDescription());
        assertEquals(Status.NEW, task.getStatus());
        assertEquals(LocalDateTime.of(2024, 5, 25, 10, 0), task.getStartTime());
        assertEquals(30, task.getDuration());
    }

    @Test
    @DisplayName("неизменность Epic (по всем полям) при добавлении задачи в менеджер")
    public void testCreateEpicCheckEveryField() {
        Epic epic = taskManager.createEpic(new Epic("test", "desc", Status.NEW, 1));
        assertEquals(1, epic.getId());
        assertEquals("test", epic.getName());
        assertEquals("desc", epic.getDescription());
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    @DisplayName("неизменность SubTask (по всем полям) при добавлении задачи в менеджер")
    public void testCreateSubTaskCheckEveryField() {
        Epic epic = taskManager.createEpic(new Epic("test", "desc", Status.NEW, 1));
        SubTask subTask = taskManager.createSubTask(new SubTask("test", "desc", Status.NEW, 1,
                1, LocalDateTime.of(2024, 5, 25, 10, 0),
                30));
        assertEquals(1, epic.getId());
        assertEquals(1, subTask.getEpicId());
        assertEquals("test", subTask.getName());
        assertEquals("desc", subTask.getDescription());
        assertEquals(Status.NEW, subTask.getStatus());
        assertEquals(LocalDateTime.of(2024, 5, 25, 10, 0),
                subTask.getStartTime());
        assertEquals(30, subTask.getDuration());
    }


    @Test
    @DisplayName("проверка на ID")
    void testShouldCreateIdCheckById() {
        Task task = new Task("task", "descTask", Status.NEW);
        Epic epic = new Epic("epic", "descEpic");
        Epic epic1 = new Epic("epic", "descEpic");
        SubTask subTask = new SubTask("epic", "descEpic", Status.NEW, 1);
        taskManager.createTask(task);
        taskManager.createEpic(epic);
        taskManager.createEpic(epic1);
        int IdEpic = epic.getId();
        int id = task.getId();
        int IdEpic1 = epic1.getId();
        taskManager.getEpicById(IdEpic1);
        assertEquals(task, taskManager.getTaskById(task.getId()));
        Assertions.assertNotEquals(subTask, epic);
        Assertions.assertNotEquals(IdEpic, id);
        Assertions.assertNotEquals(task, epic.getId(id));
        Assertions.assertNotEquals(epic.getId(IdEpic1), epic1.getId(IdEpic));

    }

    @Test
    @DisplayName("Task нельзя добавить в самого себя в виде подзадачи")
    void testShouldCreateIdAndSaveTaskById() {
        Task task = new Task("epic", "desc", Status.NEW);
        final int taskId = taskManager.createTask("epic", "desc");
        final Task savedTask = taskManager.getTaskById(taskId);
        Assertions.assertNotNull(savedTask);
        Assertions.assertNotEquals(task, savedTask);
        final List<Task> tasks = taskManager.getTasksList();
        Assertions.assertNotNull(tasks);
        assertEquals(1, tasks.size());
        Assertions.assertNotEquals(task, tasks.getFirst());
    }

    @Test
    @DisplayName("Epic нельзя добавить в самого себя в виде подзадачи")
    void testShouldCreateIdAndSaveEpicById() {
        Epic epic = new Epic("epic", "desc");
        final int epicId = taskManager.createEpic("epic", "desc");
        final Epic savedEic = taskManager.getEpicById(epicId);
        Assertions.assertNotNull(savedEic);
        Assertions.assertNotEquals(epic, savedEic);
        final List<Epic> epics = taskManager.getEpicList();
        Assertions.assertNotNull(epics);
        assertEquals(1, epics.size());
        Assertions.assertNotEquals(epic, epics.getFirst());
    }

    @Test
    @DisplayName("SubTask нельзя сделать своим же эпиком")
    void testShouldCreateIdAndSaveSubTaskById() {
        SubTask subTask = new SubTask("subTask", "desc", Status.NEW, 1);
        Epic epic = new Epic("epic", "desc");
        final int subTaskId = taskManager.createSubTask("task", "desc", epic);
        final int epicId = taskManager.createEpic("task", "desc");
        final int savedSubTask = taskManager.createEpic("subTask", "desc");
        Assertions.assertNotEquals(subTaskId, savedSubTask);
        Assertions.assertNotEquals(epicId, savedSubTask);
        final List<SubTask> subTasks = taskManager.getSubtaskList();
        Assertions.assertNotNull(subTasks);
        assertEquals(1, subTasks.size());
        Assertions.assertNotEquals(subTask, taskManager.getSubtaskList());
    }

    @Test
    @DisplayName("Проверка Exception")
    public void testException() {
        taskManager.createTask(new Task("test", "desc", Status.NEW));
        taskManager.createTask(new Task("test2", "desc", Status.DONE));
        taskManager.createTask(new Task("test3", "desc", Status.IN_PROGRESS));

        taskManager.createEpic(new Epic("test", "desc"));
        taskManager.createEpic(new Epic("test1", "desc"));
        taskManager.createEpic(new Epic("test2", "desc"));

        taskManager.createSubTask(new SubTask("test", "desc", Status.NEW, 4));
        taskManager.createSubTask(new SubTask("test1", "desc", Status.NEW, 5));
        taskManager.createSubTask(new SubTask("test2", "desc", Status.NEW, 6));
        taskManager.createSubTask(new SubTask("test", "desc", Status.NEW, 1, 5,
                LocalDateTime.of(2024, 5, 25, 15, 0),
                30));
        taskManager.createSubTask(new SubTask("test", "desc", Status.NEW, 2, 6,
                LocalDateTime.of(2024, 5, 25, 20, 0),
                30));
        taskManager.createSubTask(new SubTask("test", "desc", Status.NEW, 4, 7,
                LocalDateTime.of(2024, 5, 25, 23, 0),
                30));
        Assertions.assertThrows(RuntimeException.class, () -> taskManager.createSubTask(new SubTask("test",
                        "desc", Status.NEW, 1, 5, LocalDateTime.of(2024, 5,
                        25, 20, 10),
                        30)),
                "Пересечение не должно приводить к исключению");

        Assertions.assertDoesNotThrow(() -> taskManager.createSubTask(new SubTask("test", "desc",
                Status.NEW, 1, 5, LocalDateTime.of(2024, 5, 25, 10, 0),
                30)), "Пересечение не должно приводить к исключению");
    }


}



