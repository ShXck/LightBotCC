package org.tec.comp.interpreter;

import org.tec.comp.Message_Handler;

public class Semantic_Handler {

    public static void run_semantic_parse() {
        check_procs();
        check_vars();
        check_instruction(Action_Type.POS_START);
        check_instruction(Action_Type.PUT_LIGHT);
    }

    private static void check_vars() {
        if(LangParser.var_list.isEmpty()) LangParser.msg_list.add(Message_Handler.no_vars_found());
    }

    private static void check_procs() {
        for(Position_Procedure pos : LangParser.code_actions) {
            for(Action a : pos.get_action_list()) {
                switch (a.get_act_type()) {
                    case CALL_PROC: {
                        Position_Act_Data act_data = (Position_Act_Data) a;
                        if(!LangParser.proc_names.contains(act_data.get_proc_name()))
                            LangParser.msg_list.add(Message_Handler.no_such_proc_found(act_data.get_proc_name()));
                        break;
                    }
                }
            }
        }
    }

    private static void check_instruction(Action_Type action) {
        boolean action_found = false;
        for (Position_Procedure pp : LangParser.code_actions) {
            for(Action a : pp.get_action_list()) {
                if(a.get_act_type().equals(action)) action_found = true;
                if(a.get_act_type().equals(Action_Type.FOR_LOOP) || a.get_act_type().equals(Action_Type.KEEP_LOOP) || a.get_act_type().equals(Action_Type.WHEN_LOOP)) { // TODO: ADD KEEP AND WHEN.
                    Position_Act_Data for_loop = (Position_Act_Data) a;
                    for(Action aa : for_loop.get_loop_data().get_loop_actions()) {
                        if(aa.get_act_type().equals(action)) action_found = true;
                    }
                }
            }
        }
        if(!action_found) LangParser.msg_list.add(Message_Handler.no_action_found(action));
    }
}
