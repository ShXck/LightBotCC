package org.tec.comp.game;
import org.tec.comp.interpreter.Pair;

public class Pathfinder { // EN PROGRESO //

    private final int BOARD_SIZE = 9;
    private int current_height;
    private Block[][] solution;

    public Pathfinder() {
        solution = new Block[BOARD_SIZE][BOARD_SIZE];
        current_height = 0;
    }

    public boolean find_path(Block[][] board, Pair<Integer, Integer> init, Pair<Integer, Integer> dest) {
        build_empty_board(board);

        if(solution_exists(board, init, dest)) {
            print_board();
            return true;
        }
        return false;
    }

    private boolean solution_exists(Block[][] game_board, Pair<Integer, Integer> current_pos, Pair<Integer, Integer> dest) {
        if(current_pos.equals(dest)) { // verifica si se ha llegado al destino.
            System.out.println("FOUND!");
            solution[current_pos.first()][current_pos.second()].set_block(Block_Type.ROBOT, 0);
            return true;
        }

        if(is_valid_move(game_board, current_pos)) {
            solution[current_pos.first()][current_pos.second()].set_block(Block_Type.ROBOT, 0);

            if(solution_exists(game_board, current_pos.update_values(current_pos.first() , current_pos.second() + 1), dest)) { // Se mueve hacia la derecha (x+1).
                return true;
            }

            if(solution_exists(game_board, current_pos.update_values(current_pos.first() + 1, current_pos.second()), dest)) { // Se mueve hacia abajo (y+1).
                return true;
            }
            solution[current_pos.first()][current_pos.second()].set_block(Block_Type.WALL, 0);
        }
        return false;
    }

    private boolean is_valid_move(Block[][] game_board, Pair<Integer, Integer> pos) {
        if(pos.first() > 0 && pos.second() > 0 && pos.first() < BOARD_SIZE - 2 && pos.second() < BOARD_SIZE - 2) {
            Block current_block = game_board[pos.first()][pos.second()];
            if (current_block.get_type().equals(Block_Type.WALL)) return false; // Verifica si es un muro.
            return true;

            /*if (current_block.get_type().equals(Block_Type.HIGH_BLOCK)) {
                if (current_block.get_height() == current_height + 1) { //Verifica si la altura del bloque solo se excede en 1.
                    current_height++;
                    return true;
                }
            }
            if (current_height > 0 && current_height - 1 == current_block.get_height()) { // Verifica si la altura del siguiente no baje en más de 1.
                current_height--;
                return true;
            }*/
        }
        return false;
    }

    private void build_empty_board(Block[][] og_board) {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                if(og_board[i][j].get_type().equals(Block_Type.WALL)) solution[i][j] = new Block(Block_Type.WALL, 0);
                else solution[i][j] = new Block(Block_Type.NORMAL_BLOCK, 0);
            }
        }
    }

    public void print_board() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                if(solution[i][j] == null) System.out.print("0" + " ");
                else System.out.print(solution[i][j] + " ");
            }
            System.out.println();
        }
    }
}
