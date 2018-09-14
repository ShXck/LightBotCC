package org.tec.comp.game;

public class Block {

    private int id;
    private int height;
    private Block_Type block_type;

    public Block(Block_Type type, int bheight) {
        set_block(type, bheight);
    }

    public void set_block(Block_Type type, int bheight) {
        block_type = type;
        height = bheight;

        switch (type) {
            case NORMAL_BLOCK: {
                id = 0;
                break;
            }
            case HIGH_BLOCK: {
                id = 1;
                break;
            }
            case BLUE_LIGHT: {
                id = 2;
                break;
            }
            case BLANK: {
                id = 9;
                break;
            }
            case ROBOT: {
                id = 4;
                break;
            }
        }
    }


    private String str_repr() {
        StringBuilder sb = new StringBuilder("XXXX");

        switch (block_type) {
            case NORMAL_BLOCK: {
                sb.setCharAt(0, '1');
                sb.setCharAt(1, '0');
                sb.setCharAt(3, '0');
                break;
            }
            case HIGH_BLOCK: {
                if (height == 2) sb.setCharAt(0, '2');
                else sb.setCharAt(0, '3');
                sb.setCharAt(1, '0');
                sb.setCharAt(3, '0');
                break;
            }
            case BLUE_LIGHT:{
                if (height == 2) {
                    sb.setCharAt(0, '2');
                    sb.setCharAt(1, '1');
                } else if(height > 2) {
                    sb.setCharAt(0, '3');
                    sb.setCharAt(1, '1');
                } else {
                    sb.setCharAt(1, '1');
                    sb.setCharAt(0, '1');
                }
                sb.setCharAt(3, '0');
                break;
            }
            case ROBOT: {
                if (height == 2) sb.setCharAt(0, '2');
                else if(height > 2) {
                    sb.setCharAt(0, '3');
                } else {
                    sb.setCharAt(0, '1');
                }
                sb.setCharAt(1,'0');
                sb.setCharAt(3, '1');
                break;
            }
            case BLANK: {
                sb.setCharAt(0, '1');
                sb.setCharAt(1, '0');
                sb.setCharAt(3, '0');
                break;
            }
        }
        sb.setCharAt(2, '0');

        return sb.toString();
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

    public int to_int() {
        return Integer.parseInt(str_repr());
    }

    @Override
    public String toString() {
        return str_repr();
    }
}
