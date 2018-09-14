package org.tec.comp.interpreter;

import org.tec.comp.game.Direction;

/**
 * Container with information of actions that depend on the position.
 */
public class Position_Act_Data extends Action {

    private Multiple_Action_Container loop_data;
    private int n; // in case of blocks this is either the height of the block or the block quantity in a direction. Any other case should be -1.
    private String proc_name; // this is the name of the proc that is called after the action CALL.
    private Direction dir;

    public Position_Act_Data(Action_Type type, Multiple_Action_Container ldata) {
        super(type);
        loop_data = ldata;
        action_type = type;
        n = -1;
    }

    public Position_Act_Data(Action_Type type, int nparam) {
        super(type);
        loop_data = null;
        n = nparam;
    }

    public Position_Act_Data(Action_Type type) {
        super(type);
        loop_data = null;
        n = -1;
    }

    public Position_Act_Data(Action_Type type, String proc) {
        super(type);
        proc_name = proc;
    }

    public Position_Act_Data(Action_Type type, Direction direction) {
        super(type);
        dir = direction;
    }

    public void set_loop_data(Multiple_Action_Container loop) {
        loop_data = loop;
    }

    public Multiple_Action_Container get_loop_data() {
        return loop_data;
    }

    public int get_n_param() {
        return n;
    }

    public Direction direction() {
        return dir;
    }

    public String toString() {
        return super.get_act_type().toString();
    }
}
