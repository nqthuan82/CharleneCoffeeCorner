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

    public List<OrderItem> getOrderItems(){
        return orderItems;
    }

    public List<OrderItem> getBonusItems() {
        var bonusItems = new ArrayList<OrderItem>();

        // Apply fifth beverage bonus
        if(isFifthBeverage){
            var firstBeverage = orderItems.stream().filter(item -> item.getProduct().getCategory() == ProductCategory.BEVERAGE).findFirst().orElse(null);
            if(firstBeverage != null)
                bonusItems.add(new OrderItem(firstBeverage.getProduct(), true));
        }

        // Apply extra bonus
        if(orderItems.stream().anyMatch(item -> item.getProduct().getCategory() == ProductCategory.SNACK)){
            var firstExtra = orderItems.stream().filter(item -> item.getProduct().getCategory() == ProductCategory.EXTRAS).findFirst().orElse(null);
            if(firstExtra != null)
                bonusItems.add(new OrderItem(firstExtra.getProduct(), true));
        }
        return bonusItems;
    }

    public double getReceiptTotalPrice(){
        double receiptTotalPrice = 0;

        for(var orderItem : orderItems)
            receiptTotalPrice += orderItem.getTotalPrice();

        for(var bonusItem : getBonusItems())
            receiptTotalPrice -= bonusItem.getTotalPrice();

        return receiptTotalPrice;
    }

    public double getReceiptTotalTax(){
        double receiptTotalTax = 0;

        for(var orderItem : orderItems)
            receiptTotalTax += orderItem.getTotalTax();

        for(var bonusItem : getBonusItems())
            receiptTotalTax -= bonusItem.getTotalTax();

        return receiptTotalTax;
    }
}
