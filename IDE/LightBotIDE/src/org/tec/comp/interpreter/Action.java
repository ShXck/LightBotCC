package org.tec.comp.interpreter;

public class Action {

    Action_Type action_type;

    public Action(Action_Type type) {
        action_type = type;
    }

    public Action_Type get_act_type() {
        return action_type;
    }
}
