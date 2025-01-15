package csvreader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SuperToys {
    // a)
    private static List<Product> readProducts() throws IOException, IOException {
        List<Product> products = new ArrayList<>();
        CSVReader reader = new CSVReader("kolos/resources/super-toys.csv", ",", true);
        System.out.println(reader.getColumnLabels());

        while (reader.next()) {
            String productCode = reader.get(0);
            String name = reader.get(2);
            String priceStr = reader.get(3);
            double price = 0.0;
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                System.err.println("Invalid price format for product " + name + ": " + priceStr);
            }
            String vatStr = reader.get(4);
            int vat = 0;
            try {
                vat = Integer.parseInt(vatStr);
            } catch (NumberFormatException e) {
                System.err.println("Invalid VAT format for product " + name + ": " + vatStr);
            }
            String unit = reader.get(5);
            String category = reader.get(6);
            String producer = reader.get(7);
            String weightStr = reader.get(8);
            double weight = 0.0;
            try {
                weight = Double.parseDouble(weightStr);
            } catch (NumberFormatException e) {
                System.err.println("Invalid weight format for product " + name + ": " + weightStr);
            }
            String stockStr = reader.get(9);
            int stock = 0;
            try {
                stock = Integer.parseInt(stockStr);
            } catch (NumberFormatException e) {
                System.err.println("Invalid stock format for product " + name + ": " + stockStr);
            }
            String delivery = reader.get(11);
            products.add(new Product(productCode, name, price, vat, unit, category, producer, weight, stock, delivery));
        }
    return products;
    }

    public static void main(String[] args) throws IOException {
        List<Product> products = readProducts();
        // b)
        products.stream()
                .filter(p -> p.category.contains("Domki dla lalek"))
                .max(Comparator.comparingDouble(p -> p.price))
                .ifPresent(product -> System.out.println("najdroższy produkt z kategorii zawierającej słowo Domek dla lalek: " + product));

        // c)
        List<Product> fordProducts = products.stream()
                .filter(p -> p.name.toLowerCase().contains("ford"))
                .sorted(Comparator.comparingInt(Product::getStock).thenComparingDouble(Product::getWeight))
                .collect(Collectors.toList());
        System.out.println("Produkty z 'ford' w nazwie");
        fordProducts.forEach(System.out::println);

        // d)
        double avgPrice = products.stream()
                .filter(p -> p.category.toLowerCase().contains("pojazdy zdalnie sterowane"))
                .filter(p -> !p.category.toLowerCase().contains("latające"))
                .mapToDouble(p -> p.price)
                .average()
                .orElse(0.0);
        System.out.printf("średnią cenę zabawek z kategorii zawierającej frazę Pojazdy zdalnie sterowane ale nie podkategorii latające: %.2f\n", avgPrice);

        // e)
        double totalValue = products.stream()
                .mapToDouble(p -> p.price * (1 + p.vat / 100) * p.stock)
                .sum();
        System.out.printf("sumaryczną wartość brutto: %.2f\n", totalValue);

        // f)
        List<Product> czarnyProducts = products.stream()
                .filter(p -> p.product_code.toLowerCase().contains("czarny"))
                .filter(p -> p.price > 100)
                .sorted(Comparator.comparingDouble(Product::getWeight))
                .collect(Collectors.toList());
        System.out.println("produkty, których pole product_code zawiera słowo CZARNY', o cenie większej niż 100:");
        czarnyProducts.forEach(System.out::println);
    }

}
