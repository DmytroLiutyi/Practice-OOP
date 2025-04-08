import java.io.*;
class CalculationData implements Serializable {
    private double number1;
    private double number2;
    private double result;
    public CalculationData(double number1, double number2) {
        this.number1 = number1;
        this.number2 = number2;
    }
    public double getNumber1() {
        return number1;
    }
    public double getNumber2() {
        return number2;
    }
    public double getResult() {
        return result;
    }
    public void setResult(double result) {
        this.result = result;
    }
}
class Calculator {
    private CalculationData data;

    public Calculator(CalculationData data) {
        this.data = data;
    }
    public void calculateSum() {
        double sum = data.getNumber1() + data.getNumber2();
        data.setResult(sum);
    }

    public CalculationData getData() {
        return data;
    }
}
public class main {
    public static void main(String[] args) {
        CalculationData data = new CalculationData(1.4, 9.6);
        Calculator calculator = new Calculator(data);
        calculator.calculateSum();
        System.out.println("Число 1: " + data.getNumber1());
        System.out.println("Число 2: " + data.getNumber2());
        System.out.println("Результат: " + data.getResult());
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.ser"))) {
            out.writeObject(data);
            System.out.println("Дані збереженi у файл data.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
