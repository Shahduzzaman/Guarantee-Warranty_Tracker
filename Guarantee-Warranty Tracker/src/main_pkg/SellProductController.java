package main_pkg;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SellProductController implements Initializable {

    @FXML
    private TableView<NewProduct> ProductTable;
    @FXML
    private TableColumn<NewProduct, String> T1ProductName;
    @FXML
    private TableColumn<NewProduct, String> T1Number;
    @FXML
    private TableColumn<NewProduct, LocalDate> T1Date;
    @FXML
    private TableView<SoldProduct> SoldProductTable;
    @FXML
    private TableColumn<SoldProduct, String> T2ProductName;
    @FXML
    private TableColumn<SoldProduct, String> T2Number;
    @FXML
    private TableColumn<SoldProduct, LocalDate> T2BuyDate;
    @FXML
    private TableColumn<SoldProduct, LocalDate> T2SellDate;
    @FXML
    private Button SellProduct;
    @FXML
    private DatePicker DatePicker;
    @FXML
    private Label clockLabel;
    @FXML
    private Button Return;
    @FXML
    private TextField SerialNumber;

    // Declare loadedItems as an instance variable
    private ObservableList<NewProduct> loadedItems;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClockUtil clockUtil = new ClockUtil(clockLabel);
        DatePicker.setValue(LocalDate.now());
        T1ProductName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        T1Number.setCellValueFactory(new PropertyValueFactory<>("Number"));
        T1Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        List<Object> productList = NewProduct.readObjectsFromFile("Available Product List.bin");
        loadedItems = FXCollections.observableArrayList();
        for (Object obj : productList) {
            if (obj instanceof NewProduct) {
                loadedItems.add((NewProduct) obj);
            }
        }
        ProductTable.getItems().addAll(loadedItems);

        T2ProductName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        T2Number.setCellValueFactory(new PropertyValueFactory<>("Number"));
        T2BuyDate.setCellValueFactory(new PropertyValueFactory<>("BuyingDate"));
        T2SellDate.setCellValueFactory(new PropertyValueFactory<>("SellingDate"));
        List<Object> productList2 = SoldProduct.readObjectsFromFile("Sold Product List.bin");
        ObservableList<SoldProduct> loadedItem = FXCollections.observableArrayList();
        for (Object obj : productList2) {
            if (obj instanceof SoldProduct) {
                loadedItem.add((SoldProduct) obj);
            }
        }
        SoldProductTable.getItems().addAll(loadedItem);
    }

    @FXML
    private void sellProduct(ActionEvent event) {
        NewProduct selectedCase = ProductTable.getSelectionModel().getSelectedItem();
        if (selectedCase != null) {
            String name = selectedCase.getName();
            String number = selectedCase.getNumber();
            LocalDate buyingDate = selectedCase.getDate();
            LocalDate sellingDate = DatePicker.getValue();
            if (sellingDate != null) {
                SoldProduct pp = new SoldProduct(name, number, buyingDate, sellingDate);
                boolean addStatus = SoldProduct.Product(pp, "Sold Product List.bin");
                if (addStatus) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setHeaderText("Sold a Product Successfully!");
                    a.showAndWait();
                    ProductTable.getItems().remove(selectedCase);
                    SoldProductTable.getItems().add(pp);
                    NewProduct.updateFile(ProductTable.getItems(), "Available Product List.bin");
                    ProductTable.getSelectionModel().clearSelection();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Oops, something went wrong while adding the product to the sold list.");
                    a.showAndWait();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Please select a valid selling date.");
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please select a product to sell.");
            a.showAndWait();
        }
        ProductTable.getSelectionModel().clearSelection();
    
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
    private void searchButton(ActionEvent event) {
        ProductTable.getSelectionModel().clearSelection();
        String serialNumberToSearch = SerialNumber.getText();
        if (serialNumberToSearch == null || serialNumberToSearch.trim().isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Please enter a serial number to search.");
            errorAlert.showAndWait();
            return;
        }
        ObservableList<NewProduct> productList = ProductTable.getItems();
        for (NewProduct product : productList) {
            if (product.getNumber().equalsIgnoreCase(serialNumberToSearch)) {
                ProductTable.getSelectionModel().select(product);
                ProductTable.scrollTo(product);
                return;
            }
        }
        Alert notFoundAlert = new Alert(Alert.AlertType.ERROR);
        notFoundAlert.setHeaderText("Product Not Found");
        notFoundAlert.setContentText("No product found with the provided serial number.");
        notFoundAlert.showAndWait();
    }
}
