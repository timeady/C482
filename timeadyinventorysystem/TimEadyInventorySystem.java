/*
 *Timothy Eady
 *C482 Software I
 *
 */
package timeadyinventorysystem;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Model.Part;
import Model.Product;
import View_Controller.MainScreenController;
import View_Controller.PartController;
import View_Controller.ProductController;

public class TimEadyInventorySystem extends Application {
    private Stage primaryStage;
    private AnchorPane mainScreen;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Inventory Management System");

        showMainScreen();
    }
    
    public void showMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TimEadyInventorySystem.class.getResource("/View_Controller/MainScreen.fxml"));
            mainScreen = (AnchorPane) loader.load();   
            MainScreenController controller = loader.getController();
            controller.setMainApp(this);
            Scene scene = new Scene(mainScreen);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public boolean showPartScreen() {
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TimEadyInventorySystem.class.getResource("/View_Controller/Part.fxml"));
        AnchorPane partScreen = (AnchorPane) loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Part");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        Scene scene = new Scene(partScreen);
        stage.setScene(scene);
        PartController controller = loader.getController();
        controller.setStage(stage);
        stage.showAndWait();

        return controller.isClicked();
        } catch (IOException e) {
        return false;
        }
    }
    
    public boolean showModifyPart(Part part) {
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TimEadyInventorySystem.class.getResource("/View_Controller/ModifyPart.fxml"));
        AnchorPane partScreen = (AnchorPane) loader.load();
        Stage stage = new Stage();
        stage.setTitle("Edit Part");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        Scene scene = new Scene(partScreen);
        stage.setScene(scene);
        PartController controller = loader.getController();
        controller.setStage(stage);
        controller.setPart(part);
        stage.showAndWait();
        return controller.isClicked();
        } catch (IOException e) {
        e.printStackTrace();
        return false;
        }
    }
    
    public boolean showProductScreen() {
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TimEadyInventorySystem.class.getResource("/View_Controller/Product.fxml"));
        AnchorPane partScreen = (AnchorPane) loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Product");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        Scene scene = new Scene(partScreen);
        stage.setScene(scene);
        ProductController controller = loader.getController();
        controller.setStage(stage);
        stage.showAndWait();
        return controller.isClicked();
        } catch (IOException e) {
        e.printStackTrace();
        return false;
        }
    }
    
    public boolean showModifyProductScreen(Product product) {
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TimEadyInventorySystem.class.getResource("/View_Controller/ModifyProduct.fxml"));
        AnchorPane partScreen = (AnchorPane) loader.load();
        Stage stage = new Stage();
        stage.setTitle("Edit Product");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        Scene scene = new Scene(partScreen);
        stage.setScene(scene);
        ProductController controller = loader.getController();
        controller.setStage(stage);
        controller.setProduct(product);
        stage.showAndWait();
        return controller.isClicked();
        } catch (IOException e) {
        e.printStackTrace();
        return false;
        }
    }

}

