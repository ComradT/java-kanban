import model.Epic;
import model.Status;
import model.Task;
import model.SubTask;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        Task task1 = new Task("Закончить учебу", "Сделать спринт", Status.NEW, 0);
        Task task2 = new Task("Рукопашка", "Позвать Сергея на рукопашку", Status.NEW, 0);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        Epic epic1 = new Epic("Починить авто", "Ремонт подвески машины", Status.NEW, epic1.subTasks.add(0));
        taskManager.createEpic(epic1);
        SubTask subtask1 = new SubTask("Купить запчасти", "Выбрать и заказать запчасти", Status.NEW, 0, epic1.getId());
        taskManager.createSubtask(subtask1);



    }
}
