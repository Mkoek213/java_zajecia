package csvreader;

public class Product {
    public String product_code;
    public String name;
    public double price;
    public int vat;
    public String unit;
    public String category;
    public String producer;
    public double weight;
    public int stock;
    public String delivery;

    public Product(String productCode, String name, double price, double vat, String unit, String category, String producer, double weight, int stock, String delivery) {
        this.product_code = productCode;
        this.name = name;
        this.price = price;
        this.vat = (int) vat;
        this.unit = unit;
        this.category = category;
        this.producer = producer;
        this.weight = weight;
        this.stock = stock;
        this.delivery = delivery;
    }

    public String toString() {
        return "Product{" +
                "product_code='" + product_code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", vat=" + vat +
                ", unit='" + unit + '\'' +
                ", category='" + category + '\'' +
                ", producer='" + producer + '\'' +
                ", weight=" + weight +
                ", stock=" + stock +
                ", delivery='" + delivery + '\'' +
                '}';
    }


    public int getStock() {
        return stock;
    }

    public double getWeight() {
        return weight;
    }

}



