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
     * @param el parámetro del bloque (-1 si no tiene).
     */
    public void add_action(Action_Type act, int nparam) {
        action_list.add(new Position_Act_Data(act, nparam));
    }

    /**
     * Maneja acciones relacionadas con variables.
     * @param act tipo de acción.
     * @param v1 primera variable.
     * @param v2 segunda variable (nula si la acción solo depende de una).
     * @param nparam valor n de variable (-1 si no tiene).
     */
    public void add_action(Action_Type act, Variable v1, Variable v2, int nparam) {
        action_list.add(new Var_Act_Data(act, v1, v2, nparam));
    }

    /**
     * Maneja acciones relacionadas con procedimientos.
     * @param act el tipo de acción.
     * @param proc el nombre del procedimiento.
     */
    public void add_action(Action_Type act, String proc) {
        action_list.add(new Position_Act_Data(act, proc));
    }

    /**
     * Maneja Acciones relaciodas con direcciones.
     * @param act el tipo de acción.
     * @param dir la dirección.
     */
    public void add_action(Action_Type act, Direction dir) {
        action_list.add(new Position_Act_Data(act, dir));
    }

    /**
     * Fija la posición de un procedimiento.
     * @param xpos la posición en x.
     * @param ypos la posición en y.
     */
    public void set_proc_pos(int xpos, int ypos) {
        proc_position = new Pair<>(xpos, ypos);
    }

    /**
     * Fija el valor del id.
     * @param id la id.
     */
    public void set_id(int id) {
        proc_id = id;
    }

    /**
     *
     * @return el valor de la id del procediemiento.
     */
    public int id() { return proc_id; }

    /**
     *
     * @return lq posición del procedimiento.
     */
    public Pair<Integer, Integer> position() { return proc_position; }

    /**
     *
     * @return la lista de acciones.
     */
    public ArrayList<Action> get_action_list() {
        return action_list;
    }

    /**
     *
     * @return si el procedimiento está vacío.
     */
    public boolean is_empty() {
        return (proc_id == -1 && proc_position == null && action_list.size() == 0);
    }

    public String toString() {
        return String.valueOf(proc_id) + ":" + action_list.toString();
    }
}
