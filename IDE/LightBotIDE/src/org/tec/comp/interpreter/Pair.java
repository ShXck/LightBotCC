package org.tec.comp.interpreter;

public class Pair<X,Y> {

    private X first_element;
    private Y second_element;

    public Pair(X x, Y y) {
        first_element = x;
        second_element = y;
    }

    public X first() {
        return first_element;
    }

    public Y second() {
        return second_element;
    }

    public void set_first(X val) {
        first_element = val;
    }

    public void set_second(Y val) {
        second_element = val;
    }

    public Pair<X,Y> update_values(X xval, Y yval) {
        first_element = xval;
        second_element = yval;
        return this;
    }

    public String toString() {
        return "(" + first_element + "," + second_element + ")";
    }

    public boolean equals(Object obj) {
        if(obj == null) return false;

        if(!Pair.class.isAssignableFrom(obj.getClass())) return false;

        final Pair other = (Pair) obj;
        if((this.first_element == null)? (other.first_element != null): !this.first_element.equals(other.first_element)) return false;
        if((this.second_element == null)? (other.second_element != null): !this.second_element.equals(other.second_element)) return false;
        return true;
    }
}
