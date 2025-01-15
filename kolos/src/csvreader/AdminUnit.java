package csvreader;

import java.util.ArrayList;
import java.util.List;

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
    AdminUnitList neighbours;
    List<String> neighbours_names = new ArrayList<>();


    public String toString() {
        for (AdminUnit child : children) {
            // check if it is already in the list
            if (!children_names.contains(child.name)){
                children_names.add(child.name);
            }
        }
        for (AdminUnit neighbour : neighbours.units) {
            // check if it is already in the list
            if (!neighbours_names.contains(neighbour.name)){
                neighbours_names.add(neighbour.name);
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
                    ", neighbours=" + neighbours_names +
                    ", bbox=" + bbox.ToString() +
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
                ", neighbours=" + neighbours_names +
                ", bbox=" + bbox.ToString() +
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
