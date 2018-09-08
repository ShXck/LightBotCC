package org.tec.comp.interpreter;

public class Variable {

    private int var_value;
    private String var_id;

    public Variable(String id, int val) {
        var_value = val;
        var_id = id;
    }

    public Variable(String id) {
        var_id = id;
        var_value = -1;
    }

    public void set_value(int val) {
        var_value = val;
    }

    public boolean is_assigned() {
        return var_value != -1;
    }

    public void increment() {
        var_value++;
    }

    public void decrement() {
        var_value--;
    }

    public String get_id() {
        return var_id;
    }

    public int get_val() {
        return var_value;
    }

    @Override
    public String toString() {
        return "(" + var_id + "," + String.valueOf(var_value) + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;

        if(!Variable.class.isAssignableFrom(obj.getClass())) return false;

        final Variable other = (Variable) obj;
        if(!other.get_id().equals(this.var_id)) return false;
        if(other.get_val() != this.var_value) return false;

        return true;
    }
}
