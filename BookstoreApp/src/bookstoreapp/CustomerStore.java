package bookstoreapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.*;
import javafx.scene.control.ButtonType;

public class CustomerStore {
    private static final String CUSTOMER_FILE = "customer.txt";
    private static final int EXPECTED_FIELDS = 3;
    private final ObservableList<Customer> customers;

    public CustomerStore() {
        customers = FXCollections.observableArrayList();
        loadCustomers();
    }

    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer) {
        if (!customerExists(customer.getUsername())) {
            customers.add(customer);
            saveCustomers();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
        saveCustomers();
    }

    private boolean customerExists(String name) {
        return customers.stream()
                .anyMatch(c -> c.getUsername().equalsIgnoreCase(name.trim()));
    }

    public void loadCustomers() {
        File file = new File(CUSTOMER_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            customers.clear();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == EXPECTED_FIELDS) {
                    String name = data[0].trim();
                    String password = data[1].trim();
                    int points = Integer.parseInt(data[2].trim());
                    customers.add(new Customer(name, password, points));
                }
            }
        } catch (IOException | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void saveCustomers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_FILE))) {
            for (Customer customer : customers) {
                writer.write(String.format("%s,%s,%d",
                        customer.getUsername(),
                        customer.getPassword(),
                        customer.getPoints()));
                writer.newLine();
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password.", ButtonType.OK);
            alert.showAndWait();
        }
    }
}