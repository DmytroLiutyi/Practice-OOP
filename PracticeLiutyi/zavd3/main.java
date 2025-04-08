import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
interface DisplayableResult {
    void display(); 
    void saveToFile(String filename);
}
abstract class ResultFactory {
    public abstract DisplayableResult createResult(Object... values);
}
class GeometricAreaResult implements DisplayableResult {
    private String shapeType;
    private double area;
    private List<Double> dimensions;
    public GeometricAreaResult(String shapeType, double area, List<Double> dimensions) {
        this.shapeType = shapeType;
        this.area = area;
        this.dimensions = dimensions;
    }
    @Override
    public void display() {
        System.out.println("Геометрична фiгура: " + shapeType);
        System.out.println("Параметри: " + dimensions.stream()
                .map(d -> String.format("%.2f", d))
                .collect(Collectors.joining(", ")));
        System.out.printf("Площа: %.2f%n", area);
    }
    @Override
    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("Геометрична фiгура: " + shapeType + "\n");
            writer.write("Параметри: " + dimensions.stream()
                    .map(d -> String.format("%.2f", d))
                    .collect(Collectors.joining(", ")) + "\n");
            writer.write(String.format("Площа: %.2f%n", area));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Помилка збереження у файл: " + e.getMessage());
        }
    }
}
class GeometricAreaFactory extends ResultFactory {
    @Override
    public DisplayableResult createResult(Object... values) {
        String shapeType = (String) values[0];
        double area = (double) values[1];
        List<Double> dimensions = (List<Double>) values[2];
        return new GeometricAreaResult(shapeType, area, dimensions);
    }
}
class BinaryOnesResult implements DisplayableResult {
    private int onesCount;
    private double originalValue;
    public BinaryOnesResult(int onesCount, double originalValue) {
        this.onesCount = onesCount;
        this.originalValue = originalValue;
    }
    @Override
    public void display() {
        System.out.println("Кiлькiсть одиничних бiтiв: " + onesCount);
        System.out.printf("Оригiнальне значення: %.2f%n", originalValue);
    }
    @Override
    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("Кiлькiсть одиничних бiтiв: " + onesCount + "\n");
            writer.write(String.format("Оригiнальне значення: %.2f%n", originalValue));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Помилка збереження у файл: " + e.getMessage());
        }
    }
}
class GeometricCalculator {
    public static double calculateCircleArea(double radius) {
        return Math.PI * radius * radius;
    }
    public static double calculateEllipseArea(double semiMajorAxis, double semiMinorAxis) {
        return Math.PI * semiMajorAxis * semiMinorAxis;
    }
    public static int countBinaryOnes(double value) {
        return Integer.bitCount((int) value);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<DisplayableResult> results = new ArrayList<>();
        ResultFactory geometricFactory = new GeometricAreaFactory();
        System.out.print("Введiть радiус кола:");
        double circleRadius = scanner.nextDouble();
        System.out.print("Введiть довгу пiввiсь елiпса: ");
        double ellipseMajorAxis = scanner.nextDouble();
        System.out.print("Введiть коротку пiввiсь елiпса: ");
        double ellipseMinorAxis = scanner.nextDouble();
        double circleArea = calculateCircleArea(circleRadius);
        double ellipseArea = calculateEllipseArea(ellipseMajorAxis, ellipseMinorAxis);
        double totalArea = circleArea + ellipseArea;
        results.add(geometricFactory.createResult(
                "Коло",
                circleArea,
                Arrays.asList(circleRadius)
        ));
        results.add(geometricFactory.createResult(
                "Елiпс",
                ellipseArea,
                Arrays.asList(ellipseMajorAxis, ellipseMinorAxis)
        ));
        results.add(new BinaryOnesResult(
                countBinaryOnes(totalArea),
                totalArea
        ));
        System.out.println("\nРезультати обчислень:");
        for (DisplayableResult result : results) {
            result.display();
            result.saveToFile("my_results.txt");
        }
        scanner.close();
    }
}
