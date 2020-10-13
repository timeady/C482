/*
 *Timothy Eady
 *C482 Software I
 *
 */
package Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OutsourcedPart extends Part {

    private final StringProperty companyName;

    public OutsourcedPart(int partID, String name, double price, int stock, int min, int max, String companyName) {
        this.partID = new SimpleIntegerProperty(partID);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.stock = new SimpleIntegerProperty(stock);
        this.min = new SimpleIntegerProperty(min);
        this.max = new SimpleIntegerProperty(max);
        this.companyName = new SimpleStringProperty(companyName);
    }

    public OutsourcedPart() {
        this.partID = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty("");
        this.price = new SimpleDoubleProperty(0);
        this.stock = new SimpleIntegerProperty(0);
        this.min = new SimpleIntegerProperty(0);
        this.max = new SimpleIntegerProperty(0);
        this.companyName = new SimpleStringProperty("");
    }

    public String getCompanyName() {
        return this.companyName.get();
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
    
    public StringProperty companyNameProperty() {
            return companyName;
    }
}