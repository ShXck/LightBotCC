package org.tec.comp.interpreter;

public class Variable {

    private int var_value;
    private String var_id;
    private boolean global;

    public Variable(String id, int val, boolean is_global) {
        var_value = val;
        var_id = id;
        global = is_global;
    }

    public Variable(String id, boolean is_global) {
        var_id = id;
        var_value = -1;
        global = is_global;
    }

    /**
     * Asigna un valor a la variable.
     * @param val el valor de la variable.
     */
    public void set_value(int val) {
        var_value = val;
    }

    /**
     * @return si la variables está asignada o no.
     */
    public boolean is_assigned() {
        return var_value != -1;
    }

    /**
     * Se incrementa en uno el valor de la variable.
     */
    public void increment() {
        var_value++;
    }

    /**
     * Se decrementa en uno el valor de la variable.
     */
    public void decrement() {
        var_value--;
    }

    /**
     * @return Obtiene el nombre de la variable.
     */
    public String get_id() {
        return var_id;
    }

    /**
     * @return el valro numérico de la variable.
     */
    public int get_val() {
        return var_value;
    }

    /**
     * @return Si es global o no.
     */
    public boolean is_global() { return global; }

    @Override
    public String toString() {
        return var_id + ":" + String.valueOf(var_value);
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
