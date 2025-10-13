package bookstoreapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class OwnerMenuScreen {
    private final Scene scene;
    private final String whiteBackground = "-fx-background-color: white;";
    private final String roundBorders = "-fx-background-radius: 10;";

    public OwnerMenuScreen(Stage primaryStage, Scene mainPage) {
        StackPane root = new StackPane();
        scene = new Scene(root, 750, 500);
        
        VBox top = new Components().getTopBox();
   
        Region spacer = new Region();
        spacer.setPrefHeight(20);
        
        Label heading = new Label("Menu");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Button flowersButton = new Components().createButton("Flowers", 200);
        Button customerButton = new Components().createButton("Customer", 200);
        Button logoutButton = new Components().createButton("Logout", 200);
        
        VBox buttonBox = new VBox(10, flowersButton, customerButton, logoutButton);
        
        VBox menuBox = new VBox(15, heading, buttonBox);
        menuBox.setMaxWidth(230);
        menuBox.setPadding(new Insets(15));
        menuBox.setStyle(whiteBackground + roundBorders);

        Image image = new Image("file:ownermenu.png");
        ImageView element = new ImageView(image);
        element.setFitHeight(200);
        element.setPreserveRatio(true);
        
        HBox elementBox = new HBox(element);
        elementBox.setPrefHeight(500);
        elementBox.setPadding(new Insets(50));
        elementBox.setAlignment(Pos.BOTTOM_CENTER);
        
        VBox layout = new VBox(15, top, spacer, menuBox);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(50));
        
        root.getChildren().add(elementBox);
        root.getChildren().add(layout);
        root.setStyle("-fx-background-color: #8FBC8B;");

        flowersButton.setOnAction(e -> {
            OwnerFlowerScreen obs = new OwnerFlowerScreen(primaryStage, mainPage);
            primaryStage.setScene(obs.getScene());
        });

        customerButton.setOnAction(e -> {
            OwnerCustomerScreen ocs = new OwnerCustomerScreen(primaryStage, mainPage);
            primaryStage.setScene(ocs.getScene());
        });

        logoutButton.setOnAction(e -> {
            primaryStage.setScene(mainPage);
            System.out.println("Move to login screen");
                });
    }

    public Scene getScene() {
        return scene;
    }
}
