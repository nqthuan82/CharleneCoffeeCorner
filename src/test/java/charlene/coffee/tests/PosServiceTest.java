package charlene.coffee.tests;

import charlene.coffee.services.PosService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PosServiceTest {
    private final PosService posService;
    private final HashMap<String, Double> productPricesList;

    public PosServiceTest(){
        posService = new PosService();
        posService.resetCurrentReceipt();

        productPricesList = new HashMap<>() {{
            put("1", 2.50); // Coffee small
            put("2", 3.00); // Coffee medium
            put("3", 3.50); // Coffee large
            put("4", 3.95); // Orange juice
            put("5", 4.50); // Bacon Roll
            put("6", 0.30); // Extra milk
            put("7", 0.50); // Foamed milk
            put("8", 0.90); // Special roast coffee
        }};
    }

    @Test
    public void testOrderingInvalidProduct(){
        assertThrows(NoSuchElementException.class, () -> {
            // invalid product ID
            posService.order("100");
        });
    }

    @Test
    public void testReceiptWithoutBonuses(){
        // Order large coffee with extra milk
        posService.order(new String[]{"3", "6"});

        var receipt = posService.getCurrentReceipt();

        Assertions.assertEquals(0, receipt.getReceiptTotalBonuses());
        Assertions.assertEquals(productPricesList.get("3") + productPricesList.get("6"), receipt.getReceiptTotalPrice());
    }

    @Test
    public void testReceiptWithFifthBeverageBonus(){
        // Order large coffee with extra milk
        posService.order(new String[]{"3", "6"});
        // Has fifth beverage free
        posService.bonusFifthBeverage();

        var receipt = posService.getCurrentReceipt();

        // Has free large coffee
        Assertions.assertEquals(productPricesList.get("3"), receipt.getReceiptTotalBonuses());
        // Must pay only for extra milk
        Assertions.assertEquals(productPricesList.get("6"), receipt.getReceiptTotalPrice());
    }

    @Test
    public void testReceiptWithExtraBonus(){
        // Order large coffee with extra milk, and Bacon Roll
        posService.order(new String[]{"3", "6", "5"});

        var receipt = posService.getCurrentReceipt();

        // Has free extra milk
        Assertions.assertEquals(productPricesList.get("6"), receipt.getReceiptTotalBonuses());
        // Must pay for large coffee and Bacon Roll
        Assertions.assertEquals(productPricesList.get("3") + productPricesList.get("5"), receipt.getReceiptTotalPrice());
    }

    @Test
    public void testReceiptWithFifthBeverageBonusAndExtraBonus(){
        // Order large coffee with extra milk, and Bacon Roll
        posService.order(new String[]{"3", "6", "5"});
        // Has fifth beverage free
        posService.bonusFifthBeverage();

        var receipt = posService.getCurrentReceipt();

        // Has free large coffee and extra milk
        Assertions.assertEquals(productPricesList.get("3") + productPricesList.get("6"), receipt.getReceiptTotalBonuses());
        // Must pay for Bacon Roll
        Assertions.assertEquals(productPricesList.get("5"), receipt.getReceiptTotalPrice());
    }

    @Test
    public void testComplexReceipt(){
        // Order large coffee with extra milk
        posService.order(new String[]{"3", "6"});
        // Order small coffee with special roast
        posService.order(new String[]{"1", "8"});
        // Order bacon roll
        posService.order("5");
        // Order orange juice
        posService.order("4");
        // Has fifth beverage free
        posService.bonusFifthBeverage();

        var receipt = posService.getCurrentReceipt();

        // Has free small coffee (the cheapest beverage) and extra milk (the cheapest extra)
        Assertions.assertEquals(productPricesList.get("1") + productPricesList.get("6"), receipt.getReceiptTotalBonuses());
        // Must pay for large coffee, orange juice, Bacon Roll and special roast extra
        Assertions.assertEquals(productPricesList.get("3") + productPricesList.get("4") + productPricesList.get("5") + productPricesList.get("8"), receipt.getReceiptTotalPrice());
    }
}
