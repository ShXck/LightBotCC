package org.tec.comp.game;

import org.tec.comp.interpreter.Pair;

public class Block {

    private int id;
    private int height;
    private Block_Type block_type;
    private Pair<Integer, Integer> block_pos;

    public Block(Block_Type type, int bheight) {
        set_block(type, bheight);
        block_pos = new Pair<>(-1,-1);
    }

    public void set_block(Block_Type type, int bheight) {
        block_type = type;
        height = 0;

        switch (type) {
            case NORMAL_BLOCK: {
                id = 0;
                break;
            }
            case HIGH_BLOCK: {
                id = 1;
                height = bheight;
                break;
            }
            case BLUE_LIGHT: {
                id = 2;
                break;
            }
            case WALL: {
                id = 3;
                break;
            }
            case ROBOT: {
                id = 4;
                break;
            }
        }
    }

    public void set_block_pos(Pair<Integer,Integer> pos) {
        block_pos = pos;
    }

    public void set_block_pos(int x, int y) {
        block_pos.update_values(x, y);
    }

    public void set_height(int bheight) {
        height = bheight;
    }

    public int get_height() {
        return height;
    }

    public int get_id() {
        return id;
    }

    public Block_Type get_type() {
        return block_type;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
