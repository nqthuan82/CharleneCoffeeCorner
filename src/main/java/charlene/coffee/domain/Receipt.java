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

    public void resetOrderItems(){
        orderItems.clear();
    }

    public List<OrderItem> getBonusItems() {
        var bonusItems = new ArrayList<OrderItem>();

        // Apply fifth beverage bonus (if there are more than one beverage, give bonus with the cheapest one)
        if(isFifthBeverage){
            var firstBeverage = orderItems.stream()
                .filter(item -> item.getProduct().getCategory() == ProductCategory.BEVERAGE)
                .sorted((a ,b) -> b.getProduct().getPrice() > a.getProduct().getPrice() ? -1 : 1)
                .findFirst()
                .orElse(null);

            if(firstBeverage != null)
                bonusItems.add(new OrderItem(firstBeverage.getProduct(), true));
        }

        // Apply extra bonus (if there are more than one extra, give bonus with the cheapest one)
        if(orderItems.stream().anyMatch(item -> item.getProduct().getCategory() == ProductCategory.SNACK)){
            var firstExtra = orderItems.stream()
                .filter(item -> item.getProduct().getCategory() == ProductCategory.EXTRAS)
                .sorted((a ,b) -> b.getProduct().getPrice() > a.getProduct().getPrice() ? -1 : 1)
                .findFirst()
                .orElse(null);

            if(firstExtra != null)
                bonusItems.add(new OrderItem(firstExtra.getProduct(), true));
        }
        return bonusItems;
    }

    public double getReceiptTotalPrice(){
        double receiptTotalPrice = 0.00;

        for(var orderItem : orderItems)
            receiptTotalPrice += orderItem.getTotalPrice();

        return round(receiptTotalPrice - getReceiptTotalBonuses());
    }

    public double getReceiptTotalTax(){
        double receiptTotalTax = 0.00;

        for(var orderItem : orderItems)
            receiptTotalTax += orderItem.getTotalTax();

        for(var bonusItem : getBonusItems())
            receiptTotalTax -= bonusItem.getTotalTax();

        return round(receiptTotalTax);
    }

    public double getReceiptTotalBonuses(){
        double totalBonuses = 0.00;
        for(var bonusItem : getBonusItems())
            totalBonuses += bonusItem.getTotalPrice();

        return round(totalBonuses);
    }

    private static double round(double number){
        return Math.round(number*100.0)/100.0;
    }
}
