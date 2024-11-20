package csvreader;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminUnitList {
    List<AdminUnit> units = new ArrayList<>();
//    Przetwórzmy prawidłowo informacje o parent jednostce nadrzędnej. W pliku są zapisywane wartości long, które chcemy zamienić na referencje. A więc potrzebne jest odwzorowanie (mapa) przypisująca Long id → AdminUnit. Potrzebne jest zapewne także odwzorowanie AdminUnit → Long parentid.
//
//    Zbieraj te informacje podczas czytania rekordów
//    Po zakończeniu czytania ustaw odpowiednie referencje
//    Niektóre rekordy (województwa) nie mają jednostki nadrzędnej. Przypisz im parent=null
//
//    Uwaga mapa ma służyć wyłącznie do odczytu. Nie ma być atrybutem klasy
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
            unit.bbox.xmin = Math.min(x1, Math.min(x2, x3));
            unit.bbox.xmax = Math.max(x1, Math.max(x2, x3));
            unit.bbox.ymin = Math.min(y1, Math.min(y2, y3));
            unit.bbox.ymax = Math.max(y1, Math.max(y2, y3));
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
    }

    public void fixMissingValues() {
//        dla każdego obiektu AdminUnit, z brakującymi danymi population oraz density
//          brakujące dane są NULL
//        przyjmie estymację density = parent.density
//        obliczy population = area*density
//        Problem :!:. W jednostce parent też może brakować wartości. Chyba funkcja AdminUnit.fixMissingValues() (lub fixMissingValues(AdminUnit au)) powninna być rekurencyjna?
        for (AdminUnit unit: units){
            if (unit.density == 0){
                unit.fixMissingValues();
            }
            if (unit.population == 0){
                unit.population = unit.area * unit.density;
                // crop it to 0 places after comma
                unit.population = (int) unit.population;
            }
        }
    }


    void list(PrintStream out){
    /**
     * Wypisuje zawartość korzystając z AdminUnit.toString()
     * @param out
     */
        for (AdminUnit unit : units) {
            out.println(unit.toString());
        }
    }


    void list(PrintStream out,int offset, int limit ){
    /**
     * Wypisuje co najwyżej limit elementów począwszy od elementu o indeksie offset
     * @param out - strumień wyjsciowy
     * @param offset - od którego elementu rozpocząć wypisywanie
     * @param limit - ile (maksymalnie) elementów wypisać
     */
        for (int i = offset; i < offset + limit && i < units.size(); i++) {
            out.println(units.get(i).toString());
        }
    }


    AdminUnitList selectByName(String pattern, boolean regex){
    /**
     * Zwraca nową listę zawierającą te obiekty AdminUnit, których nazwa pasuje do wzorca
     * @param pattern - wzorzec dla nazwy
     * @param regex - jeśli regex=true, użyj finkcji String matches(); jeśli false użyj funkcji contains()
     * @return podzbiór elementów, których nazwy spełniają kryterium wyboru
     * W poniższych przykładach zawsze rezultatem będzie true
     *
     *         System.out.println("województwo małopolskie".matches(".*małop.*"));
     *         System.out.println("województwo małopolskie".matches("^wojew.*"));
     *         System.out.println("województwo pomorskie".matches(".*skie"));
     *         System.out.println("województwo małopolskie".contains("małop"));
     */

        AdminUnitList ret = new AdminUnitList();
        // przeiteruj po zawartości units
        // jeżeli nazwa jednostki pasuje do wzorca dodaj do ret
        for (AdminUnit unit : units) {
            if (regex && unit.name.matches(pattern)) {
                ret.units.add(unit);
            } else if (!regex && unit.name.contains(pattern)) {
                ret.units.add(unit);
            }
        }
        return ret;
    }

//    TODO Wypisz wybrane jednostki wywołując selectByName() oraz list().
//
//    Projekt interfejsu w stylu lista zwraca listę obiektów spełniających kryteria będziemy dalej rozwijać. To jest całkiem wydajne. Lista jest tablicą referencji (8-bajtowych wartości). Obiekty nie są kopiowane, więc listy nie zużywają dużo pamięci w porównaniu do obecnie dostępnych zasobów.
//  TEST V1
    public static void main(String[] args) throws IOException {
        AdminUnitList units = new AdminUnitList();
        units.read("/Users/mikolaj/Desktop/work/java_zajecia/lab5/resources/admin-units.csv");
        units.list(System.out);
        units.list(System.out, 10, 5);
        units.selectByName(".*", true).list(System.out);
        units.selectByName(".*", false).list(System.out);
        units.selectByName(".*z.*", true).list(System.out);
        units.selectByName(".*z.*", false).list(System.out);
    }

//    TEST V2

}