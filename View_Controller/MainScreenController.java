/*
 *Timothy Eady
 *C482 Software I
 *
 */
package View_Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import timeadyinventorysystem.TimEadyInventorySystem;
import Model.InhousePart;
import Model.Inventory;
import static Model.Inventory.deletePart;
import Model.Part;
import static Model.Inventory.getAllParts;
import static Model.Inventory.getPartInc;
import static Model.Inventory.getAllProducts;
import static Model.Inventory.lookupPart;
import Model.OutsourcedPart;
import Model.Product;
import static Model.Inventory.removeProduct;

public class MainScreenController {
    @FXML 
    private TextField partsSearchText;
    @FXML 
    private TextField productSearchText;       
    @FXML
    private TableView<Part> partsTableView;  
    @FXML
    private TableColumn<Part, Integer> partsIDColumn;  
    @FXML
    private TableColumn<Part, String> partsNameColumn;  
    @FXML
    private TableColumn<Part, Integer> partsStockColumn;  
    @FXML
    private TableColumn<Part, Double> partsPriceColumn;
    @FXML
    private TableView<Product> productsTableView;  
    @FXML
    private TableColumn<Product, Integer> productsIDColumn;  
    @FXML
    private TableColumn<Product, String> productsNameColumn;  
    @FXML
    private TableColumn<Product, Integer> productsStockColumn;  
    @FXML
    private TableColumn<Product, Double> productsPriceColumn;
    @FXML
    public ObservableList<Part> tempPart=FXCollections.observableArrayList();
    public ObservableList<Product> tempProduct=FXCollections.observableArrayList();
    private Part newPart;
    private Product newProduct;
    private TimEadyInventorySystem timeadyInventorySystem;
    private static int index;

    public MainScreenController() {        
    }
    
    public static int modifyIndex() {
        return index;
    }           

    @FXML
    void partsAddHandler(ActionEvent event) throws IOException{
        boolean click = timeadyInventorySystem.showPartScreen();
    }    

    @FXML
    void partsDeleteHandler(ActionEvent event) {
        Part part = partsTableView.getSelectionModel().getSelectedItem();
        if (part != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure you want to delete " + part.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) 
                deletePart(part);
            else 
                alert.close();        
        }
    }

