package calc;

public class Power extends Node {
    /** calc.Power jest jedyną zaimplementowaną funkcją. Zauważmy, że calc.Power(x,−1)=1x
     . Argumentem calc.Power nie musi być pojedyncza zmienna. Może to być np.: calc.Power(calc.Sum(x,2),-2), czyli 1(x+2)2


     W kodzie pokazano przykładową implementację toString() z próbą wstawienia nawiasów w takich przypadkach, jak −(−x5)lub(x+1)2

     */
    double p;
    Node arg;
    Power(Node n,double p){
        arg = n;
        this.p = p;
    }

    @Override
    double evaluate() {
        double argVal = arg.evaluate();
        return Math.pow(argVal,p);
    }




    int getArgumentsCount(){return 1;}


    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if(sign<0)b.append("-");
        int argSign = arg.getSign();
        int cnt = arg.getArgumentsCount();
        boolean useBracket = false;
        if(argSign<0 ||cnt>1)useBracket = true;
        String argString = arg.toString();
        if(useBracket)b.append("(");
        b.append(argString);
        if(useBracket)b.append(")");
        b.append("^");
        b.append(p);
        return b.toString();
    }

    Node diff(Variable var) {
        Prod r =  new Prod(sign*p,new Power(arg,p-1));
        r.mul(arg.diff(var));
        return r;
    }

    @Override
    boolean isZero() {
        return arg.isZero();
    }

}