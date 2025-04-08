import java.io.*;
class MathProcessor implements Serializable {
    private double number1;
    private double number2;

    /**
     * Конструктор класу MathProcessor.
     *
     * @param number1 перше число
     * @param number2 друге число
     */
    public MathProcessor(double number1, double number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    /**
     * Повертає суму двох чисел.
     *
     * @return сума
     */
    public double add() {
        return number1 + number2;
    }

    /**
     * Повертає добуток двох чисел.
     *
     * @return добуток
     */
    public double multiply() {
        return number1 * number2;
    }
}
public class main3 {
    public static void main(String[] args) {
        MathProcessor calc = new MathProcessor(10, 5);
        System.out.println("Перевiрка обчислень:");
        System.out.println("Сума: " + calc.add());
        System.out.println("Множення: " + calc.multiply()); 
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("math.ser"))) {
            out.writeObject(calc);
            System.out.println("Об'єкт серiалiзовано успiшно.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("math.ser"))) {
            MathProcessor restoredCalc = (MathProcessor) in.readObject();
            System.out.println("Об'єкт десерiалiзовано успiшно.");
            System.out.println("Перевiрка обчислень пiсля десерiалiзацiї:");
            System.out.println("Сума: " + restoredCalc.add());
            System.out.println("Множення: " + restoredCalc.multiply()); 
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
