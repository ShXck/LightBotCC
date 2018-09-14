package org.tec.comp.interpreter;

/**
 * Manages actions that change variables.
 */
public class Var_Act_Data extends Action {

    private Variable var1;
    private Variable var2;
    private int set_val; // In case of set action this is the new value of the variable.


    public Var_Act_Data(Action_Type type, Variable v1, Variable v2, int set_value) {
        super(type);
        var1 = v1;
        var2 = v2;
        set_val = set_value;
    }

    public Variable first_var() {
        return var1;
    }

    public Variable second_var() {
        return var2;
    }

    public int get_set_val() {return set_val;}

    public String toString() {
        return super.get_act_type().toString();
    }
}
