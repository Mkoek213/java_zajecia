package csvreader;

public class BoundingBox {
//    Napisz docelową klasę BoundingBox. Jest to prostokąt otaczający jednostkę. Do zdefiniowania prostokąta wystarczą dwa przeciwległe punkty, zamiast pięciu.
//
//    Zaprojektuj BoundingBox tak, aby miał dwa stany:
//
//    BoundingBox może być pusty
//    albo definiuje prostokąt (w tym zdegenerowany do pojedynczego punktu)
//    Sam(a) zadecyduj, jak je odróżnić, np. stosując specjalne wartości Double.NaN albo dodając flagę pusty/niepusty.
    double xmin;
    double ymin;
    double xmax;
    double ymax;


    void addPoint(double x, double y){
        /**
         * Powiększa BB tak, aby zawierał punkt (x,y)
         * Jeżeli był wcześniej pusty - wówczas ma zawierać wyłącznie ten punkt
         * @param x - współrzędna x
         * @param y - współrzędna y
         */

        if (isEmpty()){
            xmin = x;
            ymin = y;
            xmax = x;
            ymax = y;
        }
        else{
            if (x < xmin){
                xmin = x;
            }
            if (x > xmax){
                xmax = x;
            }
            if (y < ymin){
                ymin = y;
            }
            if (y > ymax){
                ymax = y;
            }
        }
    }


    boolean contains(double x, double y){
        /**
         * Sprawdza, czy BB zawiera punkt (x,y)
         * @param x
         * @param y
         * @return
         */

        if (x >= xmin && x <= xmax && y >= ymin && y <= ymax){
            return true;
        }
        return false;
    }


    boolean contains(BoundingBox bb){
        /**
         * Sprawdza czy dany BB zawiera bb
         * @param bb
         * @return
         */
        if (xmin <= bb.xmin && xmax >= bb.xmax && ymin <= bb.ymin && ymax >= bb.ymax){
            return true;
        }
        return false;
    }


    boolean intersects(BoundingBox bb){
        /**
         * Sprawdza, czy dany BB przecina się z bb
         * @param bb
         * @return
         */
        if (xmin < bb.xmax && xmax > bb.xmin && ymin < bb.ymax && ymax > bb.ymin) {
            return true;
        }
        return false;
    }

    BoundingBox add(BoundingBox bb){
        /**
         * Powiększa rozmiary tak, aby zawierał bb oraz poprzednią wersję this
         * Jeżeli był pusty - po wykonaniu operacji ma być równy bb
         * @param bb
         * @return
         */
        if (isEmpty()){
            xmin = bb.xmin;
            ymin = bb.ymin;
            xmax = bb.xmax;
            ymax = bb.ymax;
        }
        else{
            if (bb.xmin < xmin){
                xmin = bb.xmin;
            }
            if (bb.xmax > xmax){
                xmax = bb.xmax;
            }
            if (bb.ymin < ymin){
                ymin = bb.ymin;
            }
            if (bb.ymax > ymax){
                ymax = bb.ymax;
            }
        }
        return this;
    }

    boolean isEmpty(){
        /**
         * Sprawdza czy BB jest pusty
         * @return
         */

        if (xmin == 0 && ymin == 0 && xmax == 0 && ymax == 0){
            return true;
        }
        return false;
    }


    boolean equals_(Object o) {
        /**
         * Sprawdza czy
         * 1) typem o jest BoundingBox
         * 2) this jest równy bb
         * @return
         */

        if (o instanceof BoundingBox) {
            BoundingBox bb = (BoundingBox) o;
            if (xmin == bb.xmin && ymin == bb.ymin && xmax == bb.xmax && ymax == bb.ymax) {
                return true;
            }
            return false;
        }
        return false;
    }


    double getCenterX(){
        /**
         * Oblicza i zwraca współrzędną x środka
         * @return if !isEmpty() współrzędna x środka else wyrzuca wyjątek
         * (sam dobierz typ)
         */

        if (!isEmpty()){
            return (xmin + xmax) / 2;
        }
        throw new RuntimeException("Not implemented");
    }

    double getCenterY(){
        /**
         * Oblicza i zwraca współrzędną y środka
         * @return if !isEmpty() współrzędna y środka else wyrzuca wyjątek
         * (sam dobierz typ)
         */
        if (!isEmpty()){
            return (ymin + ymax) / 2;
        }
        throw new RuntimeException("Not implemented");
    }


    double distanceTo(BoundingBox bbx){
        /**
         * Oblicza odległość pomiędzy środkami this bounding box oraz bbx
         * @param bbx prostokąt, do którego liczona jest odległość
         * @return if !isEmpty odległość, else wyrzuca wyjątek lub zwraca maksymalną możliwą wartość double
         * Ze względu na to, że są to współrzędne geograficzne, zamiast odległości użyj wzoru haversine
         * (ang. haversine formula)
         *
         * Gotowy kod można znaleźć w Internecie...
         */
        if (!isEmpty()){
            double lat1 = getCenterX();
            double lon1 = getCenterY();
            double lat2 = bbx.getCenterX();
            double lon2 = bbx.getCenterY();

            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);

            lat1 = Math.toRadians(lat1);
            lat2 = Math.toRadians(lat2);

            double a = Math.pow(Math.sin(dLat / 2), 2) +
                    Math.pow(Math.sin(dLon / 2), 2) *
                            Math.cos(lat1) *
                            Math.cos(lat2);
            double c = 2 * Math.asin(Math.sqrt(a));
            double r = 6371;
            return c * r;
        }
        throw new RuntimeException("Not implemented");
    }

    public String ToString(){
        return "BoundingBox{" +
                "xmin=" + xmin +
                ", ymin=" + ymin +
                ", xmax=" + xmax +
                ", ymax=" + ymax +
                '}';
    }
}
