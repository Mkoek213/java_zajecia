package tree_src;

public class Star implements XmasShape {
    int x;
    int y;
    double scale;

    public Star(){}

    public Star(int x, int y, double scale){
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    @Override
    public void render(java.awt.Graphics2D g2d) {
        g2d.setColor(new java.awt.Color(255, 255, 0));
        int[] xPoints = {0, 10, 40, 15, 25, 0, -25, -15, -40, -10};
        int[] yPoints = {-40, -10, -10, 10, 40, 20, 40, 10, -10, -10};
        g2d.fillPolygon(xPoints, yPoints, 10);
        g2d.setColor(new java.awt.Color(255, 255, 0));
        g2d.drawPolygon(xPoints, yPoints, 10);
    }

    @Override
    public void transform(java.awt.Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }

}
