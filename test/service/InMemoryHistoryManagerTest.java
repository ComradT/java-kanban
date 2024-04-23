package service;

import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class InMemoryHistoryManagerTest {
    private InMemoryHistoryManager historyManager;
    List<Task> history;


    @BeforeEach
    public void beforeEach() {
        historyManager = new InMemoryHistoryManager();
        history = historyManager.getHistory();
    }


    @Test
    @DisplayName("проверка добавления Task в HistoryManager, и сохраняют предыдущую версию задачи и её данных")
    void testAddShouldAddNotNullTaskToHistory() {
        Task task = new Task("test", "desc", Status.NEW);
        Task task1 = new Task("test", "desc", Status.NEW);
        historyManager.add(task);
        historyManager.add(task1);
        historyManager.add(task);
        Assertions.assertEquals(historyManager.getHistory().size(), 3);
        Assertions.assertEquals(history.get(0), history.get(2));
        Assertions.assertNotNull(historyManager.getHistory().size());
    }
    @Test
    @DisplayName("задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных")
    void testTaskAddedRetainThePreviousVersionInMemoryHistoryManager() {
        Task task = new Task("test", "desc", Status.NEW, 1);
        Task task1 = new Task("test", "desc", Status.NEW, 2);
        historyManager.add(task);
        historyManager.add(task1);
        assertEquals(task.getId(), 1);
        assertEquals(task.getName(), "test");
        assertEquals(task.getDescription(), "desc");
        assertEquals(task.getStatus(), Status.NEW);
        assertEquals(task1.getId(), 2);
        assertEquals(task1.getName(), "test");
        assertEquals(task1.getDescription(), "desc");
        assertEquals(task1.getStatus(), Status.NEW);
      }

    @Test
    @DisplayName("проверка добавления не больше 10 задач в inMemoryHistoryManager")
    void testAddHistoryShouldNotBeLongerThan10() {
        int i = 0;
        while (i < 11) {
            historyManager.add(new Task("test", "desc", Status.NEW));
            i++;
        }
        Assertions.assertEquals( historyManager.getHistory().size(),10);
    }
}