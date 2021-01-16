
/**
 * @author Brian Klein
 * Date: 8-21-17
 * Program: HardwareStore.java
 * Description: This is a menu driven non-GUI client program.
 */
import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;

public class HardwareStore {

    static final int SIZE = 15;

    public static void main(String[] args) throws IOException {

        //declare an array of objects
        Inventory[] list = new Inventory[SIZE];

        String fileName;

        while (true) {

            //create a JFileChooser object
            JFileChooser openChooser = new JFileChooser("./"); //current folder

            int status = openChooser.showOpenDialog(null);

            if (status == JFileChooser.APPROVE_OPTION) { //open button

                fileName = openChooser.getSelectedFile().getAbsolutePath();
                break;

            }//end if

        }//end while

        //read data file
        Scanner inFile = new Scanner(new FileReader(fileName));

        //remove the data file heading
        for (int i = 0; i < 4; i++) {
            inFile.next();
        }

        //read data
        int count = 0;
        while (inFile.hasNext()) {
            int id = inFile.nextInt();
            String name = inFile.next();
            int numberInStock = inFile.nextInt();
            double unitPrice = inFile.nextDouble();

            //create an object and place it into the array
            list[count] = new Inventory(id, name, numberInStock, unitPrice);

            count++;
        }//end while

        boolean flag = true;
        int userCommand;
        Scanner console = new Scanner(System.in);

        while (flag) {

            displayMenu();

            userCommand = console.nextInt();

            switch (userCommand) {
                case 1:
                    for (int i = 0; i < list.length; i++) {
                        System.out.println(list[i].toString());
                    }
                    break;
                    
                case 2:
                    double sum = 0.0;

                    for (int i = 0; i < list.length; i++) {
                        sum += list[i].calculateInStockValue();
                    }
                    System.out.printf("%nTotal In-Stock Value: $ %.2f %n", sum);
                    break;

                case 3:
                    //find the item with the highest in-stock value
                    int maxIndex = 0;

                    for (int i = 1; i < list.length; i++) {
                        if (list[maxIndex].calculateInStockValue() < list[i].calculateInStockValue()) {
                            maxIndex = i;
                        }
                    }

                    System.out.println(list[maxIndex]);
                    break;

                case 4:
                    //find items with less than 10 pieces in stock
                    for (int i = 1; i < list.length; i++) {
                        if (list[i].getNumberInStock() < 10) {
                            System.out.println(list[i]);
                        }
                    }
                    break;

                case 5:
                    System.out.print("\nEnter an item to search: ");
                    String name = console.next();

                    int foundIndex = -1;
                    for (int i = 0; i < list.length; i++) {
                        if (list[i].getName().equalsIgnoreCase(name)) {
                            foundIndex = i;
                            break;
                        }
                    }
                    if (foundIndex == -1) {
                        System.out.println(name + " is not found.");
                    } else {
                        System.out.println(list[foundIndex]);
                    }
                    break;

                case 6:
                    selectionSortByID(list);

                    System.out.println("Array is sorted by ID");
                    break;

                case 7:
                    selectionSortByName(list);

                    System.out.println("Array is sorted by Name");
                    break;

                case 8:
                    selectionSortByName(list);

                    //search an item
                    System.out.print("\nEnter an item to search: ");
                    name = console.next();

                    foundIndex = binarySearchByName(list, name, 0, list.length - 1);

                    if (foundIndex == -1) {
                        System.out.println(name + "is not found.");
                    } else {
                        System.out.println(list[foundIndex]);
                    }
                    break;

                case 9:
                    quickSort(list, 0, list.length - 1);
                    
                    System.out.println("The list has been sorted using Quick Sort.");
                    break;
                    
                case 0:
                    flag = false;
                    break;
                    
                default:
                    System.out.println("Invalid command, try again.");
            }//end switch

        }//end while

    }//end main

    private static void displayMenu() {

        System.out.print("\n\n"
                + "1 -- Output the inventory\n"
                + "2 -- Output the total inventory\n"
                + "3 -- Find the item with the highest in-stock value\n"
                + "4 -- Find the items that have less than 10 pieces in stock\n"
                + "5 -- Search a particular item\n"
                + "6 -- Sort by ID\n"
                + "7 -- Sort by name\n"
                + "8 -- Search an item by name (binary search)\n"
                + "9 -- Quick Sort by Name\n"
                + "0 -- Exit\n\n"
                + "Enter command:");

    }

    public static void selectionSortByID(Inventory[] list) {

        int minIndex;
        Inventory temp;

        for (int x = 0; x < list.length - 1; x++) {//outer loop

            minIndex = x;

            for (int i = x + 1; i < list.length; i++) {//inner loop

                if (list[i].getId() < list[minIndex].getId()) {
                    minIndex = i;

                }//end if

            }//end inner loop

            //swap
            temp = list[x];
            list[x] = list[minIndex];
            list[minIndex] = temp;

        }//end outer loop

    }//end selectionSort()

    public static void selectionSortByName(Inventory[] list) {

        int minIndex;
        Inventory temp;

        for (int x = 0; x < list.length - 1; x++) {//outer loop

            minIndex = x;

            for (int i = x + 1; i < list.length; i++) {//inner loop

                if (list[i].getName().compareToIgnoreCase(list[minIndex].getName()) < 0) {
                    minIndex = i;

                }//end if

            }//end inner loop

            //swap
            temp = list[x];
            list[x] = list[minIndex];
            list[minIndex] = temp;

        }//end outer loop

    }//end selectionSort()

    //binary search using recursion
    public static int binarySearchByName(Inventory list[], String key, int left, int right) {

        int middle = (left + right) / 2;

        int foundIndex = -1;

        if (key.compareToIgnoreCase(list[middle].getName()) == 0) {
            foundIndex = middle;
        } else if (key.compareToIgnoreCase(list[middle].getName()) < 0) {
            if (left <= middle - 1) {
                foundIndex = binarySearchByName(list, key, left, middle - 1);// further search on the left
            }
        } else {
            if (right >= middle + 1) {
                foundIndex = binarySearchByName(list, key, middle + 1, right);//further search on the right
            }
        }

        return foundIndex;

    }//end binarySearch()

    //quick sort method
    public static void quickSort(Inventory [] list, int left, int right) {

        if (left < right) {
            int q = partition(list, left, right);
            quickSort(list, left, q);
            quickSort(list, q + 1, right);
        }
    }

    //partition method
    public static int partition(Inventory [] list, int left, int right) {

        Inventory x = list[left]; //pivot
        int i = left - 1;
        int j = right + 1;

        while (true) {

            j--;
            while(list[j].getName().compareTo(x.getName()) > 0 ){
                j--;
            }

            i++;
            while (list[i].getName().compareTo(x.getName()) < 0 ){
                i++;
            }

            if (i < j) { //swap list[i] with list[j]
                Inventory temp = list[j];
                list[j] = list[i];
                list[i] = temp;
            } else {
                return j;
            }

        }//end outer while

    }//end partition
}//end class
