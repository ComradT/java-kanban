package service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ManagersTest {

    @Test
    @DisplayName("возвращает значение для InMemoryTaskManager")
    public void getDefaultInMemoryTaskManager() {
        TaskManager test = Managers.getDefault();
        assertNotNull(test);
    }

    @Test
    @DisplayName("возвращает историю для InMemoryHistoryManager")
    public void getDefaultHistoryInMemoryHistoryManager() {
        HistoryManager test = Managers.getDefaultHistory();
        assertNotNull(test);
    }

}