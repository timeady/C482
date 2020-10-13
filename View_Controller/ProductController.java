/*
 *Timothy Eady
 *C482 Software I
 *
 */
package View_Controller;

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
import javafx.stage.Stage;
import Model.Inventory;
import static Model.Inventory.getAllParts;
import Model.Part;
import Model.Product;
import static View_Controller.MainScreenController.modifyIndex;

public class ProductController {
    @FXML
    private TextField productMaxText;

    @FXML
    private TextField productMinText;

    @FXML
    private TextField productIDText;

    @FXML
    private TextField productNameText;

    @FXML
    private TextField productStockText;

    @FXML
    private TextField productPriceText;

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
    private TableView<Part> associatedPartsTableView;
    
    @FXML
    private TableColumn<Part, Integer> associatedPartsIDColumn;
    
    @FXML
    private TableColumn<Part, String> associatedPartsNameColumn;
    
    @FXML
    private TableColumn<Part, Integer> associatedPartsStockColumn;
    
    @FXML
    private TableColumn<Part, Double> associatedPartsPriceColumn;
    
    private int productID;
    private Product selectedProduct;
    private Stage stage;
    private boolean click = false;
    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    public ObservableList<Part> tempPart=FXCollections.observableArrayList();
    int productIndex = modifyIndex();
    
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
        associatedPartsIDColumn.setCellValueFactory(
                cellData -> cellData.getValue().partIDProperty().asObject());
        associatedPartsNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().nameProperty());
        associatedPartsStockColumn.setCellValueFactory(
                cellData -> cellData.getValue().stockProperty().asObject());
        associatedPartsPriceColumn.setCellValueFactory(
                cellData -> cellData.getValue().priceProperty().asObject());
        productID = Inventory.getProductIDCount();
        productIDText.setText("Auto-Gen: " + productID);
        productStockText.setText(Integer.toString(0));
        partsTableView.setItems(getAllParts());
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public boolean isClicked() {
        return click;
    }

    @FXML
    void partsInProductHandler(ActionEvent event) {
        Part part = partsTableView.getSelectionModel().getSelectedItem();
        currentParts.add(part);
        associatedPartsTableView.setItems(currentParts);
    }

    @FXML
    void productCancelHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Are you sure you want to Cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            productID = Inventory.cancelProductIDCount();
            stage.close();
        } else {
            alert.close();
        }        
    }

    @FXML
    void deletePartInProduct(ActionEvent event) {
        Part part = associatedPartsTableView.getSelectionModel().getSelectedItem();
        int partSize = associatedPartsTableView.getItems().size();
        if (partSize > 1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete " + part.getName() + " from this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                currentParts.remove(part);
                associatedPartsTableView.setItems(currentParts);
            } else {
            alert.close();
            }   
        } else {        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(stage);
        alert.setTitle("Parts Error");
        alert.setHeaderText("Cannot delete " + part.getName());
        alert.setContentText("Each product must have at least one part.\n");
        alert.showAndWait();
        }
    }

    @FXML
    void productSaveHandler(ActionEvent event) {
        if (isProductValid()) {
            String name = productNameText.getText();
            String stock = productStockText.getText();
            String price = productPriceText.getText();
            String min = productMinText.getText();
            String max = productMaxText.getText();
            
            Product newProduct = new Product();
            newProduct.setProductID(productID);
            newProduct.setName(name);
            newProduct.setStock(Integer.parseInt(stock));
            newProduct.setPrice(Double.parseDouble(price));
            newProduct.setMin(Integer.parseInt(min));
            newProduct.setMax(Integer.parseInt(max));
            ArrayList<Part> parts = new ArrayList<>();
            parts.addAll(associatedPartsTableView.getItems());
            newProduct.setAssociatedParts(parts);
            Inventory.addProduct(newProduct);
 
            click = true;
            stage.close();
        }
    }
    
    @FXML
    void productModifySaveHandler(ActionEvent event) {
        if (isProductValid()) {
            String id = productIDText.getText();
            String name = productNameText.getText();
            String stock = productStockText.getText();
            String price = productPriceText.getText();
            String min = productMinText.getText();
            String max = productMaxText.getText();
            
            Product modProduct = new Product();
            modProduct.setProductID(Integer.parseInt(id));
            modProduct.setName(name);
            modProduct.setStock(Integer.parseInt(stock));
            modProduct.setPrice(Double.parseDouble(price));
            modProduct.setMin(Integer.parseInt(min));
            modProduct.setMax(Integer.parseInt(max));
            ArrayList<Part> parts = new ArrayList<>();
            parts.addAll(associatedPartsTableView.getItems());
            modProduct.setAssociatedParts(parts);
            Inventory.updateProduct(productIndex, modProduct);
            
            click = true;
            Inventory.cancelProductIDCount();
            stage.close();
        } 
    }

    private boolean isProductValid() {
        String name = productNameText.getText();
        String stock = productStockText.getText();
        String price = productPriceText.getText();
        String min = productMinText.getText();
        String max = productMaxText.getText();
        int partSize = associatedPartsTableView.getItems().size();
        ArrayList<Part> parts = new ArrayList<>();
        parts.addAll(associatedPartsTableView.getItems());
   
        String errorMessage = "";
        if (name == null || name.length() == 0) {
            errorMessage += "The product name is not valid.\n"; 
        }
        if (stock == null || stock.length() == 0) {
            productStockText.setText(Integer.toString(0));
            errorMessage += "The inventory value is not valid.\n";  
        } else {
            try {
                int stockComp = Integer.parseInt(stock);
                int minComp = Integer.parseInt(min);
                int maxComp = Integer.parseInt(max);
                if (stockComp < minComp || stockComp > maxComp) {
                errorMessage += "The current inventory level must be between the minimum and maximum values.\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "The inventory value is not valid.\n"; 
            }
        }
        if (price == null || price.length() == 0) {
            errorMessage += "The price is not valid for this product. \n"; 
        } else {
            try {
                double productPrice = Double.parseDouble(price);
                double partsPrice = 0;
                for (Part part : parts) {
                partsPrice = partsPrice + part.getPrice();
                }

                if (partsPrice > productPrice) {
                errorMessage += "The price of the product has to be greater than the the sum of the parts.\n";
                }
                
            } catch (NumberFormatException e) {
                errorMessage += "The price is not valid.\n"; 
            }
        }
        if (min == null || min.length() == 0) {
            errorMessage += "The minimum stock level is not valid.\n"; 
        } else {
            try {
                int minComp = Integer.parseInt(min);
                int maxComp = Integer.parseInt(max);
                if (maxComp < minComp || minComp >= maxComp ) {
                    errorMessage += "The maximum stock value has to be higher than the minimum.\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "The minimum stock value is not valid.\n"; 
            }
        }        
        if (max == null || max.length() == 0) {
            errorMessage += "The maximum stock value is not valid.\n"; 
        } else {
            try {
                int minComp = Integer.parseInt(min);
                int maxComp = Integer.parseInt(max);
                if (maxComp < minComp || minComp >= maxComp ) {
                    errorMessage += "The maximum stock value has to be greater than the minimum stock value.\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "The maximum stock value is not valid.\n"; 
            }
        }
        if (partSize == 0) {
            errorMessage += "Each product must have at least one part. \n"; 
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct the values in order to save this product.");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }

    @FXML
    void productSearchHandler(ActionEvent event) {
        String searchItem=productSearchText.getText();
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
                if (found==false){
                partsTableView.setItems(getAllParts());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Part Not Found");
                alert.setContentText("Please search by exact part name or ID");

                alert.showAndWait();
            }
        }
        catch(NumberFormatException e){
            for(Part p: Inventory.getAllParts()){
                if(p.getName().equals(searchItem)){
                    found=true;
                    tempPart.clear();
                    tempPart.add(p);
                    partsTableView.setItems(tempPart);
                }            
            }
            if (found==false){
            partsTableView.setItems(getAllParts());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Part Not Found");
            alert.setContentText("Please search by exact part name or ID");
            
            alert.showAndWait();
            }
        }
        }
    }
    
    public void setProduct(Product product) {
        selectedProduct = product;
        currentParts = selectedProduct.getAllAssociatedParts();
        
        productIDText.setText(Integer.toString(product.getProductID()));
        productNameText.setText(product.getName());
        productStockText.setText(Integer.toString(product.getStock()));
        productPriceText.setText(Double.toString(product.getPrice()));
        productMinText.setText(Integer.toString(product.getMin()));
        productMaxText.setText(Integer.toString(product.getMax()));
        associatedPartsTableView.setItems(currentParts);
        }

}
