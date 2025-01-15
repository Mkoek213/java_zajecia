package csvreader;

import java.io.IOException;
import java.io.PrintStream;
import java.text.Collator;
import java.util.*;
import java.util.function.Predicate;


public class AdminUnitList {
    List<AdminUnit> units = new ArrayList<>();

    Map<Long, AdminUnit> idToUnit = new HashMap<>();
    Map<AdminUnit, Long> unitToParentId = new HashMap<>();
    Map<Long,List<AdminUnit>> parent2child = new HashMap<>();

    public void read(String filename) throws IOException {
        CSVReader reader = new CSVReader(filename, ",", true);
        while (reader.next()) {
            AdminUnit unit = new AdminUnit();
            idToUnit.put(reader.getLong("id"), unit);
            unit.id = reader.getLong("id");
            if (reader.get("parent") != null) {
                unitToParentId.put(unit, reader.getLong("parent"));
            }

            unit.name = reader.get("name");
            unit.adminLevel = reader.getInt("admin_level");
            unit.population = reader.getDouble("population");
            unit.area = reader.getDouble("area");
            unit.density = reader.getDouble("density");
            double x1 = reader.getDouble("x1");
            double x2 = reader.getDouble("x2");
            double x3 = reader.getDouble("x3");
            double y1 = reader.getDouble("y1");
            double y2 = reader.getDouble("y2");
            double y3 = reader.getDouble("y3");
            unit.bbox.addPoint(x1, y1);
            unit.bbox.addPoint(x2, y2);
            unit.bbox.addPoint(x3, y3);
            units.add(unit);
        }

        // Assign parent references
        for (Map.Entry<AdminUnit, Long> entry : unitToParentId.entrySet()) {
            AdminUnit unit = entry.getKey();
            Long parentId = entry.getValue();
            if (idToUnit.containsKey(parentId)) {
                unit.parent = idToUnit.get(parentId);
                if (!parent2child.containsKey(parentId)) {
                    parent2child.put(parentId, new ArrayList<>());
                }else{
                    parent2child.get(parentId).add(unit);
                }
            } else {
                unit.parent = null;
            }
        }
        fixMissingValues();
        for (AdminUnit unit : units) {
            if (parent2child.containsKey(unit.id)) {
                unit.children = parent2child.get(unit.id);
            }else{
                unit.children = new ArrayList<>();
            }
        }
        // assign neighbours
        for (AdminUnit unit : units) {
            unit.neighbours = getNeighbors(unit, 15);
        }
    }

    public void fixMissingValues() {

        for (AdminUnit unit: units){
            if (unit.density == 0){
                unit.fixMissingValues();
            }
            if (unit.population == 0){
                unit.population = unit.area * unit.density;
                unit.population = (int) unit.population;
            }
        }
    }

    void list(PrintStream out){

        for (AdminUnit unit : units) {
            out.println(unit.toString());
        }
    }

    void list(PrintStream out,int offset, int limit ){

        for (int i = offset; i < offset + limit && i < units.size(); i++) {
            out.println(units.get(i).toString());
        }
    }

    AdminUnitList selectByName(String pattern, boolean regex){

        AdminUnitList ret = new AdminUnitList();
        for (AdminUnit unit : units) {
            if (regex && unit.name.matches(pattern)) {
                ret.units.add(unit);
            } else if (!regex && unit.name.contains(pattern)) {
                ret.units.add(unit);
            }
        }
        return ret;
    }

    AdminUnitList sortInplaceByName(){

        class AdminUnitNameComparator implements Comparator<AdminUnit> {
            @Override
            public int compare(AdminUnit t, AdminUnit t1) {
                Collator c = Collator.getInstance(new Locale("pl", "PL"));
                return c.compare(t.name, t1.name);
            }
        }
        units.sort(new AdminUnitNameComparator());
        return this;
    }

    AdminUnitList sortInplaceByArea(){

        units.sort(new Comparator<AdminUnit>() {
            @Override
            public int compare(AdminUnit t, AdminUnit t1) {
                return Double.compare(t.area, t1.area);
            }
        });
        return this;
    }

    AdminUnitList sortInplaceByPopulation(){

        units.sort((t, t1) -> Double.compare(t.population, t1.population));
        return this;
    }

    AdminUnitList sortInplace(Comparator<AdminUnit> cmp){

        units.sort(cmp);
        return this;
    }

    AdminUnitList sort(Comparator<AdminUnit> cmp){

        AdminUnitList ret = new AdminUnitList();
        for (AdminUnit unit : units) {
            ret.units.add(unit);
        }
        ret.sortInplace(cmp);
        return ret;
    }

    AdminUnitList filter(Predicate<AdminUnit> pred){

        AdminUnitList ret = new AdminUnitList();
        for (AdminUnit unit : units) {
            if (pred.test(unit)) {
                ret.units.add(unit);
            }
        }
        return ret;
    }

    AdminUnitList filter(Predicate<AdminUnit> pred, int limit){

        AdminUnitList ret = new AdminUnitList();
        for (AdminUnit unit : units) {
            if (pred.test(unit) && ret.units.size() < limit) {
                ret.units.add(unit);
            }
        }
        return ret;
    }

