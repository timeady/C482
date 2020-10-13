/*
 *Timothy Eady
 *C482 Software I
 *
 */
package Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public abstract class Part {

    protected IntegerProperty partID;
    protected StringProperty name;
    protected DoubleProperty price;
    protected IntegerProperty stock;
    protected IntegerProperty min;
    protected IntegerProperty max;
            
    public void setPartID(int partID) {
        this.partID.set(partID);
    }
    
    public int getPartID() {
        return this.partID.get();
    }
    
    public IntegerProperty partIDProperty() {
        return partID;
    }

    public void setName(String name) {
        this.name.set(name);
    }
    
    public String getName() {
        return this.name.get();
    }
    
    public StringProperty nameProperty() {
        return name;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }
    
    public double getPrice() {
        return this.price.get();
    }
    
    public DoubleProperty priceProperty() {
        return price;
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }
    
    public int getStock() {
        return this.stock.get();
    }
    
    public IntegerProperty stockProperty() {
        return stock;
    }
    
    public void setMin(int min) {
        this.min.set(min);
    }
    
    public int getMin() {
        return this.min.get();
    }
    
    public IntegerProperty minProperty() {
        return min;
    }

    public void setMax(int max) {
        this.max.set(max);
    }
    
    public int getMax() {
        return this.max.get();
    }
    
    public IntegerProperty maxProperty() {
        return max;
    }
}
