import model.Epic;
import enums.Status;
import model.Task;
import model.SubTask;
import service.Managers;
import interfaces.TaskManager;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!"); // добавил тесты
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Магазин", "Сходить в Магнит", Status.NEW, 0);
        Task task2 = new Task("Работа", "Заставить себя встать в 6 утра", Status.NEW, 1);
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        Epic epic1 = new Epic("Яндекс практикум", "Пройти все спринты", Status.NEW, 2);
        taskManager.createEpic(epic1);
        SubTask subTask1 = new SubTask("Спринт 4", "Сдать финальное задание", Status.NEW, 1, epic1.getId());
        taskManager.createSubTask(subTask1);
        SubTask subTask2 = new SubTask("Спринт 5", "Заставить себя заниматься в эти выходные", Status.NEW, 0, epic1.getId());
        taskManager.createSubTask(subTask2);

        Epic epic2 = new Epic("Одежда", "Купить одежду к лету", Status.NEW, 2);
        taskManager.createEpic(epic2);
        SubTask subTask3 = new SubTask("Джинсы", "Выбрать и купить джинсы", Status.NEW, 0, epic2.getId());
        taskManager.createSubTask(subTask3);

        System.out.println("\nСписок эпиков:");
        for (Epic epic : taskManager.getEpicList()) {
            System.out.println(epic.getName());
        }

        System.out.println("\nСписок задач:");
        for (Task task : taskManager.getTasksList()) {
            System.out.println(task.getName());
        }

        System.out.println("\nСписок подзадач для эпика 1:");
        for (SubTask subTask : taskManager.getSubtasksByEpic(epic1.getId())) {
            System.out.println(subTask.getName());
        }

        Epic epic = taskManager.getEpicById(epic1.getId());
        if (epic != null) {
            System.out.printf("%nСтатус эпика %s: %s%n", epic1.getName(), epic.getStatus());
        } else {
            System.out.println("Эпик не найден.");
        }

        task1.setStatus(Status.IN_PROGRESS);
        subTask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubTask(subTask1);
        subTask2.setStatus(Status.DONE);
        taskManager.updateSubTask(subTask2);
        subTask3.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubTask(subTask3);

        System.out.printf("%nСтатус задачи %s: %s%n", task1.getName(), task1.getStatus());
        System.out.printf("%nСтатус подзадачи %s: для эпика %s: %s%n", subTask1.getName(), epic1.getName(), subTask1.getStatus());
        System.out.printf("Статус подзадачи %s: для эпика %s: %s%n", subTask2.getName(), epic1.getName(), subTask2.getStatus());
        System.out.printf("Статус подзадачи %s: для эпика %s: %s%n", subTask3.getName(), epic2.getName(), subTask3.getStatus());

        Epic createdEpic = taskManager.getEpicById(epic1.getId());
        if (createdEpic != null) {
            System.out.printf("%nСтатус эпика %s: %s%n", createdEpic.getName(), createdEpic.getStatus());
        } else {
            System.out.println("Эпик не найден.");
        }

        taskManager.removeTaskId(task1.getId());
        taskManager.removeEpicId(epic2.getId());
        taskManager.removeSubTaskId(subTask1.getId());

        System.out.println("\nСписок задач после удаления задачи 1:");
        for (Task task : taskManager.getTasksList()) {
            System.out.println(task.getName());
        }

        System.out.println("\nСписок эпиков после удаления эпика 2:");
        for (Epic epiic : taskManager.getEpicList()) {
            System.out.println(epiic.getName());
        }

        System.out.println("\nСписок подзадач для эпика 1 после удаления подзадачи 1:");
        for (SubTask subtask : taskManager.getSubtasksByEpic(epic1.getId())) {
            System.out.println(subtask.getName());
            String emoji = "\uD83D\uDE04";
            System.out.println("Приехали" + emoji);
        }
    }
}