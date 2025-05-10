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

public class Returned_Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private String Name;
    private String Number;
    private LocalDate BuyingDate;
    private LocalDate SellingDate;
    private LocalDate ReturningDate;

    private static final String DIRECTORY = "Z:\\My Drive\\Sohel Computer & Service Center\\";

    public Returned_Product(String Name, String Number, LocalDate BuyingDate, LocalDate SellingDate, LocalDate ReturningDate) {
        this.Name = Name;
        this.Number = Number;
        this.BuyingDate = BuyingDate;
        this.SellingDate = SellingDate;
        this.ReturningDate = ReturningDate;
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

    public LocalDate getReturningDate() {
        return ReturningDate;
    }

    public void setReturningDate(LocalDate ReturningDate) {
        this.ReturningDate = ReturningDate;
    }

    @Override
    public String toString() {
        return "Returned_Product{" +
                "Name='" + Name + '\'' +
                ", Number='" + Number + '\'' +
                ", BuyingDate=" + BuyingDate +
                ", SellingDate=" + SellingDate +
                ", ReturningDate=" + ReturningDate +
                '}';
    }

    // Helper to generate the full path with .bin extension and ensure directory exists
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

    // Keep the original name: Product
    public static boolean Product(Returned_Product items, String fileName) {
        List<Object> product = readObjectsFromFile(fileName);
        product.add(items);
        return writeObjectsToFile(product, fileName);
    }

    public static void updateFile(List<Returned_Product> returnProducts, String fileName) {
        String fullPath = getFullPath(fileName);
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fullPath))) {
            for (Returned_Product returnProduct : returnProducts) {
                output.writeObject(returnProduct);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Optional: Add JavaFX ObservableList overload
    public static void updateFile(ObservableList<Returned_Product> returnProducts, String fileName) {
        String fullPath = getFullPath(fileName);
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fullPath))) {
            for (Returned_Product returnProduct : returnProducts) {
                output.writeObject(returnProduct);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
