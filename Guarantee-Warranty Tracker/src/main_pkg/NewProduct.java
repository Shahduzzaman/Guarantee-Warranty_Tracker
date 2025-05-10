package main_pkg;

import java.io.EOFException;
import java.io.File;
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

public class NewProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    private String Name;
    private String Number;
    private LocalDate Date;

    // Fixed directory path
    private static final String DIRECTORY = "Z:\\My Drive\\Sohel Computer & Service Center\\";


    public NewProduct(String Name, String Number, LocalDate Date) {
        this.Name = Name;
        this.Number = Number;
        this.Date = Date;
    }

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

    @Override
    public String toString() {
        return "NewProduct{" + "Name=" + Name + ", Number=" + Number + ", Date=" + Date + '}';
    }

    // Utility method to get the full path
    private static String getFullPath(String fileName) {
        if (!fileName.endsWith(".bin")) {
            fileName += ".bin";
        }
        File dir = new File(DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return DIRECTORY + fileName;
    }

    // Read objects from file
    public static List<Object> readObjectsFromFile(String fileName) {
        List<Object> objects = new ArrayList<>();
        String fullPath = getFullPath(fileName);

        File file = new File(fullPath);
        if (!file.exists()) return objects;

        try (FileInputStream fis = new FileInputStream(fullPath);
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

    // Write objects to file
    public static boolean writeObjectsToFile(List<Object> objects, String fileName) {
        String fullPath = getFullPath(fileName);
        try (FileOutputStream fos = new FileOutputStream(fullPath);
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

    // Add new product
    public static boolean addNewProduct(NewProduct item, String fileName) {
        List<Object> products = readObjectsFromFile(fileName);
        products.add(item);
        return writeObjectsToFile(products, fileName);
    }

    // Update entire file
    public static void updateFile(ObservableList<NewProduct> products, String fileName) {
        String fullPath = getFullPath(fileName);
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fullPath))) {
            for (NewProduct product : products) {
                output.writeObject(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
