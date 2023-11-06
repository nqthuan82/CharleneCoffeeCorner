package charlene.coffee;

import charlene.coffee.services.PosService;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var posService = new PosService();

        char makeNewReceipt = '\0';
        var scan = new Scanner(System.in);

        do
        {
            posService.printProductsList();

            posService.resetCurrentReceipt();

            System.out.println("Please enter the product IDs as above products list (comma-delimited string i.e.: 1,2,3 ): \t");
            var productIds = scan.nextLine();
            try {
                posService.order(productIds.split(","));
            }
            catch (NoSuchElementException e){
                makeNewReceipt = 'y';
                System.out.println("\n !!! Invalid product IDs !!!");
                continue;
            }

            System.out.print("\nApply the 5th beverage FREE for the customer? (y or n): ");
            var applyFifthBeverageFree = scan.next().charAt(0);
            if((applyFifthBeverageFree == 'y' || applyFifthBeverageFree == 'Y')){
                posService.bonusFifthBeverage();
            }

            posService.printReceipt();

            System.out.print("\nAdd new receipt ? (y or n): ");
            makeNewReceipt = scan.next().charAt(0); //reads a character Y or N
            scan.nextLine();
        }
        while (makeNewReceipt == 'y' || makeNewReceipt == 'Y');

        scan.close();

    }
}
