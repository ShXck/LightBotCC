package org.tec.comp.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class TransferObject {

    private int[][] board;
    private final String ID = "ide";

    public TransferObject(Block[][] board) {
        this.board = new int[board.length][board[0].length];
        set_int_board(board);
    }

    private void set_int_board(Block[][] board) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                this.board[i][j] = board[i][j].to_int();
            }
        }
    }

    public String to_json() {
        ObjectMapper mapper = new ObjectMapper();
        String result = "";

        try {
            result = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void print_board() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public String getID() {
        return ID;
    }
}
