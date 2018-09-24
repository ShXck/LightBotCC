package org.tec.comp.game;

import org.tec.comp.utilities.Message_Handler;
import org.tec.comp.interpreter.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Game_Board {

    private final int BOARD_SIZE = 8;
    private Block[][] game_board;

    private Set<String> msg_list;
    private Direction current_direction;
    private boolean built;

    Backtracking bt;

    public Game_Board() {
        game_board = new Block[BOARD_SIZE][BOARD_SIZE];
        msg_list = new LinkedHashSet<>();
        current_direction = Direction.RIGHT;
        set_initial_board();
        built = false;
        bt = new Backtracking(BOARD_SIZE);
    }

    /**
     * Construye la matriz que representa el tablero.
     *
     * @param pos_acts las acciones a realizar.
     */
    public void build_board(ArrayList<Position_Procedure> pos_acts) {
        for (Position_Procedure p : pos_acts) {
            build(p.position(), p.get_action_list());
        }
    }

    /**
     * Método auxiliar de construcción.
     *
     * @param position la posición en la que se realiza la acción.
     * @param act_list las lista de acciones.
     */
    private void build(Pair<Integer, Integer> position, ArrayList<Action> act_list) {

        if (is_valid_pos(position.first(), position.second())) {
            Block current_block = game_board[position.first()][position.second()];

            for (Action a : act_list) {
                switch (a.get_act_type()) {
                    case CHANGE_DIR: {
                        current_direction = ((Position_Act_Data) a).direction();
                        break;
                    }
                    case SET_VAR: {
                        Var_Act_Data var_data = (Var_Act_Data) a;
                        Variable var = var_data.first_var();
                        var.set_value(var_data.get_set_val());
                        break;
                    }
                    case INCR_VAR: {
                        Var_Act_Data var_data = (Var_Act_Data) a;
                        Variable var = var_data.first_var();
                        var.set_value(var.get_val() + 1);
                        break;
                    }
                    case DECR_VAR: {
                        Var_Act_Data var_data = (Var_Act_Data) a;
                        Variable var = var_data.first_var();
                        var.set_value(var.get_val() - 1);
                        break;
                    }
                    case PLACE_BLOCK: {
                        place_block(Block_Type.NORMAL_BLOCK, position, ((Position_Act_Data) a).get_n_param(), 0);
                        break;
                    }
                    case PLACE_HIGH: {
                        place_block(Block_Type.HIGH_BLOCK, position, 1, ((Position_Act_Data) a).get_n_param());
                        break;
                    }
                    case PUT_LIGHT: {
                        try {
                            current_block.set_block(Block_Type.BLUE_LIGHT, current_block.get_height());
                        } catch (NullPointerException npe) {
                            current_block = new Block(Block_Type.BLUE_LIGHT, 0);
                        }
                        break;
                    }
                    case FOR_LOOP: {
                        apply_for_loop(position, ((Position_Act_Data) a).get_loop_data());
                        break;
                    }
                    case POS_START: {
                        Var_Act_Data var_data = (Var_Act_Data) a;
                        Pair<Integer, Integer> coords = new Pair<>(var_data.first_var().get_val(), var_data.second_var().get_val());
                        Block robot_block = game_board[coords.first()][coords.second()];
                        switch (robot_block.get_type()) {
                            case BLANK: {
                                place_block(Block_Type.ROBOT, coords, 1, 0);
                                break;
                            }
                            case HIGH_BLOCK: {
                                place_block(Block_Type.ROBOT, coords, robot_block.get_height(), current_block.get_height());
                                break;
                            }
                            case BLUE_LIGHT: {
                                place_block(Block_Type.ROBOT, coords, robot_block.get_height(), current_block.get_height());
                                break;
                            }
                            case NORMAL_BLOCK: {
                                place_block(Block_Type.ROBOT, coords, robot_block.get_height(), current_block.get_height());
                            }
                        }
                        break;
                    }
                    case CALL_PROC: {
                        ArrayList<Multiple_Action_Container> procs = LangParser.code_procedures;
                        Position_Act_Data act_data = (Position_Act_Data) a;
                        for (Multiple_Action_Container mac : procs) {
                            if (act_data.get_proc_name().equals(mac.get_proc_name())) {
                                build(position, mac.get_loop_actions());
                            }
                        }
                        break;
                    }
                }
            }
        } else {
            msg_list.add(Message_Handler.invalid_position(position.first(), position.second()));
        }
    }

    /**
     * Pone un bloque.
     *
     * @param type     el tipo de bloque.
     * @param init_pos la posición del bloque.
     * @param nparam   parámetro del bloque.
     * @param height   la altura del bloque.
     */
    private void place_block(Block_Type type, Pair<Integer, Integer> init_pos, int nparam, int height) {
        if (nparam != -1) {
            switch (current_direction) {
                case RIGHT: { // [X][+]
                    int aux_pos = init_pos.second();
                    for (int i = 0; i < nparam; i++) {
                        if (is_valid_pos(init_pos.first(), aux_pos)) {
                            game_board[init_pos.first()][aux_pos++].set_block(type, height);
                        } else {
                            msg_list.add(Message_Handler.invalid_position(init_pos.first(), aux_pos));
                            break;
                        }
                    }
                    break;
                }
                case LEFT: { // [X][-]
                    int aux_pos = init_pos.second();
                    for (int i = 0; i < nparam; i++) {
                        if (is_valid_pos(init_pos.first(), aux_pos)) {
                            game_board[init_pos.first()][aux_pos--].set_block(type, height);
                        } else {
                            msg_list.add(Message_Handler.invalid_position(init_pos.first(), aux_pos));
                            break;
                        }
                    }
                    break;
                }
                case UP: { //[-][X]
                    int aux_pos = init_pos.first();
                    for (int i = 0; i < nparam; i++) {
                        if (is_valid_pos(aux_pos, init_pos.second())) {
                            game_board[aux_pos--][init_pos.second()].set_block(type, height);
                        } else {
                            msg_list.add(Message_Handler.invalid_position(init_pos.first(), aux_pos));
                            break;
                        }
                    }
                    break;
                }
                case DOWN: { //[+][X]
                    int aux_pos = init_pos.first();
                    for (int i = 0; i < nparam; i++) {
                        if (is_valid_pos(aux_pos, init_pos.second())) {
                            game_board[aux_pos++][init_pos.second()].set_block(type, height);
                        } else {
                            msg_list.add(Message_Handler.invalid_position(init_pos.first(), aux_pos));
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     * Aplica un for loop.
     *
     * @param pos  la posición de la acción.
     * @param data la lista de acciones del ciclo.
     */
    private void apply_for_loop(Pair<Integer, Integer> pos, Multiple_Action_Container data) {
        ArrayList<Action> loop_actions = data.get_loop_actions();
        Pair<Integer, Integer> aux_pos = pos;

        for (int i = 0; i < data.get_ctr(); i++) {
            build(aux_pos, loop_actions);
            switch (current_direction) {
                case DOWN: {
                    aux_pos = aux_pos.update_values(aux_pos.first() + 1, aux_pos.second());
                    break;
                }
                case UP: {
                    aux_pos = aux_pos.update_values(aux_pos.first() - 1, aux_pos.second());
                    break;
                }
                case RIGHT: {
                    aux_pos = aux_pos.update_values(aux_pos.first(), aux_pos.second() + 1);
                    break;
                }
                case LEFT: {
                    aux_pos = aux_pos.update_values(aux_pos.first(), aux_pos.second() - 1);
                    break;
                }
            }
        }
    }

    /**
     * Construye una matriz vacia.
     */
    public void set_initial_board() {
        for (int i = 0; i < game_board.length; i++) {
            for (int j = 0; j < game_board[i].length; j++) {
                game_board[i][j] = new Block(Block_Type.BLANK, 0);
            }
        }
    }

    /**
     * Verifica si es una posición valida dentro de la matriz.
     *
     * @param x la posición en x.
     * @param y la posición en y.
     * @return si es válida o no.
     */
    private boolean is_valid_pos(int x, int y) {
        return (x >= 0 && y >= 0 && x < BOARD_SIZE && y < BOARD_SIZE);
    }

    /**
     * Imprime la matriz.
     */
    public void print_board() {
        for (int i = 0; i < game_board.length; i++) {
            for (int j = 0; j < game_board[0].length; j++) {
                System.out.print(game_board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * @return la matriz del juego.
     */
    public Block[][] get_board() {
        return game_board;
    }

    /**
     * @return Los mensajes de errores en la construcción.
     */
    public Set<String> get_msg_list() {
        return msg_list;
    }

    /**
     * @return si está construida o no.
     */
    public boolean is_built() {
        return built;
    }

    /**
     * Cambia el estado de construcción.
     * @param state el nuevo estado.
     */
    public void set_built_state(boolean state) {
        built = state;
    }

    /**
     * Limpia la matriz.
     */
    public void clean_board() {
        game_board = new Block[BOARD_SIZE][BOARD_SIZE];
        set_initial_board();
        msg_list.clear();
        built = false;
        current_direction = Direction.RIGHT;
    }

    /**
     * Verifica que el mapa tenga solución.
     */
    public void check_solution() {
        bt.solveMaze(game_board, 0,1, BOARD_SIZE, 1,1);
    }
}
