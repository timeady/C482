/*
 *Timothy Eady
 *C482 Software I
 *
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static int partInc = 0;
    private static int productIDCount = 0;
    public static boolean executed = false;

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static void addPart(Part part) {
        allParts.add(part);
    }    
   
    public static void deletePart(Part part) {
        allParts.remove(part);
    }

    public static void updatePart(int index, Part part) {
        allParts.set(index, part);
    }

    public static int getPartInc() {
        partInc++;
        return partInc;
    }
    
    public static int cancelPartInc() {
        partInc--;
        return partInc;
    }

    public static Part lookupPart(int id) {
        for(Part p : getAllParts()){
            if(p.getPartID() == id)
                return p;                
       }
       return null;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public static void removeProduct(Product product) {
        allProducts.remove(product);
    }

    public static int getProductIDCount() {
        productIDCount++;
        return productIDCount;
    }    
   
    public static int cancelProductIDCount() {
        productIDCount--;
        return productIDCount;
    }

    public static Product lookupProduct(int id) {
        for(Product p : getAllProducts()){
            if(p.getProductID() == id)
                return p;              
       }
       return null;
    }
    
    public static void updateProduct(int index, Product product) {
        allProducts.set(index, product);
    }   
    
}
