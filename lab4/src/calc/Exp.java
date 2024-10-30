package calc;

public class Exp extends Node{
    double p;
    Exp(double p){
        this.p = p;
    }

    @Override
    double evaluate() {
        double e = Math.E;
        return Math.pow(e,p);
    }

    int getArgumentsCount(){return 1;}


    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if(sign<0)b.append("-");
        b.append("e");
        b.append("^");
        b.append(p);
        return b.toString();
    }

    Node diff(Variable var) {
        return new Prod(sign*p,new Exp(p));
    }

    @Override
    boolean isZero() {
        return false;
    }
}
