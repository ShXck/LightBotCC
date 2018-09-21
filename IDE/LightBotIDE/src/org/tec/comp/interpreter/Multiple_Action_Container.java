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
     * @param nparam el parámetro del bloque.
     */
    public void add_action_to_container(Action_Type act, int nparam) {
        action_list.add(new Position_Act_Data(act, nparam));
    }

    /**
     * Función que maneja acciones relacionadas a variables.
     * @param act el tipo de acción.
     * @param v1 la primera variable.
     * @param v2 segunda variable (nula si la acción solo depende de una).
     * @param nparam valor n del bloque (-1 si no tiene).
     */
    public void add_action_to_container(Action_Type act, Variable v1, Variable v2, int nparam) {
        action_list.add(new Var_Act_Data(act, v1, v2, nparam));
    }

    /**
     * Maneja una acción relacionada a un procedimiento.
     * @param act el tipo de acción.
     * @param proc el nombre del procedimeinto.
     */
    public void add_action_to_container(Action_Type act, String proc) {
        action_list.add(new Position_Act_Data(act, proc));
    }

    /**
     * Maneja comandos con conjunto de acciones.
     * @param act el tipo de comando.
     * @param container la lista de acciones.
     */
    public void add_action_to_container(Action_Type act, Multiple_Action_Container container) {
        action_list.add(new Position_Act_Data(act, container));
    }

    /**
     * Maneja acciones relacionada con direcciones.
     * @param act el tipo de acción.
     * @param direction la dirección.
     */
    public void add_action_to_container(Action_Type act, Direction direction) {
        action_list.add(new Position_Act_Data(act, direction));
    }

    /**
     *
     * @return el contador de iteración.
     */
    public int get_ctr() {
        return it_ctr;
    }

    /**
     *
     * @return el nombre del contador.
     */
    public String get_ctr_id() {
        return counter_id;
    }

    /**
     *
     * @return la lista de acciones del ciclo.
     */
    public ArrayList<Action> get_loop_actions() {
        return action_list;
    }

    /**
     * Asigna un valor al contador de iteraciones.
     * @param ctr el contador.
     */
    public void set_it_ctr(int ctr) {
        it_ctr = ctr;
    }

    /**
     * Asigna el nombre al contador.
     * @param id el nuevo nombre.
     */
    public void set_counter_id(String id) {
        counter_id = id;
    }

    /**
     * @return Si el contenedor está vacío.
     */
    public boolean is_empty() {
        return (it_ctr == -1 && counter_id.isEmpty() && action_list.isEmpty());
    }

    /**
     * @return el nombre del procedimiento asignado.
     */
    public String get_proc_name() {
        return proc_name;
    }

    @Override
    public String toString() {
        return action_list.toString();
    }
}
