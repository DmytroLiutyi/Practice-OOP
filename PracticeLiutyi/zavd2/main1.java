import java.io.*;
import java.util.Scanner;
class UserSession implements Serializable {
    private String username;
    private transient String password;
    public UserSession(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public void showInfo() {
        System.out.println("Iм'я користувача: " + username);
        System.out.println("Пароль: " + (password != null ? password : "(вiдсутнiй пiсля серiалiзацiї)"));
    }
}
public class main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть iм'я користувача: ");
        String username = scanner.nextLine();
        System.out.print("Введіть пароль: ");
        String password = scanner.nextLine();
        UserSession session = new UserSession(username, password);
        System.out.println("\n[До серiалiзацiї]");
        session.showInfo();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("session.ser"))) {
            out.writeObject(session);
            System.out.println("\nОб'єкт серiалiзовано.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("session.ser"))) {
            UserSession restored = (UserSession) in.readObject();
            System.out.println("\n[Пiсля десерiалiзацiї]");
            restored.showInfo();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
