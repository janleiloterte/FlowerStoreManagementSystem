package bookstoreapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class BookstoreApp extends Application {
    private Scene startScreen;
    private final String whiteFont = "-fx-text-fill: white;";
    private final String whiteBackground = "-fx-background-color: white;";
    private final String lightGreenBack = "-fx-background-color: #8FBC8B;";
    private final String roundBorders = "-fx-background-radius: 10;";
    private Font titleFont = Font.font("Arial", FontWeight.BOLD, 30);
    private Font headingFont = Font.font("Arial", FontWeight.BOLD, 20);
    private Font sloganFont = Font.font("Arial", FontPosture.ITALIC,14);

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        startScreen = new Scene(root, 750, 500);
        
        VBox topBox = new Components().getTopBox();
        
        Label heading = new Label("Login");
        heading.setFont(headingFont);
        
        VBox headingBox = new VBox(heading);
        headingBox.setMaxWidth(200);
        headingBox.setAlignment(Pos.CENTER_LEFT);
        
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        
        Button loginButton = new Button("Login");
        loginButton.setStyle(roundBorders);
        loginButton.setPrefWidth(200);
        
        Region spacer = new Region();
        spacer.setPrefHeight(20);
        
        VBox loginFields = new VBox(5, usernameLabel, usernameField, passwordLabel, passwordField);
        VBox loginBox = new VBox(15, heading, loginFields, loginButton);
        loginBox.setMaxWidth(230);
        loginBox.setPadding(new Insets(15));
        loginBox.setStyle(whiteBackground + roundBorders);
        
        Image image = new Image("file:login1.png");
        ImageView element = new ImageView(image);
        element.setFitHeight(275);
        element.setPreserveRatio(true);
        
        HBox elementBox = new HBox(element);
        elementBox.setPrefWidth(500);
        elementBox.setPadding(new Insets(75));
        elementBox.setAlignment(Pos.CENTER_LEFT);
        
        Image image1 = new Image("file:login2.png");
        ImageView element1 = new ImageView(image1);
        element1.setFitHeight(275);
        element1.setPreserveRatio(true);
        
        HBox elementBox1 = new HBox(element1);
        elementBox1.setPrefWidth(500);
        elementBox1.setPadding(new Insets(75));
        elementBox1.setAlignment(Pos.CENTER_RIGHT);
        
        VBox layout = new VBox(15, topBox, spacer, loginBox);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(50));
        
        VBox back = new VBox();
        back.setStyle(lightGreenBack);
        
        root.getChildren().add(elementBox);
        root.getChildren().add(elementBox1);
        root.getChildren().add(layout);
        root.setStyle(lightGreenBack);

        primaryStage.setTitle("Bloom");
        primaryStage.setScene(startScreen);
        primaryStage.setResizable(false);
        primaryStage.show();

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            Login login = new Login(username, password);
            
            boolean admin = login.checkAdmin(username, password);
            boolean customer = login.checkCustomer(username, password);

            clearFields(usernameField, passwordField);
            
            if (admin) {
                System.out.println("Move to admin screen");
                primaryStage.setScene(new OwnerMenuScreen(primaryStage, startScreen).getScene());
            }
            
            else if (customer) {
                System.out.println("Move to customer screen");
                primaryStage.setScene(new CustomerScreen(primaryStage, startScreen, username).getScene());
            }
            
            else {
                System.out.println("Invalid username or password.");
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Closing");
        });
    }

    private void clearFields(TextField usernameField, PasswordField passwordField) {
        usernameField.clear();
        passwordField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
