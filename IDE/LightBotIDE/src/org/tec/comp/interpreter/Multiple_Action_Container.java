package org.tec.comp.interpreter;

import org.tec.comp.game.Direction;

import java.util.ArrayList;

/**
 * Class that manages the FOR, WHEN, KEEP and CALL_PROC loop.
 */
public class Multiple_Action_Container {

    //LOOP DATA
    private int it_ctr; // the count of iterations.
    private String counter_id; // the name of the counter in the loop.

    //CALL_PROC DATA
    private String proc_name;
    private ArrayList<Action> action_list;

    public Multiple_Action_Container(String ctr_id, int ctr) {
        it_ctr = ctr;
        counter_id = ctr_id;
        action_list = new ArrayList<>();
    }

    public Multiple_Action_Container() {
        it_ctr = -1;
        counter_id = "";
        action_list = new ArrayList<>();
    }

    public Multiple_Action_Container(String proc) {
        proc_name = proc;
        action_list = new ArrayList<>();
    }

    /**
     * Función que maneja una acción relacionada a posicionar un bloque.
     * @param act el tipo de acción.
     * @param
     */
    public void add_action_to_container(Action_Type act, int nparam) {
        action_list.add(new Position_Act_Data(act, nparam));
    }

    /**
     * Function that manages addition of variable dependant actions.
     * @param act action type.
     * @param v1 first variable.
     * @param v2 second variable (pass it as null if the action works only on one variable).
     * @param nparam value of variable. (just in case of action SET otherwise should be -1).
     */
    public void add_action_to_container(Action_Type act, Variable v1, Variable v2, int nparam) {
        action_list.add(new Var_Act_Data(act, v1, v2, nparam));
    }

    public void add_action_to_container(Action_Type act, String proc) {
        action_list.add(new Position_Act_Data(act, proc));
    }

    public void add_action_to_container(Action_Type act, Multiple_Action_Container container) {
        action_list.add(new Position_Act_Data(act, container));
    }

    public void add_action_to_container(Action_Type act, Direction direction) {
        action_list.add(new Position_Act_Data(act, direction));
    }

    public int get_ctr() {
        return it_ctr;
    }

    public String get_ctr_id() {
        return counter_id;
    }

    public ArrayList<Action> get_loop_actions() {
        return action_list;
    }

    public void set_it_ctr(int ctr) {
        it_ctr = ctr;
    }

    public void set_counter_id(String id) {
        counter_id = id;
    }

    public boolean is_empty() {
        return (it_ctr == -1 && counter_id.isEmpty() && action_list.isEmpty());
    }

    public String get_proc_name() {
        return proc_name;
    }

    @Override
    public String toString() {
        return action_list.toString();
    }
}