    @FXML
    void partsModifyHandler(ActionEvent event) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        index = getAllParts().indexOf(selectedPart);
        if (selectedPart != null) {
            boolean saveClicked = timeadyInventorySystem.showModifyPart(selectedPart);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No part selected");
            alert.setContentText("Please select a part in the table to modify.");
            alert.showAndWait();
        }
    }
    
    @FXML
    void partsSearchHandler(ActionEvent event) {
        String searchItem=partsSearchText.getText();
        if (searchItem.equals("")){
                partsTableView.setItems(getAllParts());
        } else{
            boolean found=false;
            try{
                int id = Integer.parseInt(searchItem);
                Part p = Inventory.lookupPart(id);
                if(p != null){
                    found=true;
                    tempPart.clear();
                    tempPart.add(p);
                    partsTableView.setItems(tempPart);
                } 
                if (found == false){
                partsTableView.setItems(getAllParts());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("No part found");
                alert.setContentText("Please search by exact part name or ID");

                alert.showAndWait();
            }
        }
        catch(NumberFormatException e){
            for(Part p : Inventory.getAllParts()){
                if(p.getName().equals(searchItem)){
                    System.out.println("This is part " + p.getPartID());
                    found = true;
                    tempPart.clear();
                    tempPart.add(p);
                    partsTableView.setItems(tempPart);
                }
            
            }
            if (found == false){
            partsTableView.setItems(getAllParts());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("No part found");
            alert.setContentText("Please search by exact part name or ID.");
            
            alert.showAndWait();
            }
        }
        }
    
    }

    @FXML
    void productsAddHandler(ActionEvent event) {
        boolean click = timeadyInventorySystem.showProductScreen();
    }

    @FXML
    void productsDeleteHandler(ActionEvent event) {
        Product product = productsTableView.getSelectionModel().getSelectedItem();
        if (product != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Are you sure you want to delete " + product.getName() + "?");
            alert.setContentText("This product has parts associated with it.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.out.println("Product Deleted");
                removeProduct(product);
            } else {
                alert.close();
            }
        }        
    }

    @FXML
    void productsModifyHandler(ActionEvent event) {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        index = getAllProducts().indexOf(selectedProduct);
        if (selectedProduct != null) {
            boolean click = timeadyInventorySystem.showModifyProductScreen(selectedProduct);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No product selected");
            alert.setContentText("Please select a product in the table to modify.");
            alert.showAndWait();
        }
    }

    @FXML
    void productsSearchHandler(ActionEvent event) {
        String searchItem=productSearchText.getText();
        if (searchItem.equals("")){
                productsTableView.setItems(getAllProducts());
        } else {
            boolean found = false;
            try{
                int id = Integer.parseInt(searchItem);
                Product p = Inventory.lookupProduct(id);
                if(p != null){
                    found=true;
                    tempProduct.clear();
                    tempProduct.add(p);
                    productsTableView.setItems(tempProduct);
                }
                if (found == false){
                    productsTableView.setItems(getAllProducts());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Product not found");
                    alert.setContentText("Please search by exact product name or ID.");
                    alert.showAndWait();
                }
                }
            catch(NumberFormatException e){
                for(Product p: Inventory.getAllProducts()){
                    if(p.getName().equals(searchItem)){
                        found=true;
                        tempProduct.clear();
                        tempProduct.add(p);
                        productsTableView.setItems(tempProduct);
                    }
            
                }   
                if (found == false){
                    productsTableView.setItems(getAllProducts());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Product not found");
                    alert.setContentText("Please search by exact name or ID.");
                    alert.showAndWait();
                }
            }
        }   
    }

    void partsFiller() {
        int partID = Inventory.getPartInc();
        OutsourcedPart bedPart1 = new OutsourcedPart(partID, "Caster", 180.99, 42, 4, 150, "Stryker");
        Inventory.addPart(bedPart1);
        OutsourcedPart bedPart2 = new OutsourcedPart(getPartInc(), "Rail", 275.43, 36, 2, 123, "Carroll");
        Inventory.addPart(bedPart2);
        InhousePart bedPart3 = new InhousePart(getPartInc(), "Washer", 0.99, 1000, 100, 10000, 63);
        Inventory.addPart(bedPart3);
        InhousePart bedPart4 = new InhousePart(getPartInc(), "Inverter", 35.87, 40, 10, 100, 52);  
        Inventory.addPart(bedPart4);
    }
    
    void productsFiller() {
        int productID = Inventory.getProductIDCount();
        ArrayList<Part> bedParts1 = new ArrayList<>();
        bedParts1.add(lookupPart(1));
        Product bed1 = new Product(productID, "Secure 3", 8400.99, 57, 20, 150, bedParts1);
        Inventory.addProduct(bed1);
        ArrayList<Part> bedParts2 = new ArrayList<>();
        bedParts2.add(lookupPart(4));
        Product bed2 = new Product(Inventory.getProductIDCount(), "BariMax", 14655.99, 15, 5, 40, bedParts2);
        Inventory.addProduct(bed2);
                                                   
    }        
    
    @FXML
    void exitHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Do you really want to exit?");
        alert.setContentText("All the changes you haven't saved will be lost.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
        else {
            System.out.println("Cancel was pressed.");
        }
    }
    
    @FXML
    private void initialize() {
        partsIDColumn.setCellValueFactory(
                cellData -> cellData.getValue().partIDProperty().asObject());
        partsNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().nameProperty());
        partsStockColumn.setCellValueFactory(
                cellData -> cellData.getValue().stockProperty().asObject());
        partsPriceColumn.setCellValueFactory(
                cellData -> cellData.getValue().priceProperty().asObject());
        productsIDColumn.setCellValueFactory(
                cellData -> cellData.getValue().productIDProperty().asObject());
        productsNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().productNameProperty());
        productsStockColumn.setCellValueFactory(
                cellData -> cellData.getValue().productStockProperty().asObject());
        productsPriceColumn.setCellValueFactory(
                cellData -> cellData.getValue().productPriceProperty().asObject());
    }
    
    public void setMainApp(TimEadyInventorySystem timeadyInventorySystem) {
        this.timeadyInventorySystem = timeadyInventorySystem;
        partsFiller();
        productsFiller();
        partsTableView.setItems(getAllParts());
        productsTableView.setItems(getAllProducts());
        
    }

}
