package bookstoreapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class CustomerScreen {
    private Scene scene;
    private int points;
    private StatusState status;
    private Label statusLabel;
    private final String username;
    private String customerName;
    private TableView<Flower> flowerTable;
    private TableView<Flower> cartTable;
    private Label totalLabel;
    private double total;
    List<Flower> cartFlowers;
    
    private final String whiteFont = "-fx-text-fill: white;";
    private final String whiteBackground = "-fx-background-color: white;";
    private final String lightGreenBack = "-fx-background-color: #8FBC8B;";
    private final String roundBorders = "-fx-background-radius: 10;";
    private Font titleFont = Font.font("Arial", FontWeight.BOLD, 30);
    private Font headingFont = Font.font("Arial", FontWeight.BOLD, 20);
    private Font sloganFont = Font.font("Arial", FontPosture.ITALIC,14);

    public CustomerScreen(Stage primaryStage, Scene mainPage, String username) {
        this.username = username;
        StackPane root = new StackPane();
        scene = new Scene(root, 750, 500);
        
        VBox top = new Components().getTopBox();
        
        Region spacer = new Region();
        spacer.setPrefHeight(20);
        
        loadCustomerData();
        
        Label heading = new Label("Welcome, " + this.username + ".");
        heading.setFont(headingFont);

        statusLabel = new Label(getStatusMessage());
        
        VBox welcome = new VBox(5, heading, statusLabel);
        welcome.setAlignment(Pos.CENTER_LEFT);
        
        flowerTable = new TableView<>();
        setupBookTable();
        loadBooks();
        
        cartTable = new TableView<>();
        setupCart();
        
        Button cartButton = new Components().createButton("Add to Cart", 425);
        Button buyBookBtn = new Components().createButton("Buy", 425);
        Button redeemPointsBtn = new Components().createButton("Redeem", 425);
        Button logoutBtn = new Components().createButton("Logout", 425);

        cartButton.setOnAction(e -> handleAddCart());      
        buyBookBtn.setOnAction(e -> handleBuyBooks());
        redeemPointsBtn.setOnAction(e -> handleRedeemBooks());
        logoutBtn.setOnAction(e -> primaryStage.setScene(mainPage));
        
        VBox browseBox = new VBox(15, welcome, flowerTable);
        
        Label heading1 = new Label("Cart");
        heading1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        totalLabel = new Label("Total: $0.0");
        
        VBox cartHeading = new VBox(5, heading1, totalLabel);
        VBox cartBox = new VBox(15, cartHeading, cartTable);
        
        HBox tableBox = new HBox(15, browseBox, cartBox);
        tableBox.setAlignment(Pos.CENTER);
        
        HBox buttonBox = new HBox(15, cartButton, buyBookBtn, redeemPointsBtn, logoutBtn);
        buttonBox.setAlignment(Pos.CENTER);
        
        VBox mainBox = new VBox(15, tableBox, buttonBox);
        mainBox.setMaxWidth(500);
        mainBox.setStyle(whiteBackground + roundBorders);
        mainBox.setPadding(new Insets(15));
        mainBox.setAlignment(Pos.CENTER);
        
        Image image = new Image("file:customer.png");
        ImageView element = new ImageView(image);
        element.setFitHeight(315);
        element.setPreserveRatio(true);
        
        HBox elementBox = new HBox(element);
        elementBox.setMaxHeight(750);
        elementBox.setPrefSize(750, 500);
        elementBox.setPadding(new Insets(50));
        elementBox.setAlignment(Pos.BOTTOM_RIGHT);
        
        VBox layout = new VBox(15, top, spacer, mainBox);
        layout.setPadding(new Insets(50));
        layout.setAlignment(Pos.CENTER_LEFT);
        
        root.getChildren().add(elementBox);
        root.getChildren().add(layout);
        root.setStyle(lightGreenBack);
    }

    public Scene getScene() {
        return scene;
    }

    private void setupBookTable() {
        flowerTable.setEditable(false);
        flowerTable.setMaxWidth(450);

        TableColumn<Flower, String> nameCol = new TableColumn<>("Flower");
        TableColumn<Flower, Integer> priceCol = new TableColumn<>("Price");
        TableColumn<Flower, Integer> stockCol = new TableColumn<>("Stock");
        TableColumn<Flower, CheckBox> selectCol = new TableColumn<>("Select");

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        selectCol.setCellValueFactory(new PropertyValueFactory<>("checkBox"));

        nameCol.setPrefWidth(75);
        priceCol.setPrefWidth(75);
        stockCol.setPrefWidth(75);
        selectCol.setPrefWidth(75);

        nameCol.setStyle("-fx-alignment: CENTER;");
        priceCol.setStyle("-fx-alignment: CENTER;");
        stockCol.setStyle("-fx-alignment: CENTER;");
        selectCol.setStyle("-fx-alignment: CENTER;");

        flowerTable.getColumns().setAll(nameCol, priceCol, stockCol, selectCol);
    }

    private void loadBooks() {
        ObservableList<Flower> books = FXCollections.observableArrayList();
        try (Scanner scanner = new Scanner(new FileReader("books.txt"))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length == 3 && Integer.parseInt(data[2].trim()) > 0) {
                    books.add(new Flower(data[0].trim(), Double.parseDouble(data[1].trim()), Integer.parseInt(data[2].trim())));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading books.txt");
        }
        flowerTable.setItems(books);
    }
    
    private void setupCart() {
        cartTable.setEditable(false);
        cartTable.setMaxWidth(150);

        TableColumn<Flower, String> nameCol = new TableColumn<>("Flower");
        TableColumn<Flower, Integer> priceCol = new TableColumn<>("Price");

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        nameCol.setPrefWidth(75);
        priceCol.setPrefWidth(75);

        nameCol.setStyle("-fx-alignment: CENTER;");
        priceCol.setStyle("-fx-alignment: CENTER;");

        cartTable.getColumns().setAll(nameCol, priceCol);
    }
    
    private void handleAddCart() {
        loadCart();
        statusLabel.setText(getStatusMessage());
        totalLabel.setText(displayTotal());
    }
    
    private void loadCart() {
        cartFlowers = getSelectedBooks();
        ObservableList<Flower> flowers = FXCollections.observableArrayList(cartFlowers);
        cartTable.setItems(flowers);
    }

    private void loadCustomerData() {
        try (Scanner scanner = new Scanner(new FileReader("customer.txt"))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length >= 3 && data[0].equals(username)) {
                    customerName = data[0];
                    points = Integer.parseInt(data[2].trim());
                    updateStatus();
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading customer.txt");
        }
        customerName = "Customer";
        points = 0;
        updateStatus();
    }

    private List<Flower> getSelectedBooks() {
        List<Flower> selected = new ArrayList<>();
        for (Flower book : flowerTable.getItems()) {
            if (book.getCheckBox().isSelected()) {
                selected.add(book);
            }
        }
        return selected;
    }
    
    private double updateTotal() {
        total = 0;
        List<Flower> selectedFlowers = getSelectedBooks();
        if (selectedFlowers.isEmpty()) return total;

        for (Flower flower : selectedFlowers) {
            total += flower.getPrice();
        }
        return total;
    }
    
    private String displayTotal() {
        return "Total: $" + updateTotal();
    }

    private void handleBuyBooks() {
        if (cartFlowers.isEmpty()) return;

        for (Flower book : cartFlowers) {
            points += book.getPrice() * 10;
        }

        updateStock();
        updateStatus();
        updateCustomerPoints();
        loadBooks();
        loadCart();
        refreshAfterAction();
    }

    private void handleRedeemBooks() {
        if (cartFlowers.isEmpty()) return;

        for (Flower book : cartFlowers) {
            int cost = (int) (book.getPrice() * 100);
            if (points < cost) return;
            points -= cost;
        }
        
        updateStock();
        updateStatus();
        updateCustomerPoints();
        loadBooks();
        loadCart();
        refreshAfterAction();
    }

    private void refreshAfterAction() {
        statusLabel.setText(getStatusMessage());
        totalLabel.setText(displayTotal());
    }

    private void updateCustomerPoints() {
        List<String> updatedLines = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileReader("customer.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data.length >= 3 && data[0].equals(username)) {
                    line = data[0] + "," + data[1] + "," + points;
                }
                updatedLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error updating points in customer.txt");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("customer.txt"))) {
            for (String line : updatedLines) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error writing updated customer data.");
        }
    }
    
    private void updateStock(){
        List<String> updatedLines = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileReader("books.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                
                for (Flower flower : cartFlowers) {
                    String flowerName = flower.getName();
                    Integer stock = flower.getStock() - 1;
                    
                    if (data.length >= 3 && data[0].equals(flowerName)) {
                    line = data[0] + "," + data[1] + "," + stock;
                    }
                }
                updatedLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error updating stock in books.txt");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("books.txt"))) {
            for (String line : updatedLines) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error writing updated flower data.");
        }
    }

    private void updateStatus() {
        status = (points >= 1000) ? new GoldState() : new SilverState();
    }

    private String getStatusMessage() {
        return "You have " + points + " points. Your status is " + status.displayStatus() + ".";
    }

    private interface StatusState {
        String displayStatus();
    }

    private static class SilverState implements StatusState {
        public String displayStatus() {
            return "Seedling";
        }
    }

    private static class GoldState implements StatusState {
        public String displayStatus() {
            return "Blossom";
        }
    }
}