package calc;

public class Log extends Node{
    double base;
    Node l_log;
    Log(double base, Node l_log){
        this.base = base;
        this.l_log = l_log;
    }

    @Override
    double evaluate() {
        return Math.log(l_log.evaluate()) / Math.log(base);
    }

    int getArgumentsCount(){return 1;}


    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if(sign<0)b.append("-");
        b.append("log");
        b.append("(");
        b.append(base);
        b.append(",");
        b.append(l_log);
        b.append(")");
        return b.toString();
    }

    Node diff(Variable var) {
        Prod r = new Prod(new Inv(l_log), new Inv(new Log(Math.E, new Constant(base))));
        return r.mul(-1);
    }

    @Override
    boolean isZero() {
        return l_log.evaluate() == 1;
    }
}
