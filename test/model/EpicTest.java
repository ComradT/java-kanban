package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static model.Status.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class EpicTest {
    @Test
    @DisplayName("Добавление двух эпиков")
    public void testTwoEpicsWithSameId() {
        Epic epic = new Epic("Test", "desc", NEW, 1);
        epic.addSubTasksIds(2);
        Epic epic2 = new Epic("Test", "desc", NEW, 1);
        epic2.addSubTasksIds(2);
        Assertions.assertEquals(epic, epic2);
    }

    @Test
    @DisplayName("Добавление субтасков")
    public void testAddSubtaskToEpicWithSameId() {
        Epic epic = new Epic("Test", "desc", NEW, 1);
        epic.addSubTasksIds(epic.getId());
        Assertions.assertEquals(1, epic.getSubTasksIds().size());
    }
    @Test
    @DisplayName("Создание класса эпика")
    void epicCreation() {
        int id = 1;
        String title = "Title Epic";
        String description = "Description";
        Status status = Status.NEW;

        Epic epic = new Epic(title, description, status, id);
        epic.setId(id);

        assertEquals(id, epic.getId(), "Id Epic должен соответствовать: " + id);
        assertEquals(title, epic.getName(), "Название Epic должен соответствовать: " + title);
        assertEquals(description, epic.getDescription(), "Описание Epic должен соответствовать: " + description);
        assertEquals(status, epic.getStatus(), "Статус Epic должен соответствовать:" + status);
    }

    @Test
    @DisplayName("Сравнение эпиков")
    void epicCreationAndEquality() {
        Epic epic1 = new Epic("Epic 1", "Description 1", Status.NEW, 0);
        Epic epic2 = new Epic("Epic 1", "Description 1", Status.NEW,1);
        epic1.setId(1);
        epic2.setId(1);
        assertEquals(epic1, epic2, "Epics должны быть равными, когда их дети равны.");
    }


}

