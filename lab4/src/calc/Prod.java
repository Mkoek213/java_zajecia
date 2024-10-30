package calc;

import java.util.ArrayList;
import java.util.List;


public class Prod extends Node {
    /** calc.Prod to iloczyn (ang. product). Reprezentuje wyrażenie będące iloczynem czynników. Metoda mul() dodaje czynnik do listy. */
    List<Node> args = new ArrayList<>();

    Prod(){}

    Prod(Node n1){
        args.add(n1);
        simplify();
    }
    Prod(double c){
//        wywołaj konstruktor jednoargumentowy przekazując new calc.Constant(c)
        this(new Constant(c));
    }

    void simplify(){
        double constProd = 1.0;
        List<Node> newArgs = new ArrayList<>();
        for (Node arg: args){
            if (arg instanceof Constant){
                constProd *= arg.evaluate();
            } else if (arg instanceof Prod){
                ((Prod)arg).simplify();
                newArgs.addAll(((Prod)arg).args);
            }else{
                newArgs.add(arg);
            }
        }

        if (constProd != 1.0){
            newArgs.add(0, new Constant(constProd));
        }
        args = newArgs;
    }

    Prod(Node n1, Node n2){
        args.add(n1);
        args.add(n2);
        simplify();
    }
    Prod(double c, Node n){
//        wywołaj konstruktor dwuargumentowy
        this(new Constant(c),n);
    }



    Prod mul(Node n){
        args.add(n);
        simplify();
        return this;
    }

    Prod mul(double c){
        args.add(new Constant(c));
        simplify();
        return this;
    }


    @Override
    double evaluate() {
        double result =1;
        // oblicz iloczyn czynników wołąjąc ich metodę evaluate
        for (int i = 0; i < args.size(); i++) {
            result *= args.get(i).evaluate();
        }
        return sign*result;
    }
    int getArgumentsCount(){return args.size();}


    public String toString(){
        StringBuilder b =  new StringBuilder();
        if(sign<0)b.append("-");
//        zaimplementuj
        int idx = 0;
        for (Node arg: args) {
            int argSign = arg.getSign();
            if (argSign < 0)b.append("("); else if(idx != 0)b.append("*");
            b.append(arg);
            if (argSign < 0)b.append(")");
            idx++;
        }
        return b.toString();
    }

    Node diffVanilla(Variable var) {
        Sum r = new Sum();
        for(int i=0;i<args.size();i++){
            Prod m= new Prod();
            for(int j=0;j<args.size();j++){
                Node f = args.get(j);
                if(j==i)m.mul(f.diff(var));
                else m.mul(f);
            }
            r.add(m);
        }
        return r;
    }

    @Override
    Node diff(Variable var){
        return diffVanilla(var);
    }

    @Override
    boolean isZero() {
        for (Node arg: args) {
            if(!arg.isZero())return false;
        }
        return true;
    }

}