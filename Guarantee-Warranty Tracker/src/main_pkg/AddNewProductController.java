/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main_pkg;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mdsha
 */
public class AddNewProductController implements Initializable {

    @FXML
    private TextField NameTextField;
    @FXML
    private TextField SlNoTextField;
    @FXML
    private DatePicker DatePicker;
    @FXML
    private Label clockLabel;
    @FXML
    private Button Return;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClockUtil clockUtil = new ClockUtil(clockLabel);
        DatePicker.setValue(LocalDate.now());
        // TODO
    }    

    @FXML
    private void addProduct(ActionEvent event) {
    String name = NameTextField.getText();
    String number = SlNoTextField.getText();

    if (name == null || name.trim().isEmpty() || number == null || number.trim().isEmpty()) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setContentText("Name or Serial Number cannot be empty!");
        errorAlert.showAndWait();
        return;
    }

    LocalDate date = DatePicker.getValue();

    
    if (isNewProductNumberUnique(number)) {
        NewProduct item = new NewProduct(name, number, date);

        boolean addStatus = NewProduct.addNewProduct(item, "Available Product List.bin");

        if (addStatus) {
            String headerText = "Name: " + name + "\nSL Number: " + number + "\nDate: " + date;
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setHeaderText("New Product Added Successfully!");
            successAlert.setContentText(headerText);
            successAlert.showAndWait();
            NameTextField.clear();
            SlNoTextField.clear();
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Oops, something went wrong");
            errorAlert.showAndWait();
        }
    } else {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setContentText("Product with the same SL Number already exists!");
        errorAlert.showAndWait();
    }
}

    private boolean isNewProductNumberUnique(String number) {
        List<Object> productList = NewProduct.readObjectsFromFile("Available Product List.bin");
        for (Object obj : productList) {
            if (obj instanceof NewProduct) {
                NewProduct product = (NewProduct) obj;
                if (product.getNumber().equals(number)) {
                    return false; 
                }
            }
        }
        return true;
    }

    @FXML
    private void Return(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage currentStage = (Stage) Return.getScene().getWindow();
        currentStage.setScene(scene);
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
}
