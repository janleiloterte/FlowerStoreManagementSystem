package bookstoreapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class OwnerFlowerScreen {

    private Scene ownerFlowerScene;
    private TableView<Flower> table = new TableView<>();
    private ObservableList<Flower> data = FXCollections.observableArrayList();
    private FlowerStore flowerStore;
    
    private final String whiteFont = "-fx-text-fill: white;";
    private final String whiteBackground = "-fx-background-color: white;";
    private final String lightGreenBack = "-fx-background-color: #8FBC8B;";
    private final String roundBorders = "-fx-background-radius: 10;";
    private Font titleFont = Font.font("Arial", FontWeight.BOLD, 30);
    private Font headingFont = Font.font("Arial", FontWeight.BOLD, 20);
    private Font sloganFont = Font.font("Arial", FontPosture.ITALIC,14);

    public OwnerFlowerScreen(Stage primaryStage, Scene mainpage) {
        flowerStore = new FlowerStore();
        
        StackPane root = new StackPane();
        ownerFlowerScene = new Scene(root, 750, 500);
        
        VBox top = new Components().getTopBox();
        
        Region spacer = new Region();
        spacer.setPrefHeight(20);

        Label heading = new Label("Manage Flowers");
        heading.setFont(headingFont);
        heading.setPrefWidth(620);

        Label nameLabel = new Label("Name:");
        Label priceLabel = new Label("Price:");
        Label stockLabel = new Label("Stock:");
        
        TextField nameField = new TextField();
        TextField priceField = new TextField();
        TextField stockField = new TextField();
        nameField.setPrefWidth(95);
        priceField.setPrefWidth(95);
        stockField.setPrefWidth(95);
        
        HBox inputBox = new HBox(15, nameLabel, nameField, priceLabel, priceField, stockLabel, stockField);
        inputBox.setAlignment(Pos.CENTER);

        Button add = new Components().createButton("Add", 146);
        Button delete = new Components().createButton("Delete", 146);
        Button back = new Components().createButton("Back", 146);

        add.setOnAction(e -> {
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            String stockText = stockField.getText().trim();
            if (!name.isEmpty() && !priceText.isEmpty() && !stockText.isEmpty()) {
                addBook(name, priceText, stockText);
                nameField.clear();
                priceField.clear();
                stockField.clear();
            }
        });

        delete.setOnAction(e -> {
            deleteBook();
        });
        
        back.setOnAction(e -> {
            primaryStage.setScene(new OwnerMenuScreen(primaryStage, mainpage).getScene());
        });

        HBox buttonBox = new HBox(15, add, delete, back);
        buttonBox.setAlignment(Pos.CENTER);

        setupTable();
        loadBooks();
        
        VBox flowerBox = new VBox(15, heading, table, inputBox, buttonBox);
        flowerBox.setMaxWidth(500);
        flowerBox.setStyle(whiteBackground + roundBorders);
        flowerBox.setPadding(new Insets(15));
        flowerBox.setAlignment(Pos.CENTER);
        
        Image image = new Image("file:ownerflower.png");
        ImageView element = new ImageView(image);
        element.setFitHeight(315);
        element.setPreserveRatio(true);
        
        HBox elementBox = new HBox(element);
        elementBox.setMaxHeight(750);
        elementBox.setPrefSize(750, 500);
        elementBox.setPadding(new Insets(50));
        elementBox.setAlignment(Pos.BOTTOM_LEFT);
        
        VBox layout = new VBox(15, top, spacer, flowerBox);
        layout.setPadding(new Insets(50));
        layout.setAlignment(Pos.CENTER_RIGHT);

        root.getChildren().add(elementBox);
        root.getChildren().add(layout);
        root.setStyle(lightGreenBack);
    }

    public Scene getScene() {
        return ownerFlowerScene;
    }

    private void setupTable() {
        table.setEditable(false);

        TableColumn<Flower, String> nameCol = new TableColumn<>("Name");
        TableColumn<Flower, Double> priceCol = new TableColumn<>("Price");
        TableColumn<Flower, Integer> stockCol = new TableColumn<>("Stock");

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        nameCol.setPrefWidth(156);
        priceCol.setPrefWidth(156);
        stockCol.setPrefWidth(156);

        nameCol.setStyle("-fx-alignment: CENTER;");
        priceCol.setStyle("-fx-alignment: CENTER;");
        stockCol.setStyle("-fx-alignment: CENTER;");

        table.getColumns().setAll(nameCol, priceCol, stockCol);
    }

    private void loadBooks() {
        data.clear();
        flowerStore.loadBooks();
        data.setAll(flowerStore.getBooks());
        table.setItems(data);
    }

    private void addBook(String name, String priceText, String stockText) {
        try {
            double price = Double.parseDouble(priceText);
            int stock = Integer.parseInt(stockText);
            Flower newBook = new Flower(name, price, stock);
            flowerStore.addBook(newBook);
            loadBooks();
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format.");
        }
    }

    private void deleteBook() {
        Flower selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            flowerStore.removeBook(selected);
            loadBooks();
        }
    }
}