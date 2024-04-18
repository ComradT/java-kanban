package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskTest {
    @Test
    @DisplayName("Сравнение таксов")
    public void testTwoTasksWithSameId() {
        Task task = new Task("Test", "desc", Status.NEW, 1);
        Task task2 = new Task("Test", "desc", Status.NEW, 1);
        Assertions.assertEquals(task, task2);
    }

    @Test
    @DisplayName("Сравннение массивов двух эпиков")
    public void testTwoEpicsWithSameId() {
        Epic epic = new Epic("Test", "desc", Status.NEW,1);
        epic.addSubTasksIds(2);
        Epic epic2 = new Epic("Test", "desc", Status.NEW,1);
        epic2.addSubTasksIds(2);
        Assertions.assertEquals(epic, epic2);
    }

    @Test
    @DisplayName("Сравннение массивов двух субтасков")
    public void testTwoSubTasksWithSameId() {
        SubTask subtask = new SubTask("Test", "desc", Status.NEW, 2, 1);
        SubTask subtask2 = new SubTask("Test", "desc", Status.NEW, 2, 1);
        Assertions.assertEquals(subtask, subtask2);
    }
}