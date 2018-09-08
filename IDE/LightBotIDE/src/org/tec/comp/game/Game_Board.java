package org.tec.comp.game;

import org.tec.comp.interpreter.Pair;

import java.util.Random;

public class Game_Board {

    private final int BOARD_SIZE = 9;
    private Block[][] game_board;

    public Game_Board() {
        game_board = new Block[BOARD_SIZE][BOARD_SIZE];
        set_initial_board();
        //build_random_board();
    }

    public void set_robot_initial_pos(Pair<Integer, Integer> pos) {
        if(is_valid_pos(pos)) game_board[pos.first()][pos.second()] = new Block(Block_Type.ROBOT, 0);
    }

    public void add_block(Pair<Integer, Integer> pos, Block_Type type, int bheight) {
        if(is_valid_pos(pos)) {
            game_board[pos.first()][pos.second()] = new Block(type,bheight);
        }
    }

    public void build_random_board() {
        Block_Type blocks[] = {Block_Type.NORMAL_BLOCK, Block_Type.HIGH_BLOCK};
        Random rd = new Random();
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                if(game_board[i][j] == null) {
                    Block_Type rd_type = blocks[rd.nextInt(blocks.length)];
                    if(rd_type == Block_Type.HIGH_BLOCK) game_board[i][j] = new Block(rd_type, rd.nextInt(3) + 1);
                    else game_board[i][j] = new Block(rd_type, 0);
                }
            }
        }
        game_board[1][1].set_block(Block_Type.ROBOT, 0);
        game_board[7][7].set_block(Block_Type.BLUE_LIGHT, 0);
    }

    private boolean is_valid_pos(Pair<Integer, Integer> pos) {
        if(pos.first() >= BOARD_SIZE || pos.second() >= BOARD_SIZE) return false;
        if(pos.first() < 0 || pos.second() < 0) return false;
        return true;
    }

    private void set_initial_board() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            game_board[0][i] = new Block(Block_Type.WALL, 0);
            game_board[BOARD_SIZE - 1][i] = new Block(Block_Type.WALL,0);
            game_board[i][0] = new Block(Block_Type.WALL,0);
            game_board[i][BOARD_SIZE - 1] = new Block(Block_Type.WALL,0);
        }
    }

    public void print_board() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                if(game_board[i][j] == null) System.out.print("0" + " ");
                else System.out.print(game_board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public Block[][] get_board() {  //TODO: Remove
        return game_board;
    }
}
