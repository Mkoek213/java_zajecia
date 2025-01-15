package csvreader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

public class AdminUnitQuery {
    AdminUnitList src;
    Predicate<AdminUnit> p = a->true;
    Comparator<AdminUnit> cmp;
    int limit = Integer.MAX_VALUE;
    int offset = 0;


    AdminUnitQuery selectFrom(AdminUnitList src){

        this.src = src;
        return this;
    }

    AdminUnitQuery where(Predicate<AdminUnit> pred){

        this.p = pred;
        return this;
    }

    AdminUnitQuery and(Predicate<AdminUnit> pred){

        this.p = p.and(pred);
        return this;
    }

    AdminUnitQuery or(Predicate<AdminUnit> pred){

        this.p = p.or(pred);
        return this;
    }

    AdminUnitQuery sort(Comparator<AdminUnit> cmp){

        this.cmp = cmp;
        return this;
    }

    AdminUnitQuery limit(int limit){

        this.limit = limit;
        return this;
    }

    AdminUnitQuery offset(int offset){

        this.offset = offset;
        return this;
    }

    AdminUnitList execute(){

        AdminUnitList result = new AdminUnitList();
        result.units = new ArrayList<>(src.units); // Copy the source list
        result = result.filter(p, offset, limit); // Apply filtering with offset and limit
        if (cmp != null) {
            result.sortInplace(cmp); // Apply sorting if comparator is set
        }
        return result;
    }
}
