/*
 *Timothy Eady
 *C482 Software I
 *
 */
package Model;

import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private ArrayList<Part> associatedParts;
    private final IntegerProperty productID;
    private final StringProperty productName;
    private final DoubleProperty productPrice;
    private final IntegerProperty productStock;
    private final IntegerProperty productMin;
    private final IntegerProperty productMax;

    public Product(int productID, String name, double price, int stock, int min, int max, ArrayList<Part> associatedParts) {
        this.productID = new SimpleIntegerProperty(productID);
        this.productName = new SimpleStringProperty(name);
        this.productPrice = new SimpleDoubleProperty(price);
        this.productStock = new SimpleIntegerProperty(stock);
        this.productMin = new SimpleIntegerProperty(min);
        this.productMax = new SimpleIntegerProperty(max);
        this.associatedParts = new ArrayList<>(associatedParts);
    }

    public Product() {
        this.productID = new SimpleIntegerProperty(0);
        this.productName = new SimpleStringProperty("");
        this.productPrice = new SimpleDoubleProperty(0);
        this.productStock = new SimpleIntegerProperty(0);
        this.productMin = new SimpleIntegerProperty(0);
        this.productMax = new SimpleIntegerProperty(0);
        this.associatedParts = new ArrayList<>();

    }

    public void setProductID(int productID) {
        this.productID.set(productID);
    }

    public int getProductID() {
        return this.productID.get();
    }

    public IntegerProperty productIDProperty() {
        return productID;
    }

     public void setName(String name) {
        this.productName.set(name);
    }

    public String getName() {
        return this.productName.get();
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public void setPrice(double price) {
        this.productPrice.set(price);
    }

    public double getPrice() {
        return this.productPrice.get();
    }

    public DoubleProperty productPriceProperty() {
        return productPrice;
    }

    public void setStock(int stock) {
        this.productStock.set(stock);
    }

    public int getStock() {
        return this.productStock.get();
    }

    public IntegerProperty productStockProperty() {
        return productStock;
    }

    public void setMin(int min) {
        this.productMin.set(min);
    }
    
    public int getMin() {
        return this.productMin.get();
    }

    public IntegerProperty productMinProperty() {
        return productMin;
    }

     public void setMax(int max) {
        this.productMax.set(max);
    }

    public int getMax() {
        return this.productMax.get();
    }
    
    public IntegerProperty productMaxProperty() {
        return productMax;
    }

    public void setAssociatedParts(ArrayList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }
    
    public ArrayList<Part> getAssociatedParts() {
        return this.associatedParts;
    }

    public ObservableList<Part> getAllAssociatedParts() {
        ObservableList<Part> parts = FXCollections.observableArrayList(this.associatedParts);
        return parts;
    }

    
   
    
    
    

   
    
}
