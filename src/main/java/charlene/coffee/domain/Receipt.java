package charlene.coffee.domain;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private List<OrderItem> orderItems = new ArrayList<>();
    private int number = (int) (Math.random() * 999999);
    private boolean isFifthBeverage = false;

    public void setFifthBeverage(boolean fifthBeverage) {
        isFifthBeverage = fifthBeverage;
    }

    public int getNumber(){
        return number;
    }

    public void addOrder(OrderItem orderItem){
        orderItems.add(orderItem);
    }

    public List<OrderItem> getReceiptItems() {
        var receiptItems = new ArrayList<>(orderItems);

        // Apply fifth beverage bonus
        if(isFifthBeverage){
            var firstBeverage = orderItems.stream().filter(item -> item.getProduct().getCategory() == ProductCategory.BEVERAGE).findFirst().orElse(null);
            if(firstBeverage != null)
                receiptItems.add(new OrderItem(firstBeverage.getProduct(), true));
        }

        // Apply extra bonus
        if(orderItems.stream().anyMatch(item -> item.getProduct().getCategory() == ProductCategory.SNACK)){
            var firstExtra = orderItems.stream().filter(item -> item.getProduct().getCategory() == ProductCategory.EXTRAS).findFirst().orElse(null);
            if(firstExtra != null)
                receiptItems.add(new OrderItem(firstExtra.getProduct(), true));
        }
        return receiptItems;
    }

    public double getReceiptTotalPrice(){
        var receiptItems = getReceiptItems();
        double receiptTotalPrice = 0;
        for(OrderItem item : receiptItems){
            receiptTotalPrice += item.getReceiptItemTotalPrice();
        }
        return receiptTotalPrice;
    }

    public double getReceiptTotalTax(){
        var receiptItems = getReceiptItems();
        double receiptTotalTax = 0;
        for(OrderItem item : receiptItems){
            receiptTotalTax += item.getReceiptItemTotalTax();
        }
        return receiptTotalTax;
    }
}
