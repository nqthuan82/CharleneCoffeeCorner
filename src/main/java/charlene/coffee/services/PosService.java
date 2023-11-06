package charlene.coffee.services;
import charlene.coffee.domain.Receipt;
import charlene.coffee.domain.OrderItem;
import charlene.coffee.domain.Product;
import charlene.coffee.domain.ProductCategory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PosService {
    private final List<Product> products;
    private final String currency = "CHF";
    private final double taxRate  =  0.025;
    private Receipt currentReceipt;

    public PosService()
    {
        currentReceipt = new Receipt();
        products = new ArrayList<>(
            List.of(
                new Product("1", "Coffee small", 2.50, ProductCategory.BEVERAGE, currency, taxRate),
                new Product("2", "Coffee medium", 3.00, ProductCategory.BEVERAGE, currency, taxRate),
                new Product("3", "Coffee large", 3.50, ProductCategory.BEVERAGE, currency, taxRate),
                new Product("4", "Orange juice", 3.95, ProductCategory.BEVERAGE, currency, taxRate),
                new Product("5", "Bacon Roll", 4.50, ProductCategory.SNACK, currency, taxRate),
                new Product("6", "Extra milk", 0.30, ProductCategory.EXTRAS, currency, taxRate),
                new Product("7", "Foamed milk", 0.50, ProductCategory.EXTRAS, currency, taxRate),
                new Product("8", "Special roast coffee", 0.90, ProductCategory.EXTRAS, currency, taxRate)
            )
        );
    }

    public Receipt getCurrentReceipt() {
        return currentReceipt;
    }

    public void order(String[] productIds){
        for(var pId: productIds)
            order(pId);
    }

    public void order(String productId){
        var product = products.stream().filter(p -> p.getId().equalsIgnoreCase(productId)).findFirst().orElseThrow();
        currentReceipt.addOrder(new OrderItem(product));
    }

    public void bonusFifthBeverage(){
        currentReceipt.setFifthBeverage(true);
    }

    public void printReceipt(){
        System.out.println("\n\n\n\t\t\t\t\t Coffee Corner");
        System.out.println("\t\t\t\t\t   Charlene");
        System.out.println("\t\t\t Zollikerstrasse 788, 8008 Zurich");
        System.out.println("\n\t\t\t\tTelephone: (+41) 0442520353");
        System.out.println("\t\t\t\t  CHE-101.397.516 VAT");
        //format of date and time
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss      dd.MM.yy");
        Date date = new Date();
        //prints current date and time
        System.out.format("\n\nReceipt-Nr: %6d \t\t\t\t\t %s\n", currentReceipt.getNumber(), formatter.format(date));

        System.out.format("\n-------------------------------------------------------------\n");
        for(var orderItem: currentReceipt.getOrderItems()){
            var product = orderItem.getProduct();
            System.out.format("%3dx\t %-30s %-10s %5.2f %s\n",
                orderItem.getProductQty(),
                orderItem.getName(),
                product.getPriceWithCurrency(),
                orderItem.getTotalPrice(),
                currency);
        }

        System.out.format("\nBonuses: \n");
        for(var bonusItem: currentReceipt.getBonusItems()){
            var product = bonusItem.getProduct();
            System.out.format("%3dx\t %-30s %-10s %5.2f %s\n",
                    bonusItem.getProductQty(),
                    bonusItem.getName(),
                    product.getPriceWithCurrency(),
                    -bonusItem.getTotalPrice(),
                    currency);
        }

        System.out.format("\n-------------------------------------------------------------\n");
        System.out.format("\t\t\t\t\tSUM: %5.2f %s", currentReceipt.getReceiptTotalPrice(), currency);

        System.out.format("\n-------------------------------------------------------------\n");
        System.out.format("\nIncl. VAT %2.2f%% \t %5.2f \t = %5.2f %s",
                (taxRate*100),
                currentReceipt.getReceiptTotalPrice(),
                currentReceipt.getReceiptTotalTax(),
                currency);
        System.out.println("\n\n\n");

        // Reset current receipt after printting
        currentReceipt.resetOrderItems();
    }
}
