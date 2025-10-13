package bookstoreapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class FlowerStore {
    private static final String BOOK_FILE = "books.txt";
    private ObservableList<Flower> books;

    public FlowerStore() {
        books = FXCollections.observableArrayList();
        loadBooks(); // Load books when the store is initialized
    }

    public ObservableList<Flower> getBooks() {
        return books;
    }

    public void addBook(Flower book) {
    if (bookExists(book.getName())) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Flower already exists.", ButtonType.OK);
        alert.showAndWait();
    } else {
        books.add(book);
        saveBooks();
        }
    }

    public void removeBook(Flower book) {
        books.remove(book);
        saveBooks();
    }

    private boolean bookExists(String name) {
        return books.stream().anyMatch(b -> b.getName().equalsIgnoreCase(name));
    }

    public void loadBooks() {
        File file = new File(BOOK_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            books.clear();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    books.add(new Flower(data[0].trim(), Double.parseDouble(data[1].trim()), Integer.parseInt(data[2].trim())));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void saveBooks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOK_FILE))) {
            for (Flower book : books) {
                writer.write(book.getName() + "," + book.getPrice() + "," + book.getStock());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}