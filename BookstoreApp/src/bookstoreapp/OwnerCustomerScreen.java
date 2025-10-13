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

public class OwnerCustomerScreen {

    private Scene ownercust;
    private TableView<Customer> table = new TableView<>();
    private ObservableList<Customer> data = FXCollections.observableArrayList();
    private CustomerStore customerStore;
    
    private final String whiteFont = "-fx-text-fill: white;";
    private final String whiteBackground = "-fx-background-color: white;";
    private final String lightGreenBack = "-fx-background-color: #8FBC8B;";
    private final String roundBorders = "-fx-background-radius: 10;";
    private Font titleFont = Font.font("Arial", FontWeight.BOLD, 30);
    private Font headingFont = Font.font("Arial", FontWeight.BOLD, 20);
    private Font sloganFont = Font.font("Arial", FontPosture.ITALIC,14);

    public OwnerCustomerScreen(Stage primaryStage, Scene mainpage) {
        customerStore = new CustomerStore();
        
        StackPane root = new StackPane();
        ownercust = new Scene(root, 750, 500);
        
        VBox top = new Components().getTopBox();
        
        Region spacer = new Region();
        spacer.setPrefHeight(20);
        
        Label heading = new Label("Manage Customers");
        heading.setFont(headingFont);
        heading.setPrefWidth(620);
        
        Label userLabel = new Label("Username:");
        Label passLabel = new Label("Password:");
        TextField userField = new TextField();
        TextField passField = new TextField();
        userField.setPrefWidth(154);
        passField.setPrefWidth(154);

        HBox inputBox = new HBox(15, userLabel, userField, passLabel, passField);
        inputBox.setAlignment(Pos.CENTER);

        Button add =  new Components().createButton("Add", 146);
        Button delete = new Components().createButton("Delete", 146);
        Button back = new Components().createButton("Back", 146);
        
        add.setOnAction(e -> {
            String username = userField.getText().trim();
            String password = passField.getText().trim();
            if (!username.isEmpty() && !password.isEmpty()) {
                addCustomer(username, password);
                userField.clear();
                passField.clear();
            }
        });
        
        delete.setOnAction(e -> {
            deleteCustomer();
        });
        
        back.setOnAction(e -> {
            primaryStage.setScene(new OwnerMenuScreen(primaryStage, mainpage).getScene());
        });

        HBox buttonBox = new HBox(15, add, delete, back);
        buttonBox.setAlignment(Pos.CENTER);
        
        setupTable();
        loadCustomers();
        
        VBox customerBox = new VBox(15, heading, table, inputBox, buttonBox);
        customerBox.setMaxWidth(500);
        customerBox.setStyle(whiteBackground + roundBorders);
        customerBox.setPadding(new Insets(15));
        customerBox.setAlignment(Pos.CENTER);
        
        Image image = new Image("file:ownercustomer.png");
        ImageView element = new ImageView(image);
        element.setFitHeight(315);
        element.setPreserveRatio(true);
        
        HBox elementBox = new HBox(element);
        elementBox.setMaxHeight(750);
        elementBox.setPrefSize(750, 500);
        elementBox.setPadding(new Insets(50));
        elementBox.setAlignment(Pos.BOTTOM_RIGHT);
        
        VBox layout = new VBox(15, top, spacer, customerBox);
        layout.setPadding(new Insets(50));
        layout.setAlignment(Pos.CENTER_LEFT);
        
        root.getChildren().add(elementBox);
        root.getChildren().add(layout);
        root.setStyle(lightGreenBack);
    }

    public Scene getScene() {
        return ownercust;
    }

    private void setupTable() {
        table.setEditable(false);

        TableColumn<Customer, String> userCol = new TableColumn<>("Username");
        TableColumn<Customer, String> passCol = new TableColumn<>("Password");
        TableColumn<Customer, Integer> pointsCol = new TableColumn<>("Points");

        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));

        userCol.setPrefWidth(156);
        passCol.setPrefWidth(156);
        pointsCol.setPrefWidth(156);
        
        table.setMaxWidth(602);

        userCol.setStyle("-fx-alignment: CENTER;");
        passCol.setStyle("-fx-alignment: CENTER;");
        pointsCol.setStyle("-fx-alignment: CENTER;");

        table.getColumns().setAll(userCol, passCol, pointsCol);
    }

    private void loadCustomers() {
        data.clear();
        customerStore.loadCustomers();
        data.setAll(customerStore.getCustomers());
        table.setItems(data);
    }

    private void addCustomer(String username, String password) {
        Customer newCustomer = new Customer(username, password, 0);
        customerStore.addCustomer(newCustomer);
        loadCustomers();
    }

    private void deleteCustomer() {
        Customer selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            customerStore.removeCustomer(selected);
            loadCustomers();
        }
    }
}
