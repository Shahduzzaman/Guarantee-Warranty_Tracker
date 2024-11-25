package main_pkg;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;



public class HomePageController implements Initializable {

    @FXML
    private Button AddNewProduct;
    @FXML
    private Button SellProduct;
    @FXML
    private Button ReturnProduct;
    @FXML
    private Label clockLabel;
    @FXML
    private Button exitButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClockUtil clockUtil = new ClockUtil(clockLabel);
        // TODO
    }    
    

    @FXML
    private void addNewProduct(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddNewProduct.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage currentStage = (Stage) AddNewProduct.getScene().getWindow();
        currentStage.setScene(scene);
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void sellProduct(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SellProduct.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage currentStage = (Stage) SellProduct.getScene().getWindow();
        currentStage.setScene(scene);
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void returnProduct(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReturnProduct.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage currentStage = (Stage) ReturnProduct.getScene().getWindow();
        currentStage.setScene(scene);
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void ExitButtonOnClick(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void hyp_link(ActionEvent event) {
    String gmailUrl = "https://mail.google.com/mail/?view=cm&fs=1&to=mdshahduzzaman@gmail.com";
    String facebookUrl = "https://www.facebook.com/md.shahduzzaman.s";

    try {
        URI gmailUri = new URI(gmailUrl);
        URI facebookUri = new URI(facebookUrl);
        Desktop.getDesktop().browse(gmailUri);
        Desktop.getDesktop().browse(facebookUri);
    } catch (IOException | URISyntaxException e) {
        e.printStackTrace();
    }
}
    
}
