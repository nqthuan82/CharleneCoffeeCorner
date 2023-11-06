package charlene.coffee.domain;

public class OrderItem {
    private Product product;
    private int productQty = 1;
    private boolean isBonus = false;

    public OrderItem(Product product, int productQty, boolean isBonus){
        this.product = product;
        this.productQty = productQty;
        this.isBonus = isBonus;
    }

    public OrderItem(Product product, boolean isBonus){
        this.product = product;
        this.isBonus = isBonus;
    }

    public OrderItem(Product product){
        this.product = product;
    }

    public Product getProduct(){
        return product;
    }

    public int getProductQty(){
        return productQty;
    }

    public double getReceiptItemTotalPrice(){
        return (isBonus ? -1 : 1) * product.getPrice() * productQty;
    }

    public double getReceiptItemTotalTax(){
        return (isBonus ? -1 : 1) * product.getTax() * productQty;
    }

    public String getReceiptItemName(){
        return isBonus ? String.format("%s (Bonus)", product.getName()) : product.getName();
    }
}
