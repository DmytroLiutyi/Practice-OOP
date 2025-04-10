import java.util.*;
import java.util.concurrent.*;
public class FactorialWorkerApp {
    private static final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    private static final Stack<String> history = new Stack<>();
    private static final List<String> results = new ArrayList<>();
    private static final int WORKER_COUNT = 2;
    static class Worker extends Thread {
        public void run() {
            try {
                while (true) {
                    Runnable task = taskQueue.take();
                    task.run();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public static void startWorkers() {
        for (int i = 0; i < WORKER_COUNT; i++) {
            new Worker().start();
        }
    }
    public static long factorial(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++)
            result *= i;
        return result;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        startWorkers();
        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Ввести число та обчислити факторiал");
            System.out.println("2. Скасувати останню операцiю");
            System.out.println("3. Вивести результати");
            System.out.println("4. Вийти");
            System.out.print("Виберiть опцiю: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Введiть число: ");
                    int number = scanner.nextInt();
                    taskQueue.add(() -> {
                        long fact = factorial(number);
                        String result = "Число: " + number + " | Факторiал: " + fact;
                        results.add(result);
                        history.push(result);
                        System.out.println(result);
                    });
                    break;
                case 2:
                    if (!history.isEmpty()) {
                        String removed = history.pop();
                        results.remove(removed);
                        System.out.println("Останню операцiю скасовано.");
                    } else {
                        System.out.println("Немає операцiй для скасування.");
                    }
                    break;
                case 3:
                    System.out.println("Результати:");
                    results.forEach(System.out::println);
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Невiрний вибi1р.");
            }
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
