/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main_pkg;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author mdsha
 */
public class SoldProduct implements Serializable{
    private String Name;
    private String Number;
    private LocalDate BuyingDate;
    private LocalDate SellingDate;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String Number) {
        this.Number = Number;
    }

    public LocalDate getBuyingDate() {
        return BuyingDate;
    }

    public void setBuyingDate(LocalDate BuyingDate) {
        this.BuyingDate = BuyingDate;
    }

    public LocalDate getSellingDate() {
        return SellingDate;
    }

    public void setSellingDate(LocalDate SellingDate) {
        this.SellingDate = SellingDate;
    }

    public SoldProduct(String Name, String Number, LocalDate BuyingDate, LocalDate SellingDate) {
        this.Name = Name;
        this.Number = Number;
        this.BuyingDate = BuyingDate;
        this.SellingDate = SellingDate;
    }

    @Override
    public String toString() {
        return "SellProduct{" + "Name=" + Name + ", Number=" + Number + ", BuyingDate=" + BuyingDate + ", SellingDate=" + SellingDate + '}';
    }
    
    
    //-------------File read & write
    public static List<Object> readObjectsFromFile(String fileName) {
           List<Object> objects = new ArrayList<>();
           try (FileInputStream fis = new FileInputStream(fileName);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
               while (true) {
                   try {
                       Object obj = ois.readObject();
                       if (obj != null) {
                           objects.add(obj);
                       }
                   } catch (EOFException e) {
                       break; 
                   }
               }
           } catch (IOException | ClassNotFoundException ex) {
               ex.printStackTrace();
           }
           return objects;
           }
    
        public static boolean writeObjectsToFile(List<Object> objects, String fileName) {
           try (FileOutputStream fos = new FileOutputStream(fileName);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
               for (Object obj : objects) {
                   oos.writeObject(obj);
               }
               return true;
           } catch (IOException ex) {
               ex.printStackTrace();
               return false;
           }
       }
        public static boolean Product(SoldProduct items, String fileName) {
           List<Object> product = readObjectsFromFile(fileName);
           product.add(items);
           return writeObjectsToFile(product, fileName);
       }
        
        public static void updateFile(List<SoldProduct> soldProducts, String fileName) {
            try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
                for (SoldProduct soldProduct : soldProducts) {
                    output.writeObject(soldProduct);
                    }
                   } catch (IOException e) {
            e.printStackTrace();
            }
}
    
}
