/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main_pkg;

import java.io.EOFException;
import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
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
public class NewProduct implements Serializable{
    private String Name;
    private String Number;
    private LocalDate Date;

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

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate Date) {
        this.Date = Date;
    }

    public NewProduct(String Name, String Number, LocalDate Date) {
        this.Name = Name;
        this.Number = Number;
        this.Date = Date;
    }

    @Override
    public String toString() {
        return "NewProduct{" + "Name=" + Name + ", Number=" + Number + ", Date=" + Date + '}';
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
        public static boolean addNewProduct(NewProduct items, String fileName) {
           List<Object> product = readObjectsFromFile(fileName);
           product.add(items);
           return writeObjectsToFile(product, fileName);
       }
        
        public static void updateFile(ObservableList<NewProduct> products, String fileName) {
            try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
                for (NewProduct product : products) {
                    output.writeObject(product);
                }
            } catch (IOException e) {
            e.printStackTrace();
            }
}
        private static final long serialVersionUID = 1L;
       
    
    
}
