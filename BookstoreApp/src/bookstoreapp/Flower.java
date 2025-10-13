package bookstoreapp;

import javafx.scene.control.CheckBox;

public class Flower {
    private String name;
    private double price;
    public int stock;
    private CheckBox checkBox;
    
    public Flower(String n, double p, int s){
        this.name = n;
        this.price = p;
        this.stock = s;
        this.checkBox = new CheckBox();
    }
    public String getName(){
        return this.name;
    }
    public double getPrice(){
        return price;
    }
    
    public int getStock() {
        return stock;
    }
    
    public CheckBox getCheckBox() { 
    return checkBox; 
    }
    
    @Override
    public String toString(){
        return this.name + " - $"+ this.price;
    }

}
