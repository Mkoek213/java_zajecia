package calc;

import java.util.ArrayList;
import java.util.List;

public class Sum extends Node {
    /** Klasa calc.Sum jest wyrażeniem reprezentującą sumę argumentów. Zawiera listę węzłów List<calc.Node> args = new ArrayList<>();

     Teoretycznie, lista argumentów może liczyć 0,1,2,… elementów. Metoda add() dodaje składniki do listy */

    List<Node> args = new ArrayList<>();

    Sum(){}

    Sum(Node n1, Node n2){
        args.add(n1);
        args.add(n2);
    }


    Sum add(Node n){
        args.add(n);
        return this;
    }

    Sum add(double c){
        args.add(new Constant(c));
        return this;
    }

    Sum add(double c, Node n) {
        Node mul = new Prod(c,n);
        args.add(mul);
        return this;
    }

    @Override
    double evaluate() {
        double result =0;
//        oblicz sumę wartości zwróconych przez wywołanie evaluate skłądników sumy
        for (int i = 0; i < args.size(); i++) {
            result += args.get(i).evaluate();
        }
        return sign*result;
    }

    int getArgumentsCount(){return args.size();}

    public String toString(){
        StringBuilder b =  new StringBuilder();
        if(sign<0)b.append("-(");
        int idx = 0;
        if (args.isEmpty()){
            b.append(new Constant(0));
        }
        if (args.size() == 1){
            b.append(args.getFirst());
        }
        for (Node arg: args) {
            int argSign = arg.getSign();
            if (argSign < 0)b.append(" - "); else if(idx !=0)b.append(" + ");
            b.append(arg);
            idx++;
        }

        if(sign<0)b.append(")");
        return b.toString();
    }

    Node diffVanilla(Variable var) {
        Sum r = new Sum();
        for(Node n:args){
            r.add(n.diff(var));
        }
        return r;
    }

    @Override
    Node diff(Variable var) {
        return diffVanilla(var);
    }

    @Override
    boolean isZero() {
        for(Node n:args){
            if(!n.isZero())return false;
        }
        return true;
    }


}