/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main_pkg;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mdsha
 */
public class ReturnProductController implements Initializable {

    @FXML
    private Label clockLabel;
    @FXML
    private Button Return;
    @FXML
    private TableView<SoldProduct> SoldProductTable;
    @FXML
    private TableColumn<SoldProduct, String> T1ProductName;
    @FXML
    private TableColumn<SoldProduct, String> T1Number;
    @FXML
    private TableColumn<SoldProduct, LocalDate> T1BuyDate;
    @FXML
    private TableColumn<SoldProduct, LocalDate> T1SellDate;
    @FXML
    private TableView<Returned_Product> RetuenedProductTable;
    @FXML
    private TableColumn<Returned_Product, String> T2ProductName;
    @FXML
    private TableColumn<Returned_Product, String> T2Number;
    @FXML
    private TableColumn<Returned_Product, LocalDate> T2BuyDate;
    @FXML
    private TableColumn<Returned_Product, LocalDate> T2SellDate;
    @FXML
    private TableColumn<Returned_Product, LocalDate> T2ReturnDate;
    @FXML
    private DatePicker DatePicker;
    @FXML
    private TextField SerialNumber;
    @FXML
    private TextField SerialNumber1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClockUtil clockUtil = new ClockUtil(clockLabel);
        DatePicker.setValue(LocalDate.now());
        
        T1ProductName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        T1Number.setCellValueFactory(new PropertyValueFactory<>("Number"));
        T1BuyDate.setCellValueFactory(new PropertyValueFactory<>("BuyingDate"));
        T1SellDate.setCellValueFactory(new PropertyValueFactory<>("SellingDate"));
        List<Object> productList2 = SoldProduct.readObjectsFromFile("Sold Product List.bin");
        ObservableList<SoldProduct> loadedItem = FXCollections.observableArrayList();
        for (Object obj : productList2) {
            if (obj instanceof SoldProduct) {
                loadedItem.add((SoldProduct) obj);
            }
        }
        SoldProductTable.getItems().addAll(loadedItem);
        
        T2ProductName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        T2Number.setCellValueFactory(new PropertyValueFactory<>("Number"));
        T2BuyDate.setCellValueFactory(new PropertyValueFactory<>("BuyingDate"));
        T2SellDate.setCellValueFactory(new PropertyValueFactory<>("SellingDate"));
        T2ReturnDate.setCellValueFactory(new PropertyValueFactory<>("ReturningDate"));
        List<Object> productList = Returned_Product.readObjectsFromFile("Returned Product List.bin");
        ObservableList<Returned_Product> loadedItems = FXCollections.observableArrayList();
        for (Object obj : productList) {
            if (obj instanceof Returned_Product) {
                loadedItems.add((Returned_Product) obj);
            }
        }
        RetuenedProductTable.getItems().addAll(loadedItems);
        // TODO
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

