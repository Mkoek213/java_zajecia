package calc;

public class Inv extends Node{
    //calculates the inverse of a number multplied by arg
    Node arg;

    Inv(Node n){
        arg = n;
    }


    @Override
    double evaluate() {
        double argVal = arg.evaluate();
        double nominator = 1;
        return nominator/ (argVal);
    }

    int getArgumentsCount(){return 1;}

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if(sign<0)b.append("-");
        b.append("1/");
        b.append(arg);
        return b.toString();
    }

    Node diff(Variable var) {
        Prod r = new Prod(sign,new Inv(arg));
        r.mul(arg.diff(var));
        return r;
    }

    @Override
    boolean isZero() {
        return false;
    }
}
