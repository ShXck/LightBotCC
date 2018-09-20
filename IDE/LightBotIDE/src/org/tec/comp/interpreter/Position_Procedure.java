package org.tec.comp.interpreter;

import org.tec.comp.game.Direction;

import java.util.ArrayList;

public class Position_Procedure {

    private int proc_id = -1; // action sequence number 0,1,2,3,4,5,...,n.
    private Pair<Integer, Integer> proc_position = null; // position in which the action was declared.
    private ArrayList<Action> action_list;

    public Position_Procedure(int id, int xpos, int ypos) {
        proc_id = id;
        proc_position = new Pair<>(xpos, ypos);
        action_list = new ArrayList<>();
    }

    public Position_Procedure() {
        action_list = new ArrayList<>();
    }

    /**
     * Función que maneja una acción relacionada a un bucle (FOW,WHEN,KEEP).
     * @param act el tipo de bucle.
     * @param loop_data la información del bucle.
     */
    public void add_action(Action_Type act, Multiple_Action_Container loop_data) {
        action_list.add(new Position_Act_Data(act, loop_data));
    }

    /**
     * Función que maneja una acción relacionada a posicionar un bloque.
     * @param act el tipo de acción.
     * @param
     */
    public void add_action(Action_Type act, int nparam) {
        action_list.add(new Position_Act_Data(act, nparam));
    }

    /**
     * Function that manages addition of variable dependant actions.
     * @param act action type.
     * @param v1 first variable.
     * @param v2 second variable (pass it as null if the action works only on one variable).
     * @param nparam value of variable. (just in case of action SET otherwise should be -1).
     */
    public void add_action(Action_Type act, Variable v1, Variable v2, int nparam) {
        action_list.add(new Var_Act_Data(act, v1, v2, nparam));
    }

    public void add_action(Action_Type act, String proc) {
        action_list.add(new Position_Act_Data(act, proc));
    }

    public void add_action(Action_Type act, Direction dir) {
        action_list.add(new Position_Act_Data(act, dir));
    }

    public void set_proc_pos(int xpos, int ypos) {
        proc_position = new Pair<>(xpos, ypos);
    }

    public void set_id(int id) {
        proc_id = id;
    }

    public int id() { return proc_id; }

    public Pair<Integer, Integer> position() { return proc_position; }

    public ArrayList<Action> get_action_list() {
        return action_list;
    }

    public boolean is_empty() {
        return (proc_id == -1 && proc_position == null && action_list.size() == 0);
    }

    public String toString() {
        return String.valueOf(proc_id) + ":" + action_list.toString();
    }
}
