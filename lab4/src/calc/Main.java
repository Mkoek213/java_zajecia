package calc;

import java.util.Locale;

public class Main {

    static void buildAndPrint(){
        /** Oczekiwany wynik: 2.1*x^3 + x^2 + (-2)*x + 7 */
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(2.1,new Power(x,3))
                .add(new Power(x,2))
                .add(-2,x)
                .add(new Inv(new Power(x, 2)))
//                .add(new Prod(3.0).mul(7.0).mul(x).mul(11))
                .add(new Exp(3))
                .add(7);
        System.out.println(exp.toString());
    }


    static void buildAndEvaluate() {
        /** Oczekiwany wynik: f(x)=x^3 + (-2)*x^2 + (-x) + 2 */
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(new Power(x, 3))
//                .add(new Exp(3))
                .add(-2, new Power(x, 2))
                .add(-1, x)
                .add(new Inv(new Power(x, 2)))
                .add(2);
        for (double v = -5; v < 5; v += 0.1) {
            x.setValue(v);
            System.out.printf(Locale.US, "f(%f)=%f\n", v, exp.evaluate());
        }
    }

    static void defineCircle(){
        /** Znajdź i wypisz 100 punktów leżących wewnątrz okręgu…. Oczywiście napisz kod, który je znajduje ;-) */
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Node circle = new Sum()
                .add(new Power(x,2))
                .add(new Power(y,2))
                .add(8,x)
                .add(4,y)
                .add(16);
        System.out.println(circle.toString());

        double xv = 100*(Math.random()-.5);
        double yv = 100*(Math.random()-.5);
        x.setValue(xv);
        y.setValue(yv);
        double fv = circle.evaluate();
        System.out.print(String.format("Punkt (%f,%f) leży %s koła %s",xv,yv,(fv<0?"wewnątrz":"na zewnątrz"),circle.toString()));
    }


    static void diffPoly() {
        /** oczekiwany wynik: exp=2*x^3 + x^2 + (-2)*x + 7
         d(exp)/dx=0*x^3 + 2*3*x^2*1 + 2*x^1*1 + 0*x + (-2)*1 + 0 */
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(2,new Power(x,3))
                .add(new Power(x,2))
                .add(new Inv(new Power(x, 2)))
                .add(new Log(2, x))
                .add(-2,x)
                .add(7);
        System.out.print("exp=");
        System.out.println(exp.toString());

        Node d = exp.diff(x);
        System.out.print("d(exp)/dx=");
        System.out.println(d.toString());

    }


    static void diffCircle() {
        /** oczekiwany wynik f(x,y)=x^2 + y^2 + 8*x + 4*y + 16
         d f(x,y)/dx=2*x^1*1 + 2*y^1*0 + 0*x + 8*1 + 0*y + 4*0 + 0
         d f(x,y)/dy=2*x^1*0 + 2*y^1*1 + 0*x + 8*0 + 0*y + 4*1 + 0 */
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Node circle = new Sum()
                .add(new Power(x,2))
                .add(new Power(y,2))
                .add(8,x)
                .add(4,y)
                .add(16);
        System.out.print("f(x,y)=");
        System.out.println(circle.toString());

        Node dx = circle.diff(x);
        System.out.print("d f(x,y)/dx=");
        System.out.println(dx.toString());
        System.out.print("d f(x,y)/dy=");
        Node dy = circle.diff(y);
        System.out.println(dy.toString());

    }

    public static void main(String[] args) {
        System.out.println("Test1");
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>\n");
        buildAndPrint();
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>\n");
        System.out.println("Test2");
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>\n");
        buildAndEvaluate();
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>\n");
        System.out.println("Test3");
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>\n");
        defineCircle();
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>\n");
        System.out.println("Test4");
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>\n");
        diffPoly();
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>\n");
        System.out.println("Test5");
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>\n");
        diffCircle();
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>\n");
    }



}
