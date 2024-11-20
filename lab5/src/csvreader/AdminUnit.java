package csvreader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.foreign.MemorySegment.NULL;

public class AdminUnit {
    String name;
    int adminLevel;
    Long id;
    double population;
    double area;
    double density;
    AdminUnit parent;
    BoundingBox bbox = new BoundingBox();
    List<AdminUnit> children;
    List<String> children_names = new ArrayList<>();


    // Dodaj metodę toString() wypisującą podstawowe informacje (nazwa, typ jednostki, powierzchnia, itp.)
    public String toString() {
        for (AdminUnit child : children) {
            // check if it is already in the list
            if (!children_names.contains(child.name)){
                children_names.add(child.name);
            }
        }
        if (parent == null){
            return "AdminUnit{" +
                    "name='" + name + '\'' +
                    ", adminLevel=" + adminLevel +
                    ", population=" + population +
                    ", children=" + children_names +
                    ", area=" + area +
                    ", density=" + density +
                    ", parent=" + " " +
                    ", xmin=" + bbox.xmin +
                    ", ymin=" + bbox.ymin +
                    ", xmax=" + bbox.xmax +
                    ", ymax=" + bbox.ymax +
                    '}';
        }
        return "AdminUnit{" +
                "name='" + name + '\'' +
                ", adminLevel=" + adminLevel +
                ", population=" + population +
                ", children=" + children_names +
                ", area=" + area +
                ", density=" + density +
                ", parent=" + parent.name +
                ", xmin=" + bbox.xmin +
                ", ymin=" + bbox.ymin +
                ", xmax=" + bbox.xmax +
                ", ymax=" + bbox.ymax +
                '}';
    }

    public void fixMissingValues() {
        if (population == 0) {
            population = area * density;
        }
        if (density == 0) {
            if (parent != null) {
                density = parent.density;
            }
        }
    }
}