package org.tec.comp.game;

import org.tec.comp.Message_Handler;
import org.tec.comp.interpreter.*;

import java.util.ArrayList;

public class Game_Board {

    private final int BOARD_SIZE = 9;
    private Block[][] game_board;

    private ArrayList<String> msg_list;
    private Direction current_direction;

    public Game_Board() {
        game_board = new Block[BOARD_SIZE][BOARD_SIZE];
        msg_list = new ArrayList<>();
        current_direction = Direction.RIGHT;
        set_initial_board();
    }

    public void build_board(ArrayList<Position_Procedure> pos_acts) {
        for(Position_Procedure p : pos_acts) {
            build(p.position(), p.get_action_list());
        }
    }

    private void build(Pair<Integer, Integer> position, ArrayList<Action> act_list) {
        Block current_block = game_board[position.first()][position.second()];

        for (Action a : act_list) {
            switch (a.get_act_type()) {
                case CHANGE_DIR:{
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
                    try{
                        current_block.set_block(Block_Type.BLUE_LIGHT, current_block.get_height());
                    } catch (NullPointerException npe) {
                        current_block = new Block(Block_Type.BLUE_LIGHT, 0);
                    }
                    break;
                }
                case FOR_LOOP: {
                    apply_for_loop(position,  ((Position_Act_Data) a).get_loop_data());
                    break;
                }
                case POS_START: {
                    Var_Act_Data var_data = (Var_Act_Data) a;
                    Pair<Integer, Integer> coords = new Pair<>(var_data.first_var().get_val(), var_data.second_var().get_val());
                    Block robot_block = game_board[coords.first()][coords.second()];
                    switch (robot_block.get_type()) {
                        case BLANK: {
                            place_block(Block_Type.ROBOT, coords, 1, 0);
                        }
                        case HIGH_BLOCK: {
                            place_block(Block_Type.ROBOT, coords, robot_block.get_height(), 0);
                        }
                    }
                }
            }
        }
    }

    private void place_block(Block_Type type, Pair<Integer, Integer> init_pos, int nparam, int height) {
        if(nparam != -1) {
            switch (current_direction) {
                case RIGHT: { // [X][+]
                    int aux_pos = init_pos.second();
                    for(int i = 0; i < nparam; i++) {
                        if (is_valid_pos(init_pos.first(), aux_pos)) {
                            game_board[init_pos.first()][aux_pos++].set_block(type, height);
                        } else {
                            msg_list.add(Message_Handler.invalid_position(init_pos.first(), aux_pos));
                        }
                    }
                    break;
                }
                case LEFT:{ // [X][-]
                    int aux_pos = init_pos.second();
                    for(int i = 0; i < nparam; i++) {
                        if (is_valid_pos(init_pos.first(), aux_pos)) {
                            game_board[init_pos.first()][aux_pos--].set_block(type, height);
                        } else {
                            msg_list.add(Message_Handler.invalid_position(init_pos.first(), aux_pos));
                        }
                    }
                    break;
                }
                case UP:{ //[-][X]
                    int aux_pos = init_pos.first();
                    for(int i = 0; i < nparam; i++) {
                        if (is_valid_pos(aux_pos, init_pos.second())) {
                            game_board[aux_pos--][init_pos.second()].set_block(type, height);
                        } else {
                            msg_list.add(Message_Handler.invalid_position(init_pos.first(), aux_pos));
                        }
                    }
                    break;
                }
                case DOWN: { //[+][X]
                    int aux_pos = init_pos.first();
                    for(int i = 0; i < nparam; i++) {
                        if (is_valid_pos(aux_pos, init_pos.second())) {
                            game_board[aux_pos++][init_pos.second()].set_block(type, height);
                        } else {
                            msg_list.add(Message_Handler.invalid_position(init_pos.first(), aux_pos));
                        }
                    }
                    break;
                }
            }
        }
    }

    private void apply_for_loop(Pair<Integer, Integer> pos, Multiple_Action_Container data) {
        ArrayList<Action> loop_actions = data.get_loop_actions();
        Pair<Integer, Integer> aux_pos = pos;
        for(int i = 0; i < data.get_ctr(); i++) {
            build(pos, loop_actions);
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

    public void set_initial_board() {
        for(int i = 0; i < game_board.length; i++) {
            for(int j = 0; j < game_board[i].length; j++) {
                game_board[i][j] = new Block(Block_Type.BLANK, 0);
            }
        }
    }

    private boolean is_valid_pos(int x, int y) {
        return (x >= 0 && y >= 0 && x < BOARD_SIZE && y < BOARD_SIZE);
    }

    public void print_board() {
        for(int i = 0; i < game_board.length; i++) {
            for(int j = 0; j < game_board[0].length; j++) {
                System.out.print(game_board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public Block[][] get_board() {
        return game_board;
    }
}