    AdminUnitList filter(Predicate<AdminUnit> pred, int offset, int limit){

        AdminUnitList ret = new AdminUnitList();
        int counter = 0;
        for (AdminUnit unit : units) {
            if (pred.test(unit)) {
                if (counter >= offset && ret.units.size() < limit) {
                    ret.units.add(unit);
                }
                counter++;
            }
        }
        return ret;
    }

    AdminUnitList getNeighbors(AdminUnit unit, double maxdistance){

        AdminUnitList neighbors = new AdminUnitList();
        for (AdminUnit neighbor : units) {
            if (unit.adminLevel == neighbor.adminLevel) {
                if (unit.bbox.intersects(neighbor.bbox)) {
                    neighbors.units.add(neighbor);
                } else if (unit.adminLevel == 8) {
                    double distance = unit.bbox.distanceTo(neighbor.bbox);
                    if (distance < maxdistance) {
                        neighbors.units.add(neighbor);
                    }
                }
            }
        }
        return neighbors;
    }

//    TODO Wypisz wybrane jednostki wywołując selectByName() oraz list().
//
//    Projekt interfejsu w stylu lista zwraca listę obiektów spełniających kryteria będziemy dalej rozwijać. To jest całkiem wydajne. Lista jest tablicą referencji (8-bajtowych wartości). Obiekty nie są kopiowane, więc listy nie zużywają dużo pamięci w porównaniu do obecnie dostępnych zasobów.
//  TEST V1
//    public static void main(String[] args) throws IOException {
//        double t1 = System.nanoTime()/1e6;
//        AdminUnitList units = new AdminUnitList();
//        units.read("/Users/mikolaj/Desktop/work/java_zajecia/lab5/resources/admin-units.csv");
//        units.list(System.out);
//        units.list(System.out, 10, 5);
//        units.selectByName(".*", true).list(System.out);
//        units.selectByName(".*", false).list(System.out);
//        units.selectByName(".*z.*", true).list(System.out);
//        units.selectByName(".*z.*", false).list(System.out);
//        double t2 = System.nanoTime()/1e6;
//        System.out.printf(Locale.US,"t2-t1=%f\n",t2-t1);
//    }
//    public static void main(String[] args) throws IOException {
//        // Zademonstruj w kodzie (np. testach), że jesteś w stanie:
//        //
//        //znaleźć wybraną jednostkę na danym poziomie hierarchii
//        //wyznaczyć i wypisać listę sąsiadów
//        //sprawdź wyniki na mapie…
//
//        AdminUnitList units = new AdminUnitList();
//        units.read("/Users/mikolaj/Desktop/work/java_zajecia/lab5/resources/admin-units.csv");
//        int level = 8;
//        String name = "Wieliczka";
//        for (AdminUnit unit : units.units) {
//            if (unit.adminLevel == level && unit.name.equals(name)) {
//                System.out.println(unit);
//                AdminUnitList neighbors = units.getNeighbors(unit, 15);
//                neighbors.list(System.out);
//            }
//        }
//    }


    public static void main(String[] args) throws IOException {

        AdminUnitList units = new AdminUnitList();
        units.read("/Users/mikolaj/Desktop/work/java_zajecia/lab5/resources/admin-units.csv");
        PrintStream out = System.out;
//        //Pytanie 1:
//        units.filter(a->a.name.startsWith("Ż")).sortInplaceByArea().list(out);

//        //Pytanie 2 wybór (i sortowanie) elementów zaczynających się na “K”:
//        units.filter(a->a.name.startsWith("K")).sortInplaceByName().list(out);

//        //Pytanie 3 wybór jednostek będących powiatami, których parent.name to województwo małopolskie
//        units.filter(a->a.adminLevel == 6 && a.parent.name.equals("województwo małopolskie")).list(out);

//        //Pytanie 4 zaproponuj kilka podobnych kryteriów selekcji:
//        // 1. wybór jednostek będących województwami, których powierzchnia jest większa niż 10000
//        units.filter(a->a.adminLevel == 4 && a.area > 10000).list(out);
//        // 2. wybór jednostek będących powiatami, których gęstość zaludnienia jest większa niż 100
//        units.filter(a->a.adminLevel == 6 && a.density > 100).list(out);

//        // z uzyciem and
//        Predicate<AdminUnit> p1 = a->a.adminLevel == 6;
//        Predicate<AdminUnit> p2 = a->a.parent.name.equals("województwo małopolskie");
//        units.filter(p1.and(p2)).list(out);

        //zapytanie z uzyciem AdminUnitQuery
        Predicate<AdminUnit> p1 = a->a.area>1000;
        Predicate<AdminUnit> p2 = a->a.name.startsWith("Sz");
        AdminUnitQuery query = new AdminUnitQuery()
                .selectFrom(units)
                .where(p1)
                .or(p2)
                .sort(Comparator.comparingDouble(a -> a.area))
                .limit(100);
        query.execute().list(out);
    }

}