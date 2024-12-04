package csvreader;

public class BoundingBox {

    double xmin;
    double ymin;
    double xmax;
    double ymax;


    void addPoint(double x, double y){

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

        if (x >= xmin && x <= xmax && y >= ymin && y <= ymax){
            return true;
        }
        return false;
    }

    boolean contains(BoundingBox bb){

        if (xmin <= bb.xmin && xmax >= bb.xmax && ymin <= bb.ymin && ymax >= bb.ymax){
            return true;
        }
        return false;
    }

    boolean intersects(BoundingBox bb){

        if (xmin < bb.xmax && xmax > bb.xmin && ymin < bb.ymax && ymax > bb.ymin) {
            return true;
        }
        return false;
    }

    BoundingBox add(BoundingBox bb){

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

        if (xmin == 0 && ymin == 0 && xmax == 0 && ymax == 0){
            return true;
        }
        return false;
    }

    boolean equals_(Object o) {

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

        if (!isEmpty()){
            return (xmin + xmax) / 2;
        }
        throw new RuntimeException("Not implemented");
    }

    double getCenterY(){

        if (!isEmpty()){
            return (ymin + ymax) / 2;
        }
        throw new RuntimeException("Not implemented");
    }

    double distanceTo(BoundingBox bbx){

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
