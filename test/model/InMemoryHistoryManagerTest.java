package model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;
import service.TaskManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    public void testAddSubtaskIdToSubtask() {
        Epic epic = new Epic("Test", "desc", Status.NEW,0);
        taskManager.createEpic(epic);
        Epic epic1 = new Epic("Test", "desc", Status.NEW,1);
        taskManager.createEpic(epic1);
        SubTask subTask = new SubTask("Test", "desc", Status.NEW, epic.getId());
        taskManager.createSubTask(subTask);
        SubTask tmpSubtask = new SubTask("Test", "desc", Status.NEW, subTask.getId());
        taskManager.createSubTask(tmpSubtask);
        Assertions.assertEquals(1, taskManager.getTasksList().size());
    }

    /*@Test
    public void testAddAnyTypeOfTasks() {
        Task tmpTask = new Task("Test", "desc", Status.NEW,0);
        Task task = taskManager.createTask(tmpTask);
        Epic tmpEpic = new Epic("Test", "desc", Status.NEW,0);
        Epic epic = taskManager.createEpic(tmpEpic);
        SubTask tmpSubtask = new SubTask("Test", "desc", Status.NEW, epic.getId());
        SubTask subtask = taskManager.createSubTask(tmpSubtask);
        assertEquals(task, taskManager.getTaskById(task.getId()));
        assertEquals(epic, taskManager.getEpicById(epic.getId()));
        assertEquals(subtask, taskManager.getSubTaskById(subtask.getId()));
    }

    @Test
    public void testConflictTasksWithSameId() {
        Task tmpTask = new Task("Test", "desc", Status.NEW);
        Task tmpTask2 = new Task("Test", "desc", Status.NEW, 1);
        Task task = taskManager.createTask(tmpTask);
        Task task2 = taskManager.createTask(tmpTask2);
        assertEquals(2, taskManager.getsTasks().size());
        assertNotEquals(task.getId(), task2.getId());
    }

    @Test
    public void testEqualTask() {
        Task tmpTask = new Task("Test", "desc", Status.NEW, 2);
        Task task = taskManager.createTask(tmpTask);
        assertEquals(tmpTask, task);
    }

    @Test
    public void testHistory() {
        Task tmpTask = new Task("Test", "desc", Status.NEW, 2);
        Task task = taskManager.createTask(tmpTask);
        taskManager.getTaskById(task.getId());
        List<Task> tasks = taskManager.getHistory();
        assertEquals(Status.NEW, tasks.getFirst().getStatus());

        Task tmpTask2 = new Task("Test", "desc", Status.DONE, task.getId());
        taskManager.updateTask(tmpTask2);
        taskManager.getTaskById(task.getId());

        tasks = taskManager.getHistory();
        assertEquals(1, tasks.size());
        assertEquals(Status.DONE, tasks.getFirst().getStatus());

        Epic tmpEpic = new Epic("Epic", "EpicDesc");
        Epic epic = taskManager.createEpic(tmpEpic);
        assertEquals(1, tasks.size());

        Subtask tmpSubtask = new Subtask("Sub", "SubDesc", Status.IN_PROGRESS, epic.getId());
        Subtask subtask = taskManager.createSubTask(tmpSubtask);
        assertEquals(1, tasks.size());

        taskManager.getSubTaskById(subtask.getId());
        tasks = taskManager.getHistory();
        assertEquals(2, tasks.size());
        assertEquals(task.getId(), tasks.get(0).getId());
        assertEquals(subtask.getId(), tasks.get(1).getId());

        taskManager.getEpicById(epic.getId());
        tasks = taskManager.getHistory();
        assertEquals(3, tasks.size());
        assertEquals(task.getId(), tasks.get(0).getId());
        assertEquals(subtask.getId(), tasks.get(1).getId());
        assertEquals(epic.getId(), tasks.get(2).getId());

        taskManager.getTaskById(task.getId());
        tasks = taskManager.getHistory();
        assertEquals(3, tasks.size());
        assertEquals(task.getId(), tasks.get(2).getId());
        assertEquals(subtask.getId(), tasks.get(0).getId());
        assertEquals(epic.getId(), tasks.get(1).getId());

        taskManager.deleteEpicById(epic.getId());
        tasks = taskManager.getHistory();
        assertEquals(1, tasks.size());
        assertEquals(task.getId(), tasks.getFirst().getId());

        taskManager.deleteAllTasks();
        tasks = taskManager.getHistory();
        assertEquals(0, tasks.size());
    }

    @Test
    public void testClearSubtaskIds() {
        List<Task> history = taskManager.getHistory();
        Epic epic = new Epic("Test", "desc");
        epic = taskManager.createEpic(epic);
        Subtask subtask1 = new Subtask("Sub", "subdesc", Status.IN_PROGRESS, epic.getId());
        subtask1 = taskManager.createSubTask(subtask1);
        Subtask subtask2 = new Subtask("Sub2", "subdesc2", Status.NEW, epic.getId());
        subtask2 = taskManager.createSubTask(subtask2);
        Subtask subtask3 = new Subtask("Sub3", "subdesc3", Status.DONE, epic.getId());
        subtask3 = taskManager.createSubTask(subtask3);
        taskManager.getSubTaskById(subtask1.getId());
        taskManager.getSubTaskById(subtask2.getId());
        taskManager.getSubTaskById(subtask3.getId());
        taskManager.getEpicById(epic.getId());
        history = taskManager.getHistory();
        assertEquals(4, history.size());

        List<Integer> epicSubTasks = epic.getSubTaskIds();
        assertTrue(history.contains(subtask2));
        assertTrue(epicSubTasks.contains(subtask2.getId()));
        taskManager.deleteSubTaskById(subtask2.getId());
        epicSubTasks = epic.getSubTaskIds();
        assertFalse(epicSubTasks.contains(subtask2.getId()));
        history = taskManager.getHistory();
        assertEquals(3, history.size());
        assertFalse(history.contains(subtask2));

        taskManager.deleteAllSubTasks();
        epicSubTasks = epic.getSubTaskIds();
        assertEquals(0, epicSubTasks.size());
        history = taskManager.getHistory();
        assertEquals(1, history.size());
        assertFalse(history.contains(subtask1));
        assertFalse(history.contains(subtask3));
    }

    @Test
    public void testUpdateTasks() {
        Task task = new Task("name", "desc", Status.NEW);
        task = taskManager.createTask(task);
        taskManager.getTaskById(task.getId());
        List<Task> history = taskManager.getHistory();
        assertEquals(history.getFirst(), task);

        Task updTask = new Task("updName", "udpDesc", Status.IN_PROGRESS, task.getId());
        taskManager.updateTask(updTask);
        task = taskManager.getTaskById(task.getId());
        history = taskManager.getHistory();
        assertEquals(history.getFirst(), task);
        assertEquals(1, history.size());
    }*/
}
