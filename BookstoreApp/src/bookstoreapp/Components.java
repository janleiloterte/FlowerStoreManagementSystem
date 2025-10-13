/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookstoreapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 *
 * @author janleilo
 */
public class Components {
    private final String whiteFont = "-fx-text-fill: white;";
    private final String whiteBackground = "-fx-background-color: white;";
    private final String lightGreenBack = "-fx-background-color: #8FBC8B;";
    private final String roundBorders = "-fx-background-radius: 10;";
    private Font titleFont = Font.font("Arial", FontWeight.BOLD, 30);
    private Font headingFont = Font.font("Arial", FontWeight.BOLD, 20);
    private Font sloganFont = Font.font("Arial", FontPosture.ITALIC,14);
    
    public VBox getTopBox() {
        Label title = new Label("Bloom");
        title.setFont(titleFont);
        title.setStyle(whiteFont);
        
        Image image = new Image("file:logo.png");
        ImageView logo = new ImageView(image);
        logo.setFitWidth(30);
        logo.setPreserveRatio(true);
        
        HBox logoBox = new HBox(5, logo, title);
        logoBox.setAlignment(Pos.CENTER);
        
        Label slogan = new Label("Your go-to flower shop app.");
        slogan.setFont(sloganFont);
        slogan.setStyle(whiteFont);
        
        VBox topBox = new VBox(5, logoBox, slogan);
        topBox.setAlignment(Pos.CENTER);
        
        return topBox;
    }

    public Button createButton(String text, Integer size) {
        Button button = new Button(text);
        button.setPrefWidth(size);
        button.setStyle(roundBorders);
        return button;
    }
    
}
