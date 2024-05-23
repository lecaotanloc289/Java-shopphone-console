package Team_7;

public class Product {
    private String product_name;
    private String brand;
    private int quantity;
    private double cost;
    private int id_product;
    private String country;

    public Product (int id_product, String product_name, String brand, int quantity, double cost, String country) {
        this.id_product = id_product;
        this.product_name = product_name;
        this.brand = brand;
        this.quantity = quantity;
        this.cost = cost;
        this.country = country;

    }

    public String getProduct_name() {
        return product_name;
    }

    public String getBrand() {
        return brand;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity (int quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }
    public void setCost (double cost) {this.cost = cost; }
    public int getId_product() {
        return id_product;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString () {
        return getId_product() + ", " + getProduct_name() + ", " + getBrand()
                +  ", " + getQuantity() + ", " + getCost() + ", " + getCountry();
    }
}
