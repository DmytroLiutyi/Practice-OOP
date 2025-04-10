import java.io.*;
import java.util.*;
interface ResultDisplay {
    void displayResults(List<String> results);
}
class TextResultDisplay implements ResultDisplay {
    @Override
    public void displayResults(List<String> results) {
        System.out.println("Результати обчислень:");
        for (String result : results) {
            System.out.println(result);
        }
    }
}
class TableResultDisplay implements ResultDisplay {
    private String tableHeader;
    public TableResultDisplay(String tableHeader) {
        this.tableHeader = tableHeader;
    }
    @Override
    public void displayResults(List<String> results) {
        System.out.println(tableHeader);
        System.out.println("---------------------------------------------------");
        for (String result : results) {
            System.out.println(result);
        }
    }
}
interface DisplayFactory {
    ResultDisplay createDisplay();
}
class TextDisplayFactory implements DisplayFactory {
    @Override
    public ResultDisplay createDisplay() {
        return new TextResultDisplay();
    }
}
class TableDisplayFactory implements DisplayFactory {
    private String tableHeader;
    public TableDisplayFactory(String tableHeader) {
        this.tableHeader = tableHeader;
    }
    @Override
    public ResultDisplay createDisplay() {
        return new TableResultDisplay(tableHeader);
    }
}
class BinaryAlternationCalculator implements Serializable {
    private List<String> results = new ArrayList<>();
    public int countAlternations(int number) {
        String binaryString = Integer.toBinaryString(number);
        int count = 0;
        for (int i = 0; i < binaryString.length() - 1; i++) {
            if (binaryString.charAt(i) != binaryString.charAt(i + 1)) {
                count++;
            }
        }
        String result = "Число: " + number + " -> " + binaryString + " | Чергувань: " + count;
        results.add(result);
        return count;
    }
    public List<String> getResults() {
        return results;
    }
}
public class main4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BinaryAlternationCalculator calc = new BinaryAlternationCalculator();

        System.out.print("Введiть кiлькiсть чисел: ");
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Введiть число: ");
            int number = scanner.nextInt();
            calc.countAlternations(number);
        }
        System.out.print("Виберiть формат виведення (1 - текстовий, 2 - таблиця): ");
        int displayOption = scanner.nextInt();
        DisplayFactory factory;
        if (displayOption == 1) {
            factory = new TextDisplayFactory();
        } else {
            scanner.nextLine();
            System.out.print("Введiть заголовок таблицi: ");
            String tableHeader = scanner.nextLine();
            factory = new TableDisplayFactory(tableHeader);
        }
        ResultDisplay display = factory.createDisplay();
        display.displayResults(calc.getResults());
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("results.ser"))) {
            out.writeObject(calc);
            System.out.println("Результати збережено у файл.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("results.ser"))) {
            BinaryAlternationCalculator loadedCalc = (BinaryAlternationCalculator) in.readObject();
            display.displayResults(loadedCalc.getResults());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