    @FXML
    private void viewDetails(ActionEvent event) {
    SoldProduct selectedCase = SoldProductTable.getSelectionModel().getSelectedItem();

    if (selectedCase != null) {
        String name = selectedCase.getName();
        String number = selectedCase.getNumber();
        LocalDate sellingDate = selectedCase.getSellingDate();
        LocalDate returningDate = DatePicker.getValue();
        
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedSellingDate = sellingDate.format(dateFormat);
        String formattedReturningDate = returningDate.format(dateFormat);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Warranty Months");
        dialog.setHeaderText("Enter the warranty months:");
        dialog.setContentText("Warranty Months:");

        dialog.showAndWait().ifPresent(warrantyMonthsInput -> {
            try {
                int warrantyMonths = Integer.parseInt(warrantyMonthsInput);
                LocalDate expirationDate = sellingDate.plusMonths(warrantyMonths);
                Period period = Period.between(returningDate, expirationDate);
                String formattedexpirationDate = expirationDate.format(dateFormat);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Warranty Status");
                alert.setHeaderText("Product Name: " + name + "\n"
                        + "SL Number: " + number + "\n"
                        + "Selling Date: " + formattedSellingDate + "\n"
                        + "Returning Date: " + formattedReturningDate + "\n"
                        + "Warranty Months: " + warrantyMonths + "\n"
                        + "Expiration Date: " + formattedexpirationDate + "\n");

                if (returningDate.isAfter(expirationDate)) {
                    alert.setContentText("__Product Expired__");
                } else {
                    int yearsLeft = period.getYears();
                    int monthsLeft = period.getMonths();
                    int daysLeft = period.getDays();
                    alert.setContentText("Warranty Left:   " + yearsLeft + " year,  " + monthsLeft + " month,  " + daysLeft + " days");
                }
                alert.showAndWait();       
            } catch (NumberFormatException e) {
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Invalid Input");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Please enter a valid integer for warranty months.");
                errorAlert.showAndWait();
            }
        });
    }
}

    @FXML
    private void addReturnProduct(ActionEvent event) {
        SoldProduct selectedCase = SoldProductTable.getSelectionModel().getSelectedItem();
        
        if (selectedCase != null) {
            String name = selectedCase.getName();
            String number = selectedCase.getNumber();
            LocalDate buyingDate = selectedCase.getBuyingDate();
            LocalDate sellingDate = selectedCase.getSellingDate();
            LocalDate returningDate = DatePicker.getValue();

            if (sellingDate != null) {
                Returned_Product pp = new Returned_Product(name, number, buyingDate, sellingDate, returningDate);
                boolean addStatus = Returned_Product.Product(pp, "Returned Product List.bin");

                if (addStatus) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setHeaderText("Got Returned a Product Successfully!");
                    a.showAndWait();
                    SoldProductTable.getItems().remove(selectedCase);
                    RetuenedProductTable.getItems().add(pp);
                    SoldProduct.updateFile(SoldProductTable.getItems(), "Sold Product List.bin");
                    SerialNumber.clear();
                    SoldProductTable.getSelectionModel().clearSelection();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Oops, something went wrong while adding the product to the return list.");
                    a.showAndWait();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Please select a valid returning date.");
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please select a Product to get Return.");
            a.showAndWait();
        }
    
    }

    @FXML
    private void searchButton(ActionEvent event) {
        SoldProductTable.getSelectionModel().clearSelection();
        String serialNumberToSearch = SerialNumber.getText();

        if (serialNumberToSearch == null || serialNumberToSearch.trim().isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Please enter a serial number to search.");
            errorAlert.showAndWait();
            return;
        }
        ObservableList<SoldProduct> productList = SoldProductTable.getItems();
        for (SoldProduct product : productList) {
            if (product.getNumber().equalsIgnoreCase(serialNumberToSearch)) {
                SoldProductTable.getSelectionModel().select(product);
                SoldProductTable.scrollTo(product);
                return;
            }
    }
        Alert notFoundAlert = new Alert(Alert.AlertType.ERROR);
        notFoundAlert.setHeaderText("Product Not Found");
        notFoundAlert.setContentText("No product found with the provided serial number.");
        notFoundAlert.showAndWait();
    }

    @FXML
    private void searchButton1(ActionEvent event) {
        RetuenedProductTable.getSelectionModel().clearSelection();
        String serialNumberToSearch = SerialNumber1.getText();

        if (serialNumberToSearch == null || serialNumberToSearch.trim().isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Please enter a serial number to search.");
            errorAlert.showAndWait();
            return;
        }
        ObservableList<Returned_Product> productList = RetuenedProductTable.getItems();
        for (Returned_Product product : productList) {
            if (product.getNumber().equalsIgnoreCase(serialNumberToSearch)) {
                RetuenedProductTable.getSelectionModel().select(product);
                RetuenedProductTable.scrollTo(product);
                return;
            }
        }
        Alert notFoundAlert = new Alert(Alert.AlertType.ERROR);
        notFoundAlert.setHeaderText("Product Not Found");
        notFoundAlert.setContentText("No product found with the provided serial number.");
        notFoundAlert.showAndWait();
    
    }

    @FXML
    private void deleteButton(ActionEvent event) {
        Returned_Product selectedCase = RetuenedProductTable.getSelectionModel().getSelectedItem();
        if (selectedCase != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setHeaderText("Confirm Deletion");
            confirmationAlert.setContentText("Are you sure you want to delete this item?");
            ButtonType yesButton = new ButtonType("Yes", ButtonData.OK_DONE);
            ButtonType noButton = new ButtonType("No", ButtonData.CANCEL_CLOSE);
            confirmationAlert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {
                RetuenedProductTable.getItems().remove(selectedCase);
                Returned_Product.updateFile(RetuenedProductTable.getItems(), "Returned Product List.bin");
                SerialNumber1.clear();
                RetuenedProductTable.getSelectionModel().clearSelection();
            }
        } else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setHeaderText("No Item Selected");
            noSelectionAlert.setContentText("Please select an item to delete.");
            noSelectionAlert.showAndWait();
        }
    }
    
}
