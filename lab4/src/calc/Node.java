package calc;

abstract public class Node {
    /** W klasie calc.Node wprowadzono atrybut sign znak, dziedziczony przez klasy potomne. Potraktujmy to następująco. Załóżmy, że reprezentacją tekstową węzła jest abcd

     jeśli sign<0 reprezentacją tekstową będzie -(abcd) [w nawiasach]
     jeśli sign>=0 będzie to po prostu abcd [bez nawiasów]
 */
     int sign=1;
    Node minus(){
        sign = -1;
        return this;
    }
    Node plus(){
        sign = 1;
        return this;
    }
    int getSign(){return sign;}

    /**
     * Oblicza wartość wyrażenia dla danych wartości zmiennych
     * występujących w wyrażeniu
     */
    abstract double evaluate();

    /**
     *
     * zwraca tekstową reprezentację wyrażenia
     */
    public String toString(){return "";}

    /**
     *
     * Zwraca liczbę argumentów węzła
     */
    int getArgumentsCount(){return 0;}

    abstract Node diff(Variable var);

    abstract boolean isZero();





}