/**
 * @author Brian Klein
 * Date: 8-21-17
 * Program: Inventory.java
 * Description: This is a user-defined class
 */
public class Inventory {
    
    //data members
    private int id;
    private String name;
    private int numberInStock;
    private double unitPrice;
    
    //constructors

    public Inventory() {
    }

    public Inventory(int id, String name, int numberInStock, double unitPrice) {
        this.id = id;
        this.name = name;
        this.numberInStock = numberInStock;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberInStock() {
        return numberInStock;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberInStock(int numberInStock) {
        this.numberInStock = numberInStock;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    //calculate and return the in stock value of each item
    public double calculateInStockValue() {
        return this.numberInStock * this.unitPrice;
    }

    @Override
    public String toString() {
        return String.format("%-5d %-16s %,-9d $%,-7.2f $%,-13.2f",
                id, name, numberInStock, unitPrice, this.calculateInStockValue());
    }
    
    
    
}
