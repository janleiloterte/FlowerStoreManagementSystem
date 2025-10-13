package bookstoreapp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Login {
    private final String username;
    private final String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public boolean checkAdmin(String username, String password) {
        return username.equals("admin") && password.equals("admin");
    }
    
    public boolean checkCustomer(String username, String password) {
         try(Scanner scan = new Scanner(new FileReader("customer.txt"))) {
            while(scan.hasNextLine()) {
                String[] info = scan.nextLine().split(",");
                
                if(info.length == 3) {
                    String user = info[0].trim();
                    String pass = info[1].trim();
                    
                    return user.equals(username) && pass.equals(password);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error reading customer.txt file.");
        }
        return false;
    }
}