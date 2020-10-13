/*
 *Timothy Eady
 *C482 Software I
 *
 */
package View_Controller;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import static View_Controller.MainScreenController.modifyIndex;

public class PartController {
    @FXML
    private RadioButton inhouseRadio;

    @FXML
    private Label companyMachineLabel;

    @FXML
    private TextField partIDText;

    @FXML
    private TextField partNameText;

    @FXML
    private TextField partStockText;

    @FXML
    private TextField partPriceText;

    @FXML
    private TextField companyMachineField;

    @FXML
    private TextField partMaxText;

    @FXML
    private TextField PartMinText;

    @FXML
    private RadioButton outsourcedRadioButton;
    
    @FXML
    private ToggleGroup partToggleGroup;
        
    private int partID;
    private Part part;
    private Part selectedPart;
    private OutsourcedPart selectedOutPart;
    private InhousePart selectedInPart;
    private Stage stage;
    private boolean click = false;
    int partIndex = modifyIndex();
                    
    @FXML
    private void initialize() {
        partToggleGroup = new ToggleGroup();
        this.inhouseRadio.setToggleGroup(partToggleGroup);
        this.outsourcedRadioButton.setToggleGroup(partToggleGroup);
        partID = Inventory.getPartInc();
        partIDText.setText("Auto-Generated: "+ partID);
        
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void radioHandler()
    {
         if (this.partToggleGroup.getSelectedToggle().equals(this.inhouseRadio)){
            companyMachineLabel.setText("Machine ID");
            companyMachineField.setPromptText("Machine ID");
         }
         if (this.partToggleGroup.getSelectedToggle().equals(this.outsourcedRadioButton)){
            companyMachineLabel.setText("Company Name");
            companyMachineField.setPromptText("Company Name");
         }
    }
    
    public void setPart(Part part) {
        selectedPart = part;
        
        partIDText.setText(Integer.toString(part.getPartID()));
        partNameText.setText(part.getName());
        partStockText.setText(Integer.toString(part.getStock()));
        partPriceText.setText(Double.toString(part.getPrice()));
        PartMinText.setText(Integer.toString(part.getMin()));
        partMaxText.setText(Integer.toString(part.getMax()));

        if (part instanceof InhousePart) {
            selectedInPart = (InhousePart) part;
            companyMachineLabel.setText("Machine ID");
            inhouseRadio.selectedProperty().set(true);
            companyMachineField.setText(Integer.toString(selectedInPart.getMachineID()));
        } else {
            selectedOutPart = (OutsourcedPart) part;
            companyMachineLabel.setText("Company Name");
            outsourcedRadioButton.selectedProperty().set(true);
            companyMachineField.setText(selectedOutPart.getCompanyName());
        }
    }

    public boolean isClicked() {
        return click;
    }

    @FXML
    private void partSaveHandler() {
        if (checkValidPart()) {
            String name = partNameText.getText();
            String stock = partStockText.getText();
            String price = partPriceText.getText();
            String min = PartMinText.getText();
            String max = partMaxText.getText();
            String companyMachine = companyMachineField.getText();
            if ((this.partToggleGroup.getSelectedToggle().equals(this.inhouseRadio))) {
                InhousePart inPart = new InhousePart();
                inPart.setPartID(partID);
                inPart.setName(name);
                inPart.setPrice(Double.parseDouble(price));
                inPart.setStock(Integer.parseInt(stock));
                inPart.setMin(Integer.parseInt(min));
                inPart.setMax(Integer.parseInt(max));
                inPart.setMachineID(Integer.parseInt(companyMachine));
                Inventory.addPart(inPart);
            
            } else {
                OutsourcedPart outPart = new OutsourcedPart();
                outPart.setPartID(partID);
                outPart.setName(name);
                outPart.setPrice(Double.parseDouble(price));
                outPart.setStock(Integer.parseInt(stock));
                outPart.setMin(Integer.parseInt(min));
                outPart.setMax(Integer.parseInt(max));
                outPart.setCompanyName(companyMachine);
                Inventory.addPart(outPart);
            }
            
            click = true;
            stage.close();
        } 
            
        }
    
    @FXML
    private void modifyPartSaveHandler() {
        if (checkValidPart()) {
            String ID = partIDText.getText();
            String name = partNameText.getText();
            String stock = partStockText.getText();
            String price = partPriceText.getText();
            String min = PartMinText.getText();
            String max = partMaxText.getText();
            String companyMachine = companyMachineField.getText();           
                if ((this.partToggleGroup.getSelectedToggle().equals(this.inhouseRadio))) {
                InhousePart inPart = new InhousePart();
                inPart.setPartID(Integer.parseInt(ID));
                inPart.setName(name);
                inPart.setPrice(Double.parseDouble(price));
                inPart.setStock(Integer.parseInt(stock));
                inPart.setMin(Integer.parseInt(min));
                inPart.setMax(Integer.parseInt(max));
                inPart.setMachineID(Integer.parseInt(companyMachine));
                Inventory.updatePart(partIndex, inPart);
            
            } else {
                OutsourcedPart outPart = new OutsourcedPart();
                outPart.setPartID(Integer.parseInt(ID));
                outPart.setName(name);
                outPart.setPrice(Double.parseDouble(price));
                outPart.setStock(Integer.parseInt(stock));
                outPart.setMin(Integer.parseInt(min));
                outPart.setMax(Integer.parseInt(max));
                outPart.setCompanyName(companyMachine);
                Inventory.updatePart(partIndex, outPart);
            }
            click = true;
            Inventory.cancelPartInc();
            stage.close();   
        }             
    }
    
    @FXML
    void partCancelHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Are you sure you want to Cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            partID = Inventory.cancelPartInc();
            stage.close();
        } else {
            alert.close();
        }        
    }

    private boolean checkValidPart() {
        String name = partNameText.getText();
        String stock = partStockText.getText();
        String price = partPriceText.getText();
        String min = PartMinText.getText();
        String max = partMaxText.getText();
        String companyMachine = companyMachineField.getText();
        String errorMessage = "";
        if (name == null || name.length() == 0) 
            errorMessage += "The part name is not valid.\n"; 
        
        if (stock == null || stock.length() == 0) 
            errorMessage += "The inventory value is not valid.\n";  
        else {
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
            errorMessage += "The price value is not valid.\n"; 
        } else {
            try {
                Double.parseDouble(price);
            } catch (NumberFormatException e) {
                errorMessage += "The price value is not valid.\n"; 
            }
        }
        if (min == null || min.length() == 0) {
            errorMessage += "The minimum stock value is not valid.\n"; 
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
        if (companyMachine == null || companyMachine.length() == 0) {
            errorMessage += "The machineID or company name is not valid.\n"; 
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct the values in order to save this part.");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
        
    }    
         
}
