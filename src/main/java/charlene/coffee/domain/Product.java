package charlene.coffee.domain;

public class Product {
    // properties
    private String id;
    private String name;
    private double price;
    private String currency;
    private ProductCategory category;
    private double taxRate;

    // constructor
    public Product(String id, String name, double price, ProductCategory category, String currency, double taxRate)
    {
        this.id=id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.currency = currency;
        this.taxRate = taxRate;
    }

    // getter methods
    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public double getPrice()
    {
        return price;
    }

    public double getTax()
    {
        return taxRate*price/(1 + taxRate);
    }

    public ProductCategory getCategory()
    {
        return category;
    }

    public String getPriceWithCurrency()
    {
        return String.format("%5.2f %s",price, currency);
    }
}
