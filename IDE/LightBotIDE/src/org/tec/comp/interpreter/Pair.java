package org.tec.comp.interpreter;

public class Pair<X,Y> {

    private X first;
    private Y second;

    public Pair(X x, Y y) {
        first = x;
        second = y;
    }

    public X get_first() {
        return first;
    }

    public Y get_second() {
        return second;
    }

    public String toString() {
        return "(" + first + "," + second + ")";
    }

    public boolean equals(Object obj) {
        if(obj == null) return false;

        if(!Pair.class.isAssignableFrom(obj.getClass())) return false;

        final Pair other = (Pair) obj;
        if((this.first == null)? (other.first != null): !this.first.equals(other.first)) return false;
        if((this.second == null)? (other.second != null): !this.second.equals(other.second)) return false;
        return true;
    }
}
